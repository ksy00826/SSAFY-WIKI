package com.bdos.ssafywiki.diff;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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

    public String threeWayMerge(List<String> base, List<String> versionA, List<String> versionB) throws PatchFailedException {
        Patch<String> patchA = DiffUtils.diff(base, versionA);
        Patch<String> patchB = DiffUtils.diff(base, versionB);

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
}
