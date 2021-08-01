package com.mc.sub.converter;

import com.mc.sub.Line;
import com.mc.sub.Sub;
import com.mc.sub.Utils;
import com.mc.sub.gui.LogPrinter;

import java.io.IOException;

public class AddSpaceConverter {
    private static final int TIME_LEAD_TAIL_IN_MILLIS = 20;

    private LogPrinter logPrinter;

    public AddSpaceConverter(LogPrinter logPrinter) {
        this.logPrinter = logPrinter;
    }

    public void submitAddSpace(String in, String out, int millisSecondsSpace) throws IOException {
        Sub sub = convert(new Sub(in), millisSecondsSpace);
        sub.writeFile(out);
        System.err.println("File written: " + out);
    }

    public void submitAddSpace(String inPath) throws IOException {
        String outPath = generateOutPath(inPath);

        logPrinter.println("Adding space for: " + inPath);

        Sub sub = convert(new Sub(inPath), TIME_LEAD_TAIL_IN_MILLIS);
        sub.writeFile(outPath);
        logPrinter.println("Space added at: " + outPath);
    }

    private Sub convert(Sub oldSub, int millisSecondsSpace) {
        Sub newSub = new Sub();

        int index = 0;
        while (index < oldSub.size()) {
            Line currentLine = oldSub.getLine(index);

            // Don't have next line (In the end)
            Line nextLine = index < oldSub.size() - 1 ? oldSub.getLine(index + 1) : null;
            if (nextLine == null) {
                newSub.add(currentLine);
                break;
            }

            logPrinter.println(currentLine);
            index++;
        }
        return newSub;
    }

    private static String generateOutPath(String inPath){
        String dir = Utils.getDirFromPath(inPath);
        String fileName = Utils.getFileNameWithoutExtFromPath(inPath);
        return dir + Utils.getSlash() + fileName + "-space.srt";
    }
}
