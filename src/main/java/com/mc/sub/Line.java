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
                String[] arr = line.split("-->");
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

    public int length(){
        return content.length();
    }

    public static void main(String[] args) {
        String data = "\n" +
                "3668\n" +
                "00:20:48,090 --> 00:20:48,450\n" +
                "nerdy\n";
        Line line = new Line(data);
        System.out.println(line);
    }
}
