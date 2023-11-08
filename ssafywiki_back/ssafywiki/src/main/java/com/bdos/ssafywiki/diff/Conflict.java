package com.bdos.ssafywiki.diff;

import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Chunk;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
public class Conflict {
    private List<AbstractDelta<String>> deltasA;
    private List<AbstractDelta<String>> deltasB;
    private int start;
    private int last;

    public Conflict(AbstractDelta<String> deltaA, AbstractDelta<String> deltaB) {
        deltasA = new ArrayList<>();
        deltasB = new ArrayList<>();
        deltasA.add(deltaA);
        deltasB.add(deltaB);
        if (deltaA.getSource().getPosition() <= deltaB.getSource().getPosition()) {
            start = deltaA.getSource().getPosition();
        } else {
            start = deltaB.getSource().getPosition();
        }
        if (deltaA.getSource().last() > deltaB.getSource().last()) {
            last = deltaA.getSource().last();
        } else {
            last = deltaB.getSource().last();
        }
    }

    public void addDeltaA(AbstractDelta<String> delta) {
        deltasA.add(delta);
        start = delta.getSource().getPosition();
    }

    public void addDeltaB(AbstractDelta<String> delta) {
        deltasB.add(delta);
        start = delta.getSource().getPosition();
    }

    public boolean isExistInA(AbstractDelta<String> delta) {
        if (deltasA.get(deltasA.size() - 1).equals(delta)) {
            return true;
        }
        return false;
    }

    public boolean isExistInB(AbstractDelta<String> delta) {
        if (deltasB.get(deltasB.size() - 1).equals(delta)) {
            return true;
        }
        return false;
    }

    public void patch(List<String> base) {
        List<String> subBase = new ArrayList<>(base.subList(start, last + 1));
        List<String> orgData = new ArrayList<>(subBase);

        // version A
        for (AbstractDelta<String> delta : deltasA) {
            Chunk<String> source = delta.getSource();
            Chunk<String> target = delta.getTarget();
            // 원본 지우고
            for (int i = 0; i < source.size(); i++)
                orgData.remove(source.getPosition() - start);
            // 변경된 것 채우기
            orgData.addAll(source.getPosition() - start, target.getLines());
        }
        // version B
        for (AbstractDelta<String> delta : deltasB) {
            Chunk<String> source = delta.getSource();
            Chunk<String> target = delta.getTarget();
            // 원본 지우고
            for (int i = 0; i < source.size(); i++)
                subBase.remove(source.getPosition() - start);
            // 변경된 것 채우기
            subBase.addAll(source.getPosition() - start, target.getLines());
        }

        orgData.add(0, "`<<<<<< HEAD`");
        orgData.add("`======`");
        orgData.addAll(subBase);
        orgData.add("`>>>>>>> PATCH`");

        for (int i = 0; i < last + 1 - start; i++) {
            base.remove(start);
        }
        base.addAll(start, orgData);
    }
}
