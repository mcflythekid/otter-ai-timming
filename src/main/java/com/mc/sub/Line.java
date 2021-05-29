package com.mc.sub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Line {
    private Integer index;
    private String from;
    private String to;
    private String content;

    public boolean isEndWithComma(){
        return content.endsWith(",");
    }

    public boolean containEndingWithOther(){
        return Utils.contains(content, new String[]{".", "!", "?"});
    }

    public boolean isEndWithOther(){
        return Utils.endWiths(content, new String[]{".", "!", "?"});
    }

    public boolean isEnd() {
        if (content == null) {
            return false;
        }
        return Utils.endWiths(content, new String[]{".", ",", "!", "?"});
    }

    public Line(String data) {
        String[] lines = data.split("(\r\n|\r|\n)", -1);
        for (String line : lines) {
            if (isBlank(line)) {
                continue;
            }
            if (index == null) {
                index = Integer.parseInt(line);
                continue;
            }
            if (from == null) {
                String[] arr = line.split(" --> ");
                from = arr[0];
                to = arr[1];
                continue;
            }
            if (content == null) {
                content = line;
                continue;
            }
        }
    }

    public int length() {
        return content.length();
    }
}
