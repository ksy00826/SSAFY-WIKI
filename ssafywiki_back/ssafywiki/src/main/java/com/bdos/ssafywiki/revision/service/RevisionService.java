package com.bdos.ssafywiki.revision.service;

import com.bdos.ssafywiki.diff.Diff;
import com.bdos.ssafywiki.diff.DiffMatchPatch;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.repository.ContentRepository;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class RevisionService {
    private final RevisionRepository revisionRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final DiffMatchPatch diffMatchPatch;

    public Page<Revision> getHistory(long docsId, Pageable pageable) {

        return revisionRepository.findAllByDocumentJoinComment(docsId, pageable);
    }

    public Revision getDetail(long docsId, long revNumber) {

        return revisionRepository.findByDocumentIdAndNumber(docsId, revNumber);
    }

    @Transactional
    public void revokeVersion(long revId) {
        // revoke를 한 user
        User user = new User("qqq@naver.com", "pwpw", "조현덕", "hd", "ssafy", "010", "buk", "token");
        // 일단 유저를 다른 곳에 연관관계로 등록하기 위해 임시로 저장
        userRepository.save(user);

        Revision revokeRev = revisionRepository.findById(revId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
        Document document = revokeRev.getDocument();
        Revision topRev = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);

        // revokeRev와 topRev의 diff amount를 계산
        long diffAmount = 0;

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

    public LinkedList<Diff> diff(long revId, long oldRevId) {
//        String newText = """
//                ----
//                {{{#!wiki style="text-align:center"
//                '''{{{+2 여러분이 가꾸어 나가는 {{{#00a495,#E69720 지식의 나무}}}}}}'''
//                [[나무위키|{{{#00a495,#E69720 나무위키}}}]]에 오신 것을 환영합니다!
//                [[:파일:nogray.png|{{{#ffffff,#E69720 다크는 회색이 아니라 검정입니다!}}}]]
//                나무위키는 누구나 기여할 수 있는 위키입니다.
//                검증되지 않았거나 편향된 내용이 있을 수 있습니다.
//                }}}
//                ----
//                [include(틀:이용안내)]
//                ----
//                [include(틀:나무위키 프로젝트)]
//                ----
//                {{{#!wiki style="margin-bottom:-10px"
//                {{{#!wiki style="margin-bottom:-10px"
//                [include(위키운영:접근 틀)]
//                }}}
//                [include(틀:나무위키)]}}}
//                ----
//                [include(틀:운영알림)]
//                ----
//                [include(틀:운영토론)][[분류:나무위키]]
//                ## 운영 토론이나 운영 알림은 해당 틀을 편집해 주시기 바랍니다.
//                """;
//        String oldText = """
//                ----
//                [include(틀:대문 기념일)]
//                ----
//                [include(틀:이용안내)]
//                ----
//                [include(틀:나무위키 프로젝트)]
//                ----
//                {{{#!wiki style="margin-bottom:-10px"
//                {{{#!wiki style="margin-bottom:-10px"
//                [include(위키운영:접근 틀)]
//                }}}
//                [include(틀:나무위키)]}}}
//                ----
//                [include(틀:운영알림)]
//                ----
//                [include(틀:운영토론)][[분류:나무위키]]
//                ## 운영 토론이나 운영 알림은 해당 틀을 편집해 주시기 바랍니다.
//                """;

        Revision rev = revisionRepository.findById(revId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
        Revision oldRev = revisionRepository.findById(oldRevId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));

        String oldText = oldRev.getContent().getText();
        String text = rev.getContent().getText();

        LinkedList<Diff> diffs = diffMatchPatch.diff_main(oldText, text);
//        System.out.println("########################");
//        System.out.println("두개의 차이 " + diffMatchPatch.diff_levenshtein(diffs));
//        System.out.println("차이" + diffMatchPatch.diff_length(diffs));
        diffMatchPatch.diff_cleanupSemantic(diffs);
//        System.out.println(diffs);

        String html = diffMatchPatch.diff_prettyHtml(diffs);

        return diffs;
    }

    public String diffHtml(long revId, long oldRevId){
        String text = """
                ----
                {{{#!wiki style="text-align:center"
                '''{{{+2 여러분이 가꾸어 나가는 {{{#00a495,#E69720 지식의 나무}}}}}}'''
                [[나무위키|{{{#00a495,#E69720 나무위키}}}]]에 오신 것을 환영합니다!
                [[:파일:nogray.png|{{{#ffffff,#E69720 다크는 회색이 아니라 검정입니다!}}}]]
                나무위키는 누구나 기여할 수 있는 위키입니다.
                검증되지 않았거나 편향된 내용이 있을 수 있습니다.
                }}}
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
        LinkedList<Diff> diffs = diffMatchPatch.diff_main(oldText, text);
//        System.out.println("########################");
//        System.out.println("두개의 차이 " + diffMatchPatch.diff_levenshtein(diffs));
//        System.out.println("차이" + diffMatchPatch.diff_length(diffs));
        diffMatchPatch.diff_cleanupSemantic(diffs);
//        System.out.println(diffs);

        String html = diffMatchPatch.diff_prettyHtml(diffs);
        return html;
    }
}
