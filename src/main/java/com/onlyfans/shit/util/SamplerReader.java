package com.onlyfans.shit.util;

import com.onlyfans.shit.Earth;
import com.onlyfans.shit.converter.TheSmart;
import com.onlyfans.shit.Line;
import com.onlyfans.shit.Sub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.onlyfans.shit.util.Utils.removeAllLastCharNotLetterAndNumber;

public class SamplerReader {

    public static List<Sub> readSampler(String folderPaths) throws IOException {
        List<String> srtPaths = Barn.filesPathList(folderPaths).stream()
                .filter(s -> s.endsWith(".srt")).collect(Collectors.toList());

        List<Sub> output = new ArrayList<>();
        for (String strPath : srtPaths) {
            Sub sub = new Sub(strPath);
            sub.setName(strPath);
            output.add(sub);
        }
        return output;
    }

    private static int count = 0;

    private static final Pattern PAUSE_FREE_PATTERN = Pattern.compile(",\\s([a-z]+)[^a-z]?$");

    private static String extractPause(String str) {
        Matcher matcher = PAUSE_FREE_PATTERN.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static void testASub(Sub convertedSub) {
        int size = convertedSub.size();
        for (int i = 0; i < size; i++) {
            Line line = convertedSub.getLine(i);

            String contentLow = line.getContent().toLowerCase();

            String contentLowEndWithLetter = removeAllLastCharNotLetterAndNumber(contentLow);
            String lastWord = extractPause(contentLowEndWithLetter);
            if (lastWord != null && !Earth.PAUSES.contains(lastWord)) {
                String colorPause = Color.ANSI_GREEN + lastWord + Color.ANSI_RESET;
                String str = contentLow.replace(lastWord, colorPause);

                if (i < size - 1) {
                    str = str + " === " + convertedSub.getLine(i + 1).getContent();
                }
                System.out.println(str);
                count++;
            }

//            String contentLowEndWithLetter = removeAllLastCharNotLetterAndNumber(contentLow);
//            String endWithsPause = extractEndWithsArrayElement(contentLowEndWithLetter, PAUSES_TEST);
//            if (endWithsPause != null) {
//                String colorPause = Color.ANSI_GREEN + endWithsPause + Color.ANSI_RESET;
//                String str = contentLow.replace(endWithsPause, colorPause);
//
//                if (i < size - 1) {
//                    str = str + " === " + convertedSub.getLine(i + 1).getContent();
//                }
//                System.out.println(convertedSub.getName() + "\n"
//                        + line.getFrom() + "\n"
//                        + str + "\n");
//                count++;
//            }

        }
    }

    public static void main(String[] args) throws IOException {
        int maxChars = 80;
        TheSmart.submitDir("sampler-source", "sampler", maxChars);

        List<Sub> subs = readSampler("sampler");

        for (Sub sub : subs) {
            testASub(TheSmart.convert(sub, maxChars));
        }

        System.out.println("----------");
        System.out.println(count);
    }


}
