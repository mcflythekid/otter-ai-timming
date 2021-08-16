package com.mc.sub;

import com.mc.sub.util.Utils;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Sub {
    private List<Line> lines = new ArrayList<>();

    public Line getLastLine(){
        try {
            return lines.get(size() - 1);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int getNextLineIndex() {
        return lines.size() + 1;
    }

    private static final String BR = "\r\n";

    public void writeFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Line line : lines) {
            sb.append(line.getIndex()).append(BR);
            sb.append(line.getFrom()).append(" --> ").append(line.getTo()).append(BR);
            sb.append(line.getContent()).append(BR);
            sb.append(BR);
        }
        Utils.writeFile(path, sb.toString().trim());
    }

    public int size() {
        return lines.size();
    }

    public void add(Line line) {
        line.setIndex(getNextLineIndex());
        lines.add(line);
    }

    public Sub() {
    }

    public Line getLine(int index) {
        return lines.get(index);
    }

    public Sub(String path) throws IOException {
        String content = Utils.readFile(path);
        String[] blocks = content.split("(?m)^\\s*$");
        for (String block : blocks) {
            if (block.trim().equals("")) {
                continue;
            }
            Line line = new Line(block);
            lines.add(line);
        }
    }

    public Line extractLine(int from, int to) {
        Sub sub = this;

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

    public Line extractLine() {
        int from = 0;
        int to = this.size() - 1;
        return this.extractLine(from, to);
    }

    public int countChar(){
        return Utils.countCharUse(extractLine().getContent());
    }

    public boolean isEndSentence(){
        return this.extractLine().isEndSentence();
    }
}
