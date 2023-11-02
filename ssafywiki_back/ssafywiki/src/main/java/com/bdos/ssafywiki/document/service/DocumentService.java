package com.bdos.ssafywiki.document.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.bdos.ssafywiki.diff.DiffMatchPatch;
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
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;
    private final RevisionRepository revisionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DocsCategoryRepository docsCategoryRepository;

    //mapstruct
    private final RevisionMapper revisionMapper;

    private final DiffMatchPatch diffMatchPatch;

    @Transactional
    public RevisionDto.Response writeDocs(DocumentDto.Post post) {
        //로그인 한 사용자(작성 유저) : JWT
        User user = new User("qqq@naver.com", "pwpw", "ksy", "sysy", Role.USER9, "010", "buk", "token");
        //일단 유저를 다른 곳에 연관관계로 등록하기 위해 임시로 저장
        userRepository.save(user);
        //유저 널 체크 필요

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
                .diffAmount((long)diffMatchPatch.diff_length(diffMatchPatch.diff_main("", post.getContent())))
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

    public RevisionDto.Response readDocs(Long docsId) {
        //유저의 권한과 문서의 권한을 체크해서 처리

        //해당 문서 엔티티 찾기
        Document document = documentRepository.findById(docsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));
        //docsId에 해당하는 가장 최신 버전의 문서를 찾아서 리턴 (revision 엔티티 찾기)
        Revision revision = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);
        return revisionMapper.toResponse(revision);
    }

    public RevisionDto.Response updateDocs(DocumentDto.Put put) {
        //유저의 권한과 문서의 권한을 체크해서 처리

        //엔티티 : 코멘트, 내용 -> 버전
        Comment comment = new Comment(put.getComment());
        Content content = new Content(put.getContent());
        commentRepository.save(comment);
        contentRepository.save(content);

        Document document = documentRepository.findById(put.getDocsId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DOCUMENT_NOT_FOUND));

        //연관관계 : 수정 유저, 이전 버전id, 문서id
        User user = userRepository.findById(1L).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Revision preRevision = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);

        //그 외 : 텍스트 증감 수, 문서 버전 번호
        Long textDiff = (long)diffMatchPatch.diff_length(diffMatchPatch.diff_main(preRevision.getContent().getText(), put.getContent()));
        Long newVersionNo = preRevision.getNumber() + 1;

        Revision revision = new Revision(textDiff, newVersionNo);

        //연관관계 등록 : 코멘트, 내용, 문서, 유저, 버전(selft)
        revision.setUser(user);
        revision.setDocument(document);
        revision.setContent(content);
        revision.setComment(comment);
        revision.setParent(preRevision);

        revisionRepository.save(revision);

        //문서 상세 내용 리턴
        return revisionMapper.toResponse(revision);
    }
}
