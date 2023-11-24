package com.bdos.ssafywiki.revision.service;

import com.bdos.ssafywiki.diff.MergeDto;
import com.bdos.ssafywiki.diff.MyDiffUtils;
import com.bdos.ssafywiki.docs_auth.repository.UserDocsAuthRepository;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.document.repository.DocumentRepository;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.mapper.RevisionMapper;
import com.bdos.ssafywiki.revision.repository.ContentRepository;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.entity.GuestUser;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Privilege;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.repository.UserRepository;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class RevisionService {
    private final RevisionRepository revisionRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final MyDiffUtils myDiffUtils;
    private final DocumentRepository documentRepository;
    private final RevisionMapper revisionMapper;
    private final UserDocsAuthRepository userDocsAuthRepository;

    public Page<Revision> getHistory(long docsId, Pageable pageable) {

        return revisionRepository.findAllByDocumentJoinComment(docsId, pageable);
    }

    public List<RevisionDto.DocsResponse> getUserHistory(long userId) {

        List<Revision> revisionList = revisionRepository.findAllByUser(userId);
        List<RevisionDto.DocsResponse> responseList = new ArrayList<>();
        for (Revision revision : revisionList) {
            RevisionDto.DocsResponse revisionDto = RevisionDto.DocsResponse.builder()
                    .docsId(revision.getId())
                    .title(revision.getDocument().getTitle())
                    .createdAt(revision.getCreatedAt())
                    .build();
            responseList.add(revisionDto);
        }
        return responseList;
    }

    @Transactional
    public void revertVersion(User user, long revId) {
        if (user == null) user = new GuestUser();

        Revision revokeRev = revisionRepository.findById(revId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
        Document document = revokeRev.getDocument();

        // user 쓰기 권한 있는지 확인
        boolean result = false;
        if (document.getWriteAuth() < 4) {
            result = user.getRole().havePrivilege(Privilege.getOptionLv('W', document.getWriteAuth()));
        } else {
            if (user.getId().equals(document.getUser().getId())) {
                // private 문서 작성자와 읽기 요청 유저와 같으면
                result = true;
            } else {
                // 권한 테이블 조회
                result = checkWriteAuth(document.getWriteAuth(), user.getRole(), user.getId());
            }
        }

        // 권한이 없으면 error
        if (!result) throw new BusinessLogicException(ExceptionCode.DOCUMENT_NO_ACCESS);


        Revision topRev = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);

        // revokeRev와 topRev의 diff amount를 계산
        String oldText = topRev.getContent().getText();
        String text = revokeRev.getContent().getText();
        Patch<String> patch = DiffUtils.diff(myDiffUtils.splitIntoLines(oldText), myDiffUtils.splitIntoLines(text));

        long diffAmount = myDiffUtils.diffLength(patch);

        Content newContent = Content.builder().text(revokeRev.getContent().getText()).build();
        contentRepository.save(newContent);

        Revision newRev = Revision.builder()
                .number(topRev.getNumber() + 1)
                .diffAmount(diffAmount)
                .origin(revokeRev)
                .parent(topRev)
                .content(newContent)
                .document(document)
                .user(user)
                .build();

        revisionRepository.save(newRev);

        document.setModifiedAt(LocalDateTime.now());
        if ("".equals(oldText) && !"".equals(text)) document.setDeleted(false);
        if ("".equals(text)) document.setDeleted(true);

        documentRepository.save(document);
    }

    public List<AbstractDelta<String>> diff(long revId, long oldRevId) {
        Revision rev = revisionRepository.findById(revId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
        Revision oldRev = revisionRepository.findById(oldRevId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));

        String oldText = oldRev.getContent().getText();
        String text = rev.getContent().getText();

        Patch<String> patch = DiffUtils.diff(myDiffUtils.splitIntoLines(oldText), myDiffUtils.splitIntoLines(text));

        return patch.getDeltas();
    }

    public MergeDto diffTest() {
        String oldText = """
                최초의 문서
                1
                2
                """;

        String text = """
                최초의 문서
                                
                2
                """;

        String version2 = """
                최초의 문서
                1
                """;


        List<String> base = myDiffUtils.splitIntoLines(oldText);
        List<String> versionA = myDiffUtils.splitIntoLines(text);
        System.out.println(versionA);
        List<String> versionB = myDiffUtils.splitIntoLines(version2);
        System.out.println(versionB);

        MergeDto threeWayMergeResult = null;
        try {
            threeWayMergeResult = myDiffUtils.threeWayMerge(base, versionA, versionB);
        } catch (PatchFailedException e) {
            throw new BusinessLogicException(ExceptionCode.MERGE_FAILED);
        }

        return threeWayMergeResult;
    }


    private boolean checkWriteAuth(Long writeAuth, Role role, Long id) {
        if (role == Role.ROLE_ADMIN) return true;

        // 권한테이블에서 권한있는지 체크
        return userDocsAuthRepository.findByDocsAuthIdAndUserId(writeAuth, id).isPresent();
    }

    public int[][] getUserContributeDocs(User user, LocalDateTime startDate) {
//        List<Revision> revisionList = revisionRepository.findByUserWithStartDate(user.getId(), startDate);

        //시작 날짜부터 1씩 증가하면서 i, j++
        int[][] arr = new int[23][7];
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 7; j++) {
                List<Revision> revisionList = revisionRepository.findByUserWithDate(user.getId(), startDate, startDate.plusDays(1L));
                arr[i][j] = revisionList.size();
                startDate = startDate.plusDays(1L);
            }
        }

        return arr;
    }

    public List<RevisionDto.UserContribute> getUserContributeDocsWithDate(User user, LocalDateTime date) {
        //결과값
        List<RevisionDto.UserContribute> result = new ArrayList<>();

        //당일에 수정한 문서를 조회 - 문서 아이디, 문서 제목
        List<Revision> updateDocs = revisionRepository.findByDocsUserWithDate(user.getId(), date, date.plusDays(1L));

        //수정한 문서에 대해 수정한 시간, 수정한 버전 ID, 수정 코멘트를 조회
        for (Revision revision : updateDocs) {
            List<RevisionDto.ContributeDetail> revisionInfo = new ArrayList<>();
            List<Revision> revisionList = revisionRepository.findRevisionInfoByUserAndDateAndDocs(revision.getDocument(), user, date, date.plusDays(1L));
            for (Revision rev : revisionList) {
                RevisionDto.ContributeDetail contributeDetail = RevisionDto.ContributeDetail.builder()
                        .createdAt(rev.getCreatedAt())
                        .revisionId(rev.getId())
                        .revisionComment((rev.getComment() == null) ? "" : rev.getComment().getContent())
                        .build();
                revisionInfo.add(contributeDetail);
            }
            RevisionDto.UserContribute userContribute = RevisionDto.UserContribute.builder()
                    .docsId(revision.getDocument().getId())
                    .title((revision.getDocument().getTitle()))
                    .revisions(revisionInfo)
                    .build();
            result.add(userContribute);
        }

        return result;
    }

    public int[][] getUserContributeDocs(Long userId, LocalDateTime startDate) {
//        List<Revision> revisionList = revisionRepository.findByUserWithStartDate(user.getId(), startDate);
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        //시작 날짜부터 1씩 증가하면서 i, j++
        int[][] arr = new int[23][7];
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 7; j++) {
                List<Revision> revisionList = revisionRepository.findByUserWithDate(user.getId(), startDate, startDate.plusDays(1L));
                arr[i][j] = revisionList.size();
                startDate = startDate.plusDays(1L);
            }
        }

        return arr;
    }

    public List<RevisionDto.UserContribute> getUserContributeDocsWithDate(Long userId, LocalDateTime date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        //결과값
        List<RevisionDto.UserContribute> result = new ArrayList<>();

        //당일에 수정한 문서를 조회 - 문서 아이디, 문서 제목
        List<Revision> updateDocs = revisionRepository.findByDocsUserWithDate(user.getId(), date, date.plusDays(1L));

        //수정한 문서에 대해 수정한 시간, 수정한 버전 ID, 수정 코멘트를 조회
        for (Revision revision : updateDocs) {
            List<RevisionDto.ContributeDetail> revisionInfo = new ArrayList<>();
            List<Revision> revisionList = revisionRepository.findRevisionInfoByUserAndDateAndDocs(revision.getDocument(), user, date, date.plusDays(1L));
            for (Revision rev : revisionList) {
                RevisionDto.ContributeDetail contributeDetail = RevisionDto.ContributeDetail.builder()
                        .createdAt(rev.getCreatedAt())
                        .revisionId(rev.getId())
                        .revisionComment((rev.getComment() == null) ? "" : rev.getComment().getContent())
                        .build();
                revisionInfo.add(contributeDetail);
            }
            RevisionDto.UserContribute userContribute = RevisionDto.UserContribute.builder()
                    .docsId(revision.getDocument().getId())
                    .title((revision.getDocument().getTitle()))
                    .revisions(revisionInfo)
                    .build();
            result.add(userContribute);
        }

        return result;
    }
}
