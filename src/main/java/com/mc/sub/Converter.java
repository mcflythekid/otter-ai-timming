package com.mc.sub;

import java.io.IOException;

public class Converter {
    public static Sub convert(Sub oldSub, int maxChars, String[] splitters) {
        Sub newSub = new Sub();
        int i = 0;
        int count = 0;
        StringBuilder content = new StringBuilder();
        String from = oldSub.getLine(0).getFrom();
        while (i < oldSub.size()) {
            Line line = oldSub.getLine(i);
            count += line.length();
            content.append(line.getContent()).append(" ");
            if (Utils.endWiths(line.getContent(), splitters) && count > maxChars) {
                newSub.add(Line.builder()
                        .from(from).to(line.getTo()).content(content.toString().trim())
                        .build());
                count = 0;
                content = new StringBuilder();
                if (i < oldSub.size() - 1) {
                    from = oldSub.getLine(i + 1).getFrom();
                }
            }
            i++;
        }
        return newSub;
    }

    public static void main(String[] args) throws IOException {
        Sub sub = convert(new Sub("cc.srt"), 100, new String[]{",", "."});
        sub.writeFile("xx.srt");
    }
}
