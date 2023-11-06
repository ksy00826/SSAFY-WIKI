package com.bdos.ssafywiki.revision.service;

import com.bdos.ssafywiki.diff.Conflict;
import com.bdos.ssafywiki.diff.Diff;
import com.bdos.ssafywiki.diff.DiffMatchPatch;
import com.bdos.ssafywiki.diff.MergeResult;
import com.bdos.ssafywiki.document.entity.Document;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.revision.entity.Content;
import com.bdos.ssafywiki.revision.entity.Revision;
import com.bdos.ssafywiki.revision.repository.ContentRepository;
import com.bdos.ssafywiki.revision.repository.RevisionRepository;
import com.bdos.ssafywiki.user.entity.User;
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
    private final DiffMatchPatch diffMatchPatch;

    public Page<Revision> getHistory(long docsId, Pageable pageable) {

        return revisionRepository.findAllByDocumentJoinComment(docsId, pageable);
    }

    public Revision getDetail(long docsId, long revNumber) {

        return revisionRepository.findByDocumentIdAndNumber(docsId, revNumber);
    }

    public List<RevisionDto.Version> getUserHistory(long userId) {

        return revisionRepository.findAllByUser(userId);
    }

    @Transactional
    public void revertVersion(User user, long revId) {

        // 일단 유저를 다른 곳에 연관관계로 등록하기 위해 임시로 저장
        userRepository.save(user);

        Revision revokeRev = revisionRepository.findById(revId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVISION_NOT_FOUND));
        Document document = revokeRev.getDocument();
        Revision topRev = revisionRepository.findTop1ByDocumentOrderByIdDesc(document);

        // revokeRev와 topRev의 diff amount를 계산
        String oldText = topRev.getContent().getText();
        String text = revokeRev.getContent().getText();
        Patch<String> patch = DiffUtils.diff(splitIntoLines(oldText), splitIntoLines(text));

        long diffAmount = diffLength(patch);

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

        Patch<String> patch = DiffUtils.diff(splitIntoLines(oldText), splitIntoLines(text));

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

//        System.out.println(diffLength(patch));


//        for (AbstractDelta<String> delta : patch.getDeltas()) {
//            switch (delta.getType()) {
//                case CHANGE:
//                    System.out.println("ChangeDelta: " + delta);
//                    break;
//                case DELETE:
//                    System.out.println("DeleteDelta: " + delta);
//                    break;
//                case INSERT:
//                    System.out.println("InsertDelta: " + delta);
//                    break;
//                case EQUAL:
//                    System.out.println("EqualDelta: " + delta); // Usually not shown in diffs
//                    break;
//            }
//        }

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
            String result = threeWayMerge(oldStrings, newStrings, splitIntoLines(version2));

        } catch (PatchFailedException e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.MERGE_FAILED);
        }

        return patch.getDeltas();
    }

    private List<String> splitIntoLines(String text) {
        return List.of(text.split("\n"));
    }

    /**
     * 글자수 차이를 구합니다.
     *
     * @param patch
     * @return Number of changes
     */
    public <T> int diffLength(Patch<T> patch) {
        int length = 0;
        for (AbstractDelta<T> delta : patch.getDeltas()) {
            Chunk<T> source = null;
            Chunk<T> target = null;
            int decrease = 0;
            int increase = 0;
            switch (delta.getType()) {
                case CHANGE:
                    source = delta.getSource();
                    target = delta.getTarget();
                    decrease = source.getLines().stream().mapToInt(line -> line.toString().length() + 1).sum();
                    increase = target.getLines().stream().mapToInt(line -> line.toString().length() + 1).sum();
                    length += increase - decrease;
                    break;
                case DELETE:
                    source = delta.getSource();
                    System.out.println(delta.getSource());
                    System.out.println(delta.getTarget());
                    decrease = source.getLines().stream().mapToInt(line -> line.toString().length() + 1).sum();
                    length -= decrease;
                    break;
                case INSERT:
                    target = delta.getTarget();
                    System.out.println(delta.getSource());
                    System.out.println(delta.getTarget());
                    increase = target.getLines().stream().mapToInt(line -> line.toString().length() + 1).sum();
                    length += increase;
                    break;
            }
        }
        return length;
    }

    public String threeWayMerge(List<String> base, List<String> versionA, List<String> versionB) throws PatchFailedException {
        Patch<String> patchA = DiffUtils.diff(base, versionA);
//        System.out.println(patchA.getDeltas());
        Patch<String> patchB = DiffUtils.diff(base, versionB);
//        System.out.println(patchB.getDeltas());
//        patchB.withConflictOutput(CUSTOM_CONFLICT_PRODUCES_MERGE_CONFLICT);

        // A를 base에 적용함
//        List<String> merged = DiffUtils.patch(base, patchA);
//        merged = DiffUtils.patch(merged, patchB);
//
//        System.out.println("########################");
//        for (String s : merged) {
//            System.out.println(s);
//        }


//        merged = DiffUtils.patch(base, patchA);
//        System.out.println(merged);

        // 변화가 뒤에서 부터 일어나야지 List 인덱스가 안변함
        List<AbstractDelta<String>> deltasA = patchA.getDeltas();
//        System.out.println(deltasA.stream().map(delta -> delta.getTarget()).collect(Collectors.toList()));
        List<AbstractDelta<String>> deltasB = patchB.getDeltas();

        int indexA = patchA.getDeltas().size() - 1;
        int indexB = patchB.getDeltas().size() - 1;

        // 수정할 수 있는 Delta 객체를 Patch에 저장
        Patch<String> mergeDeltaPatch = new Patch<>();
        // Conflict 나는 Delta 쌍을 List에 저장
        List<Conflict> conflictList = new ArrayList<>();

        while (indexA >= 0 && indexB >= 0) {
            AbstractDelta<String> deltaA = deltasA.get(indexA);
            AbstractDelta<String> deltaB = deltasB.get(indexB);

            Conflict conflict = conflictList.size() > 0 ? conflictList.get(conflictList.size() - 1) : null;
            boolean isExistA = false;
            boolean isExistB = false;
            if (conflict != null) {
                isExistA = conflict.isExistInA(deltaA);
                isExistB = conflict.isExistInB(deltaB);
            }

            if (deltaA.getSource().getPosition() <= deltaB.getSource().last() && deltaA.getSource().last() >= deltaB.getSource().getPosition()) {
                // 겹치는 구간 존재
                // 겹쳤는데 이후 변화가 같음
                if (deltaA.equals(deltaB)) {
                    mergeDeltaPatch.addDelta(deltaA);
                } else if (deltaA.getSource().getPosition() <= deltaB.getSource().getPosition() && isExistB) {
                    conflict.addDeltaA(deltaA);
                } else if (deltaA.getSource().getPosition() > deltaB.getSource().getPosition() && isExistA) {
                    conflict.addDeltaB(deltaB);
                } else {
                    conflictList.add(new Conflict(deltaA, deltaB));
                }
            } else {
                // 안겹침 / 가장 뒤에 있는 것 수정 가능 / 뒤에있는게 conflict에 있으면 안됨
                if (deltaA.getSource().last() < deltaB.getSource().last()) {
                    if (!isExistB)
                        mergeDeltaPatch.addDelta(deltaB);
                } else {
                    if (!isExistA)
                        mergeDeltaPatch.addDelta(deltaA);
                }
            }

            if (deltaA.getSource().getPosition() > deltaB.getSource().getPosition()) {
                indexA--;
            } else {
                indexB--;
            }
//            System.out.println("indexA : " + indexA + ", " + "indexB : " + indexB);
        }

        // mergeDeltaPatch를 적용
        List<String> merged = DiffUtils.patch(base, mergeDeltaPatch);
        applyConflict(merged, conflictList);

//        System.out.println("########################");
//        merged.stream().forEach(System.out::println);

        return merged.stream().collect(Collectors.joining("\n"));
    }

    public void applyConflict(List<String> base, List<Conflict> conflictList) {
        for (Conflict conflict : conflictList) {
            conflict.patch(base);
        }
    }

    public static final ConflictOutput<String> CUSTOM_CONFLICT_PRODUCES_MERGE_CONFLICT = (VerifyChunk verifyChunk, AbstractDelta<String> delta, List<String> result) -> {
        System.out.println("result : " + result);
        if (result.size() > delta.getSource().getPosition()) {
            List<String> orgData = new ArrayList<>();
            System.out.println(delta);
            if (delta.getType() == DeltaType.CHANGE) {
                for (int i = 0; i < delta.getTarget().size(); i++) {
                    orgData.add(result.get(delta.getSource().getPosition()));
                    result.remove(delta.getSource().getPosition());
                }
            } else {
                for (int i = 0; i < delta.getSource().size(); i++) {
                    orgData.add(result.get(delta.getSource().getPosition()));
                    result.remove(delta.getSource().getPosition());
                }
            }


            orgData.add(0, "<<<<<< HEAD");
            orgData.add("======");
            orgData.addAll(delta.getTarget().getLines());
            orgData.add(">>>>>>> PATCH");

            result.addAll(delta.getSource().getPosition(), orgData);

        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
}
