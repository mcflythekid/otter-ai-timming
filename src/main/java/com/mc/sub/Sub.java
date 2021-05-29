package com.mc.sub;

import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Sub {
    private List<Line> lines = new ArrayList<>();

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

    public static void main(String[] args) throws IOException {
        Sub sub = new Sub("cc.srt");
        System.out.println(sub);
    }
}
