package com.mc.sub.converter;

import com.mc.sub.Line;
import com.mc.sub.Sub;
import com.mc.sub.gui.LogPrinter;
import com.mc.sub.util.PublicUtils;
import com.mc.sub.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mc.sub.GlobalConfig.FINALIZE_ADD_SMART_BREAKER;
import static com.mc.sub.GlobalConfig.FINALIZE_SMART_BREAKER;

public class SmartLineConverter {

    private static LogPrinter logPrinter;

    private static void println(String msg) {
        System.out.println(msg);
        if (logPrinter != null) {
            logPrinter.println(msg);
        }
    }

    public static void submitDir(String inDir, String outDir, int length) throws IOException {
        List<String> srtPaths = PublicUtils.filesPathList(inDir).stream()
                .filter(s -> s.endsWith(".srt")).collect(Collectors.toList());

        for (String strPath : srtPaths) {
            Sub sub = convert(new Sub(strPath), length);
            String fileNameNoExt = Utils.getFileNameWithoutExtFromPath(strPath);
            String out = outDir + Utils.getSlash() + fileNameNoExt + "-joined.srt";
            sub.writeFile(out);
        }
    }

    public static void submit(String in, String out, int length) throws IOException {
        Sub sub = convert(new Sub(in), length);
        sub.writeFile(out);
        println("File written: " + out);
    }

    private static List<Sub> extractSentences(Sub sub) {
        return extractByBreak(sub, line -> line.isEndSentence());
    }

    private static List<Sub> extractSubSentences(Sub sub) {
        return extractByBreak(sub, line -> line.isEndSubSentence());
    }

    private static List<Sub> extractByBreak(Sub sub, Breaker breaker) {
        List<Sub> subs = new ArrayList<>();

        int size = sub.size();
        int i = 0;
        Sub tmpSub = new Sub();

        for (; i < size; i++) {
            Line line = sub.getLine(i);
            tmpSub.add(line);

            if (breaker.isBreak(line) || i == size - 1) {
                subs.add(tmpSub);
                tmpSub = new Sub();
            }
        }

        subs.forEach(s -> s.setExtractedByBreaker(true));
        return subs;
    }

    public static Sub convert(Sub source, int maxChars) {
        Sub output = new Sub();
        output.setName(source.getName() + " - JOINED");

        List<Sub> sentences = extractSentences(source); // Extracted sentences (   End with . ? ! )

        List<Sub> blocks = new ArrayList<>();
        for (Sub sentence : sentences) {
            List<Sub> longBlocks = extractSubSentences(sentence); // Extracted blocks (End with , ;)
            List<Sub> shortBlocks = new ArrayList<>();
            for (Sub longBlock : longBlocks) {
                shortBlocks.addAll(splitBlockByTimegap(longBlock, maxChars));
            }
            List<Sub> joinedShortBlock = joinBlock(shortBlocks, maxChars);

            blocks.addAll(joinedShortBlock);
        }

        Line lastLine = null;
        for (Sub block : blocks) {
            Line line = block.extractLine();

            if (lastLine != null && lastLine.isEndSentence()) {
                line.formatCaps();
            }
            line.formatFinal();

            lastLine = line;
            output.add(line);
        }
        return output;
    }

    private static List<Sub> joinBlock(List<Sub> blocks, int maxChars) {
        int size = blocks.size();

        for (int i = 0; i < size - 1; i++) {
            Sub curr = blocks.get(i);
            Sub next = blocks.get(i + 1);

            Sub joined = join2(curr, next);
            if (joined.countChar() <= maxChars) {

                boolean nextIsAPause = next.isAPause(curr);
                if (nextIsAPause) {
                    println("Skipping a pause from being merged: [" + next.extractLine().getContent() + "]");
                } else {
                    List<Sub> output = new ArrayList<>();
                    for (int j = 0; j < i; j++) {
                        output.add(blocks.get(j));
                    }
                    output.add(joined);
                    for (int k = i + 2; k < size; k++) {
                        output.add(blocks.get(k));
                    }

                    return joinBlock(output, maxChars);
                }
            }
        }

        return blocks;
    }

    private static Sub join2(Sub s1, Sub s2) {
        Sub sub = new Sub();
        sub.getLines().addAll(s1.getLines());
        sub.getLines().addAll(s2.getLines());
        return sub;
    }

    private static List<Sub> splitBlockByTimegap(Sub sub, int maxChars) {
        // Done if not longer than condition
        if (sub.countChar() <= maxChars) {
            return Arrays.asList(sub);
        }

        List<Sub> splits = splitInto2(sub);
        List<Sub> output = new ArrayList<>();
        for (Sub split : splits) {
            output.addAll(splitBlockByTimegap(split, maxChars));
        }
        return output;
    }

    private static List<Sub> splitInto2(Sub sub) {
        // Get delimiter
        int markedIndex = 0;
        long maxGap = -1;
        for (int i = 0; i < sub.size() - 1; i++) {
            Line currentLine = sub.getLine(i);
            Line nextLine = sub.getLine(i + 1);
            long currentGap = nextLine.getFromMillis() - currentLine.getToMillis();
            if (currentGap > maxGap) {
                maxGap = currentGap;
                markedIndex = i + 1; // Use next line index
            }
        }

        // Get x1 - x2
        Sub x1 = new Sub();
        Sub x2 = new Sub();
        for (int i = 0; i < sub.size(); i++) {
            Line currentLine = sub.getLine(i);
            if (i < markedIndex) {
                x1.add(currentLine);
            } else {
                x2.add(currentLine);
            }
        }

        List<Sub> splits = new ArrayList<>();

        if (FINALIZE_ADD_SMART_BREAKER && x1.size() > 0) {
            x1.getLastLine().setContent(x1.getLastLine().getContent() + FINALIZE_SMART_BREAKER);
        }

        splits.add(x1);
        splits.add(x2);
        return splits;
    }
}
