package com.mc.sub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern TIME_PATTERN = Pattern.compile("^(\\d{1,2}):(\\d{1,2}):(\\d{1,2}),(\\d{1,3})$");

    /**
     * 0:00:39,680
     *
     * @param timeString
     * @return
     */
    private static long getMillis(String timeString) {
        Matcher matcher = TIME_PATTERN.matcher(timeString);
        if (matcher.matches()) {
            long millisInHour = Long.parseLong(matcher.group(1)) * 1000 * 60 * 60;
            long millisInMinute = Long.parseLong(matcher.group(2)) * 1000 * 60;
            long millisInSecond = Long.parseLong(matcher.group(3)) * 1000;
            long millis = Long.parseLong(matcher.group(4));
            return millisInHour + millisInMinute + millisInSecond + millis;
        } else {
            throw new RuntimeException("Bad time: " + timeString);
        }
    }

    public long getFromMillis() {
        return getMillis(from);
    }

    public long getToMillis() {
        return getMillis(to);
    }

    private static final String CALCULATE_TIME_STRING_FORMAT = "%02d:%02d:%02d,%03d";

    private static String calculateTimeString(long millis) {
        return String.format(CALCULATE_TIME_STRING_FORMAT,
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
                TimeUnit.MILLISECONDS.toMillis(millis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis))
        );
    }

    public void updateFrom(long millis) {
        setFrom(calculateTimeString(millis));
    }

    public void updateTo(long millis) {
        setTo(calculateTimeString(millis));
    }

    public boolean isEndWithComma() {
        return content.endsWith(",");
    }

    public boolean containEndingWithOther() {
        return Utils.contains(content, new String[]{".", "!", "?"});
    }

    public boolean isEndWithOther() {
        return Utils.endWiths(content, new String[]{".", "!", "?"});
    }

    public boolean isEnd() {
        if (content == null) {
            return false;
        }
        return Utils.endWiths(content, new String[]{".", ",", "!", "?"});
    }

    public boolean isEndSentence() {
        if (content == null) {
            return false;
        }
        return Utils.endWiths(content, new String[]{".", "!", "?"});
    }

    public boolean isEndSubSentence() {
        if (content == null) {
            return false;
        }
        return Utils.endWiths(content, new String[]{",", ";"});
    }

    public Line(String data) {
        String[] lines = data.split("(\r\n|\r|\n)", -1);
        int timeLineIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

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
                timeLineIndex = i;
                continue;
            }
        }

        if (timeLineIndex == -1) {
            throw new RuntimeException("Bad sub line: " + data);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = timeLineIndex + 1; i < lines.length; i++) {
            sb.append(lines[i].trim()).append("\n");
        }
        content = sb.toString().strip();
    }

    public int length() {
        return content.length();
    }

    public void formatCaps() {
        content = StringUtils.capitalize(content);
    }

    public void formatFinal() {
        if (content.endsWith(",")) {
            //content = content.substring(0, content.length() - 1);
        }
    }
}
