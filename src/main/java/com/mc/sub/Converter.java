package com.mc.sub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    private static Line tmp(int from, int to, Sub sub) {
        Line fromLine = sub.getLine(from);
        Line toLine = sub.getLine(to);
        StringBuilder content = new StringBuilder();
        for (int k = from; k <= to; k++) {
            content.append(sub.getLine(k).getContent()).append(" ");
        }
        Line newLine = Line.builder()
                .index(to)
                .from(fromLine.getFrom())
                .to(toLine.getTo())
                .content(content.toString().trim())
                .build();
        return newLine;
    }

    public static void submit(String in, String out, int length) throws IOException {
        Sub sub = convert(new Sub(in), length);
        sub.writeFile(out);
        System.err.println("File written: " + out);
    }

    private static Sub convert(Sub oldSub, int maxChars) {
        Sub newSub = new Sub();

        System.out.println("Size = " + oldSub.size());

        int fromIndex = 0;
        int i = 0;
        List<Line> candidateLines = new ArrayList<>();
        while (i < oldSub.size()) {
            Line tmp = oldSub.getLine(i);
            if (tmp.isEnd() || i == oldSub.size() - 1) {
                Line finalLine = null;
                Line candidateLine = tmp(fromIndex, i, oldSub);
                candidateLines.add(candidateLine);
                if ((candidateLine.isEndWithOther() && candidateLine.length() > maxChars) || i == oldSub.size() - 1) {
                    if (candidateLines.size() == 1 || candidateLines.size() == 2) {
                        finalLine = candidateLines.get(0);
                    } else {
                        //
                        //if (!justBreak){
                            for (int j = candidateLines.size() - 2; j >= 0; j--){
                                Line tmpx = candidateLines.get(j);
                                if (tmpx.isEndWithOther()){
                                    finalLine = tmpx;
                                    break;
                                }
                            }
                        if (finalLine == null){
                            int last = candidateLines.size() - 1;
                            finalLine = candidateLines.get(last/2);
                            finalLine.setContent(finalLine.getContent() + "...");
                        }
                        //

                    }
                    candidateLines = new ArrayList<>();
                    i = finalLine.getIndex();
                    fromIndex = i + 1;
                    newSub.add(finalLine);
                }
            }
            i++;
        }
        return newSub;
    }
}
