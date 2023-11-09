package com.bdos.ssafywiki.diff;

import com.bdos.ssafywiki.exception.ExceptionCode;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.*;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyDiffUtils {
    public List<String> splitIntoLines(String text) {
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

    public MergeDto threeWayMerge(List<String> base, List<String> versionA, List<String> versionB) throws PatchFailedException {
        Patch<String> patchA = DiffUtils.diff(base, versionA);
        Patch<String> patchB = DiffUtils.diff(base, versionB);

        // 변화가 뒤에서 부터 일어나야지 List 인덱스가 안변함
        List<AbstractDelta<String>> deltasA = patchA.getDeltas();
        List<AbstractDelta<String>> deltasB = patchB.getDeltas();
//        System.out.println("#################");
//        System.out.println(deltasA);
//        System.out.println(deltasB);
//        System.out.println("#################");

        int indexA = patchA.getDeltas().size() - 1;
        int indexB = patchB.getDeltas().size() - 1;

        // 수정할 수 있는 Delta 객체를 Stack에 저장
        ArrayDeque<AbstractDelta<String>> modifiedDeltaStack = new ArrayDeque<>();

        // Conflict 나는 Delta 쌍을 List에 저장
        List<Conflict> conflictList = new ArrayList<>();

        while (indexA >= 0 && indexB >= 0) {
            AbstractDelta<String> deltaA = deltasA.get(indexA);
            AbstractDelta<String> deltaB = deltasB.get(indexB);
//            System.out.println(deltaA.getSource());
//            System.out.println(deltaB.getSource());

            Conflict conflict = conflictList.size() > 0 ? conflictList.get(conflictList.size() - 1) : null;
//            System.out.println("conflict: " + conflict);

            boolean isExistA = false;
            boolean isExistB = false;
            if (conflict != null) {
                isExistA = conflict.isExistInA(deltaA);
                isExistB = conflict.isExistInB(deltaB);
            }

//            System.out.println("A position: " + deltaA.getSource().getPosition() + " A last: " + deltaA.getSource().last());
//            System.out.println("B position: " + deltaB.getSource().getPosition() + " B last: " + deltaB.getSource().last());

            if (deltaA.getSource().getPosition() <= (DeltaType.INSERT.equals(deltaB.getType()) ? deltaB.getSource().last() + 1 : deltaB.getSource().last()) &&
                    (DeltaType.INSERT.equals(deltaA.getType()) ? deltaA.getSource().last() + 1 : deltaA.getSource().last()) >= deltaB.getSource().getPosition()) {
                // 겹치는 구간 존재
                // 겹쳤는데 이후 변화가 같음
//                System.out.println("겹치는 구간 존재");
//                System.out.println("A와 B의 변화가 같나요?: " + deltaA.equals(deltaB));

                if (deltaA.equals(deltaB)) {
//                    mergeDeltaPatch.addDelta(deltaA);
                    modifiedDeltaStack.push(deltaA);
                } else if (deltaA.getSource().getPosition() <= deltaB.getSource().getPosition() && isExistB) {
                    conflict.addDeltaA(deltaA);
                } else if (deltaA.getSource().getPosition() > deltaB.getSource().getPosition() && isExistA) {
                    conflict.addDeltaB(deltaB);
                } else {
                    conflictList.add(new Conflict(deltaA, deltaB));
                }
            } else {
                // 안겹침 / 가장 뒤에 있는 것 수정 가능 / 뒤에있는게 conflict에 있으면 안됨
                if ((DeltaType.INSERT.equals(deltaA.getType()) ? deltaA.getSource().last() + 1 : deltaA.getSource().last()) < (DeltaType.INSERT.equals(deltaB.getType()) ? deltaB.getSource().last() + 1 : deltaB.getSource().last())) {
                    if (!isExistB)
//                        mergeDeltaPatch.addDelta(deltaB);
                        modifiedDeltaStack.push(deltaB);
                } else {
                    if (!isExistA)
//                        mergeDeltaPatch.addDelta(deltaA);
                        modifiedDeltaStack.push(deltaA);
                }
            }

            if (deltaA.getSource().getPosition() > deltaB.getSource().getPosition()) {
                indexA--;
            } else {
                indexB--;
            }
        }

        // 남은 변경사항은 수정 가능한 것
        Conflict conflict = conflictList.size() > 0 ? conflictList.get(conflictList.size() - 1) : null;

        while (indexA >= 0) {
            AbstractDelta<String> deltaA = deltasA.get(indexA);
            if (conflict == null || !conflict.isExistInA(deltaA))
                modifiedDeltaStack.push(deltaA);
            indexA--;
        }
        while (indexB >= 0) {
            AbstractDelta<String> deltaB = deltasB.get(indexB);
            if (conflict == null || !conflict.isExistInB(deltaB))
                modifiedDeltaStack.push(deltaB);
            indexB--;
        }

        // 수정할 수 있는 Delta 객체를 Patch에 저장
        Patch<String> mergeDeltaPatch = new Patch<>();
        while (!modifiedDeltaStack.isEmpty()) {
            mergeDeltaPatch.addDelta(modifiedDeltaStack.pop());
        }

//        System.out.println("############3-way-merge#############");
//        System.out.println("mergeDeltaPatch: " + mergeDeltaPatch);
//        System.out.println("conflictList: " + conflictList);

        // mergeDeltaPatch를 적용
        List<String> merged = DiffUtils.patch(base, mergeDeltaPatch);
        applyConflict(merged, conflictList);

        return new MergeDto(merged.stream().collect(Collectors.joining("\n")), conflictList.size() > 0 ? ExceptionCode.MERGE_CONFLICT : null);
    }

    public void applyConflict(List<String> base, List<Conflict> conflictList) {
        for (Conflict conflict : conflictList) {
            conflict.patch(base);
        }
    }
}
