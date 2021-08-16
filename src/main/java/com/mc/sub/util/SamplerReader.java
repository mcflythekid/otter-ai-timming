package com.mc.sub.util;

import com.mc.sub.Line;
import com.mc.sub.Sub;
import com.mc.sub.converter.SmartLineConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mc.sub.util.PublicUtils.*;

public class SamplerReader {

    public static List<Sub> readSampler(String folderPaths) throws IOException {
        List<String> srtPaths = PublicUtils.filesPathList(folderPaths).stream()
                .filter(s -> s.endsWith(".srt")).collect(Collectors.toList());

        List<Sub> output = new ArrayList<>();
        for (String strPath : srtPaths) {
            output.add(new Sub(strPath));
        }
        return output;
    }

    private static List<String> PAUSES;

    static {
        try {
            PAUSES = PublicUtils.readAllLineFromResource("pauses.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void testASub(Sub convertedSub){
        int size = convertedSub.size();
        for (int i = 0; i < size; i++) {
            Line line = convertedSub.getLine(i);

            String contentLow = line.getContent().toLowerCase();

            String contentLowEndWithLetter = removeAllLastCharNotLetterAndNumber(contentLow);
            String endWithsPause = extractEndWithsArrayElement(contentLowEndWithLetter, PAUSES);
            if (endWithsPause != null){
                String colorPause = Color.ANSI_GREEN + endWithsPause + Color.ANSI_RESET;
                String str = contentLow.replace(endWithsPause, colorPause);

                if (i < size - 1){
                    str = str + " === " + convertedSub.getLine(i+1).getContent();
                }
                System.out.println(str);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        int maxChars = 80;
        SmartLineConverter.submitDir("sampler-source", "sampler", maxChars);

        List<Sub> subs = readSampler("sampler");
        for (Sub sub : subs){
            testASub(SmartLineConverter.convert(sub, maxChars));
        }
    }

    public static String removeAllLastCharNotLetterAndNumber(String str) {
        char lastChar = str.charAt(str.length() - 1);
        if (!Character.isLetter(lastChar) && !Character.isDigit(lastChar)) {
            return removeAllLastCharNotLetterAndNumber(removeLastChar(str));
        }
        return str;
    }

}
