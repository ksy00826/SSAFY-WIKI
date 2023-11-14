package com.bdos.ssafywiki.document.service;

import com.bdos.ssafywiki.diff.MergeDto;
import com.bdos.ssafywiki.diff.MyDiffUtils;
import com.bdos.ssafywiki.docs_auth.repository.DocsAuthRepository;
import com.bdos.ssafywiki.docs_auth.repository.UserDocsAuthRepository;
import com.bdos.ssafywiki.docs_category.entity.Category;
import com.bdos.ssafywiki.docs_category.entity.DocsCategory;
import com.bdos.ssafywiki.docs_category.repository.CategoryRepository;
import com.bdos.ssafywiki.docs_category.repository.DocsCategoryRepository;
import com.bdos.ssafywiki.document.dto.DocumentDto;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.mapper.DocumentMapper;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Comment;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.mapper.RevisionMapper;
import com.bdos.ssafywiki.revision.repository.CommentRepository;
import com.bdos.ssafywiki.revision.repository.ContentRepository;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.entity.GuestUser;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Privilege;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.repository.UserRepository;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.PatchFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;
    private final RevisionRepository revisionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DocsCategoryRepository docsCategoryRepository;
    private final UserDocsAuthRepository userDocsAuthRepository;
    private final DocsAuthRepository docsAuthRepository;

    //mapstruct
    private final RevisionMapper revisionMapper;

    private final MyDiffUtils myDiffUtils;
    private final DocumentMapper documentMapper;

    @Transactional
    public RevisionDto.DocsResponse writeDocs(DocumentDto.Post post, User userDetail) {
        //일단 유저를 다른 곳에 연관관계로 등록하기 위해 임시로 저장
        //userRepository.save(user);
        User user = userRepository.findById(userDetail.getId()).get();

        //@@@@@@@1. Document entity 생성
        Document document = Document.builder()
                .title(post.getTitle())
                .readAuth(post.getReadAuth())
                .writeAuth(post.getWriteAuth())
                .build(); //redirect, deleted는 기본값이 false

        //1.1 연관관계 등록
        document.setUser(user);
        document.setParent(null);
        document.setChildren(new ArrayList<>());
        documentRepository.save(document);

        //1.2 카테고리 등록
        //카테고리 테이블을 일단 읽어옴
        post.getCategories().stream()
                //존재하지 않는 카테고리는 생성 및 저장하여 리턴
                .map(categoryName ->
                        categoryRepository.findByName(categoryName)
                                .orElseGet(() -> {
                                    Category category = Category.builder().name(categoryName).build();
                                    categoryRepository.save(category);
                                    return category;
                                }))
                //각 카테고리마다 문서와 연결
                .forEach(category -> {
                    //카테고리-문서 엔티티 생성
                    DocsCategory docsCategory = new DocsCategory();
                    System.out.println(category);
                    docsCategory.setCategory((Category) category);
                    docsCategory.setDocument(document);
                    docsCategoryRepository.save(docsCategory);
                    System.out.println(docsCategory);
                });
        System.out.println(document.getCategoryList());

        //@@@@@@@2. Revision entity 생성
        Revision revision = Revision.builder()
                .number(1L)
                .diffAmount((long) myDiffUtils.diffLength(DiffUtils.diff(myDiffUtils.splitIntoLines(""), myDiffUtils.splitIntoLines(post.getContent()))))
                .build();

        //2.1 Content entity 생성 + Comment entity 생성
        Content content = new Content(post.getContent());
        Comment comment = new Comment("First Document");
        contentRepository.save(content);
        commentRepository.save(comment);

        //2.2 연관관계 등록
        revision.setContent(content);
        revision.setComment(comment);
        revision.setDocument(document);
        revision.setUser(user);

        revisionRepository.save(revision);

        //만들어진 문서 DTO 리턴
        return revisionMapper.toResponse(revision);
    }

    public RevisionDto.DocsResponse readDocs(Long docsId, Long revId, User user) {
        if (user == null) user = new GuestUser();

        //해당 문서 엔티티 찾기
        Document document = documentRepository.findById(docsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));

        //유저의 권한과 문서의 권한을 체크해서 처리
        log.info(user.toString());
        boolean result = false;
        if (document.getReadAuth() < 4) {
            result = user.getRole().havePrivilege(Privilege.getOptionLv('R', document.getReadAuth()));
        } else {  // private 문서
            if (!checkReadAuth(document.getReadAuth(), user.getRole(), user.getId()))
                throw new BusinessLogicException(ExceptionCode.REQUIRED_PRIVATE);
        }

        // 권한이 없으면 error
        if (!result) {
            if (document.getReadAuth() == 2)
                throw new BusinessLogicException(ExceptionCode.REQUIRED_LOGIN);
            if (document.getReadAuth() == 3)
                throw new BusinessLogicException(ExceptionCode.REQUIRED_MANAGER);
            else
                throw new BusinessLogicException(ExceptionCode.REQUIRED_PRIVATE);
        }

        // 권한이 있으면
        //docsId에 해당하는 가장 최신 버전의 문서를 찾아서 리턴 (revision 엔티티 찾기)
        Revision revision;
        if (revId == null) revision = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);
        else revision = revisionRepository.findByDocumentIdAndRevisionId(docsId, revId);

        RevisionDto.DocsResponse response = revisionMapper.toResponse(revision);
        if ("".equals(revision.getContent() != null ? revision.getContent().getText() : ""))
            response.setContent("이 문서는 삭제되었습니다.");

        return response;
    }

    private boolean checkReadAuth(Long readAuth, Role role, Long id) {
        if (role == Role.ROLE_ADMIN) return true;

        // 권한테이블에서 권한있는지 체크
        return userDocsAuthRepository.findByDocsAuthIdAndUserId(readAuth, id).isPresent();
    }


    public RevisionDto.CheckUpdateResponse checkUpdateDocs(Long docsId, User user) {
        if (user == null) user = new GuestUser();

        //해당 문서 엔티티 찾기
        Document document = documentRepository.findById(docsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));

        //유저의 권한과 문서의 권한을 체크해서 처리
        boolean canUpdate = false;
        boolean canRead = false;
        if (document.getReadAuth() < 4) {
            canRead = user.getRole().havePrivilege(Privilege.getOptionLv('R', document.getReadAuth()));
        } else {  // private 문서
            canRead = checkReadAuth(document.getReadAuth(), user.getRole(), user.getId());
        }
        if (document.getWriteAuth() < 4) {
            canUpdate = user.getRole().havePrivilege(Privilege.getOptionLv('W', document.getWriteAuth()));
        } else {  // private 문서
            canUpdate = checkReadAuth(document.getWriteAuth(), user.getRole(), user.getId());
        }

        // Read 권한이 없으면 error
        if (!canRead) throw new BusinessLogicException(ExceptionCode.DOCUMENT_NO_ACCESS);

        // docsId에 해당하는 가장 최신 버전의 문서를 찾아서 리턴 (revision 엔티티 찾기)
        // 수정할 수 없으면 update false로 보냄.
        Revision revision = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);
        return revisionMapper.toCheckUpdateResponse(revision, canUpdate);
    }

    public RevisionDto.UpdateResponse updateDocs(DocumentDto.Put put, User user) {
        //유저의 권한과 문서의 권한을 체크해서 처리

        // base 버전이 최상위 버전인지 확인
        Document document = documentRepository.findById(put.getDocsId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));
        Revision topRevision = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);

        // 충돌난거 수정해서 다시보낸 경우
        // put.getTopRevId() != null
        // put.getTopRevId() == topRevision.getId() 이면 threeWayMerge 안함
        // put.getTopRevId() != topRevision.getId() 이면 다시 threeWayMerge 시도
        MergeDto threeWayMergeResult = null;
        // base 버전이 최상위 버전이 아닌 경우
        if ((put.getTopRevId() == null && !put.getRevId().equals(topRevision.getId())) ||
                (put.getTopRevId() != null && !put.getTopRevId().equals(topRevision.getId()))) {

            Revision baseRevision = revisionRepository.findById(put.getRevId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
            List<String> base = myDiffUtils.splitIntoLines(baseRevision.getContent().getText());
            List<String> latest = myDiffUtils.splitIntoLines(topRevision.getContent().getText());
            List<String> update = myDiffUtils.splitIntoLines(put.getContent());
            try {
                threeWayMergeResult = myDiffUtils.threeWayMerge(base, latest, update);
            } catch (PatchFailedException e) {
                throw new BusinessLogicException(ExceptionCode.MERGE_FAILED);
            }
//            System.out.println("#############################");
//            System.out.println(threeWayMergeResult.getResult());

            // 결과의 exception 코드가 null이면 충돌안나고 병합된것
            if (ExceptionCode.MERGE_CONFLICT.equals(threeWayMergeResult.getExceptionCode())) {
                return RevisionDto.UpdateResponse.builder()
                        .docsId(put.getDocsId())
                        .revId(put.getRevId())
                        .title(document.getTitle())
                        .comment(put.getComment())
                        .topRevId(topRevision.getId())
                        .content(threeWayMergeResult.getResult()).exceptionCode(threeWayMergeResult.getExceptionCode()).build();
            }
        }

        // threeWayMergeResult가 null이 아니면 병합 과정을 거쳤다는 것.
        String modifiedContent = threeWayMergeResult != null ? threeWayMergeResult.getResult() : put.getContent();

        document.setModifiedAt(LocalDateTime.now());

        //엔티티 : 코멘트, 내용 -> 버전
        Comment comment = new Comment(put.getComment());
        Content content = new Content(modifiedContent);
        commentRepository.save(comment);
        contentRepository.save(content);


        //연관관계 : 수정 유저, 이전 버전id, 문서id
        //그 외 : 텍스트 증감 수, 문서 버전 번호
        Long textDiff = (long) myDiffUtils.diffLength(DiffUtils.diff(myDiffUtils.splitIntoLines(topRevision.getContent().getText()), myDiffUtils.splitIntoLines(modifiedContent)));
        Long newVersionNo = topRevision.getNumber() + 1;

        Revision revision = new Revision(textDiff, newVersionNo);

        //연관관계 등록 : 코멘트, 내용, 문서, 유저, 버전(selft)
        revision.setUser(user);
        revision.setDocument(document);
        revision.setContent(content);
        revision.setComment(comment);
        revision.setParent(topRevision);

        revisionRepository.save(revision);
//            saveRecentDocsToRedis(document);


        List<Long> ids = docsCategoryRepository.findAllByDocsId(put.getDocsId());
        ids.stream().forEach(
                docsCategoryId -> {
                    docsCategoryRepository.deleteById(docsCategoryId);
                }
        );
        put.getCategories().stream()
                //존재하지 않는 카테고리는 생성 및 저장하여 리턴
                .map(categoryName ->
                        categoryRepository.findByName(categoryName)
                                .orElseGet(() -> {
                                    Category category = Category.builder().name(categoryName).build();
                                    categoryRepository.save(category);
                                    return category;
                                }))
                //각 카테고리마다 문서와 연결
                .forEach(category -> {
                    //카테고리-문서 엔티티 생성
                    DocsCategory docsCategory = new DocsCategory();
                    System.out.println(category);
                    docsCategory.setCategory((Category) category);
                    docsCategory.setDocument(document);
                    docsCategoryRepository.save(docsCategory);
                    System.out.println(docsCategory);
                });
        //문서 상세 내용 리턴
        return RevisionDto.UpdateResponse.builder()
                .docsId(put.getDocsId())
                .revId(revision.getId())
                .title(document.getTitle())
                .comment(put.getComment())
                .topRevId(topRevision.getId())
                .modifiedAt(revision.getModifiedAt())
                .content(revision.getContent().getText()).exceptionCode(null).build();


    }

    public List<DocumentDto.Recent> loadRecentDocsList() {
        List<Document> dbDocumentList = documentRepository.findTop10ByOrderByModifiedAtDesc();
        List<DocumentDto.Recent> recentsDocsList = dbDocumentList.stream()
                .map(documentMapper::documentToRecent)
                .collect(Collectors.toList());
        return recentsDocsList;
    }

    public DocumentDto.Detail getRandomDocs() {
        Long docsCnt = documentRepository.getAllDocsCnt();
        Document randomDoc = null;
        do{
            Long randomId = (long)((int) (Math.random() * docsCnt) + 1);
            randomDoc = documentRepository.findById(randomId).orElse(null);
        }while(randomDoc == null || randomDoc.isDeleted());

        return documentMapper.toDetail(randomDoc);
    }
}
