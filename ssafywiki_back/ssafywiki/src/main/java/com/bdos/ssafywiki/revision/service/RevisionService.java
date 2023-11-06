package com.bdos.ssafywiki.revision.service;

import com.bdos.ssafywiki.diff.Conflict;
import com.bdos.ssafywiki.diff.MyDiffUtils;
import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.discussion.entity.Discussion;
import com.bdos.ssafywiki.discussion.mapper.DiscussionMapper;
import com.bdos.ssafywiki.document.entity.Document;
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
import com.github.difflib.algorithm.DiffAlgorithmFactory;
import com.github.difflib.algorithm.DiffAlgorithmI;
import com.github.difflib.patch.*;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevisionService {
    private final RevisionRepository revisionRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final MyDiffUtils myDiffUtils;

    public Page<Revision> getHistory(long docsId, Pageable pageable) {

        return revisionRepository.findAllByDocumentJoinComment(docsId, pageable);
    }

    public List<RevisionDto.DocsResponse> getUserHistory(long userId) {

        List<Revision> revisionList = revisionRepository.findAllByUser(userId);
        List<RevisionDto.DocsResponse> responseList = new ArrayList<>();
        for (Revision revision : revisionList) {
            RevisionDto.DocsResponse revisionDto = RevisionDto.DocsResponse.builder()
                    .title(revision.getDocument().getTitle())
                    .createdAt(revision.getCreatedAt())
                    .build();
            responseList.add(revisionDto);
        }
        return responseList;
    }

    @Transactional
    public void revokeVersion(User user, long revId) {
        if(user == null) user = new GuestUser();

        Revision revokeRev = revisionRepository.findById(revId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
        Document document = revokeRev.getDocument();

        // user 쓰기 권한 있는지 확인
        boolean result = false;
        if(document.getWriteAuth() < 4){
            result = user.getRole().havePrivilege(Privilege.getOptionLv('W', document.getWriteAuth()));
        }else{
            result = checkWriteAuth(document.getReadAuth(), user.getRole(), user.getId());
        }

        // 권한이 없으면 error
        if(!result)  throw new BusinessLogicException(ExceptionCode.DOCUMENT_NO_ACCESS);


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
    }

    public List<AbstractDelta<String>> diff(long revId, long oldRevId) {
        Revision rev = revisionRepository.findById(revId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
        Revision oldRev = revisionRepository.findById(oldRevId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));

        String oldText = oldRev.getContent().getText();
        String text = rev.getContent().getText();

        Patch<String> patch = DiffUtils.diff(myDiffUtils.splitIntoLines(oldText), myDiffUtils.splitIntoLines(text));

        return patch.getDeltas();
    }

    public List<AbstractDelta<String>> diffTest() {
        String text = """
                ----
                {{{#!wiki style="text-align:center"
                '''{{{+2 여러분이 가꾸어 나가는 {{{#00a495,#E69720 지식의 나무}}}}}}'''
                [[나무위키|{{{#00a495,#E69720 나무위키}}}]]에 오신 것을 환영합니다!
                [[:파일:nogray.png|{{{#ffffff,#E69720 다크는 회색이 아니라 검정입니다!}}}]]
                나무위키는 누구나 기여할 수 있는 위키입니다.
                검증되지 않았거나 편향된 내용이 있을 수 있습니다.
                }}}
                ####
                [include(틀:이용안내)]
                ----
                [include(틀:나무위키 프로젝트~~~~~)]
                ----
                {{{#!wiki style="margin-bottom:-10px"
                {{{#!wiki style="margin-bottom:-10px"
                [include(위키운영:접근 틀)]
                }}}
                [include(틀:나무위키)]}}}
                ----
                [include(틀:운영알림)]
                ----
                [include(틀:운영토론)][[분류:나무위키]]
                ## 운영 토론이나 운영 알림은 해당 틀을 편집해 주시기 바랍니다.
                """;
        String oldText = """
                ----
                [include(틀:대문 기념일)]
                ----
                [include(틀:이용안내)]
                ----
                [include(틀:나무위키 프로젝트)]
                ----
                {{{#!wiki style="margin-bottom:-10px"
                {{{#!wiki style="margin-bottom:-10px"
                [include(위키운영:접근 틀)]
                }}}
                [include(틀:나무위키)]}}}
                ----
                [include(틀:운영알림)]
                ----
                [include(틀:운영토론)][[분류:나무위키]]
                ## 운영 토론이나 운영 알림은 해당 틀을 편집해 주시기 바랍니다.
                """;

        List<String> oldStrings = List.of(oldText.split("\n"));
        List<String> newStrings = List.of(text.split("\n"));
        Patch<String> patch = DiffUtils.diff(oldStrings, newStrings);

        String version2 = """
                ----
                이부분이 충돌이 나겠지?
                ----
                이부분은 충돌이 안나겠지?
                ----
                ----
                {{{#!wiki style="margin-bottom:-10px"
                {{{#!wiki style="margin-bottom:-10px"
                [include(위키운영:접근 틀)]
                }}}
                [include(틀:나무위키)]}}}
                ----
                [include(틀:운영알림)]
                ----
                [include(틀:운영토론)][[분류:나무위키]]
                ## 운영 토론이나 운영 알림은 해당 틀을 편집해 주시기 바랍니다.
                """;

        try {
            String result = myDiffUtils.threeWayMerge(oldStrings, newStrings, myDiffUtils.splitIntoLines(version2));

        } catch (PatchFailedException e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.MERGE_FAILED);
        }

        return patch.getDeltas();
    }





    private boolean checkWriteAuth(Long readAuth, Role role, Long id) {
        // 권한테이블에서 권한있는지 체크

        return true;
    }
}
