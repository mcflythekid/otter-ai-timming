package com.mc.sub.converter;

import com.mc.sub.Line;
import com.mc.sub.Sub;
import com.mc.sub.gui.LogPrinter;

import java.io.IOException;

import static com.mc.sub.util.Utils.generateOutPath;

public class AddSpaceConverter {
    private static final int MIN_SPACE_MILLIS = 30;

    private LogPrinter logPrinter;

    public AddSpaceConverter(LogPrinter logPrinter) {
        this.logPrinter = logPrinter;
    }

    public void submitAddSpace(String inPath) throws IOException {
        String outPath = generateOutPath(inPath, "space");
        Sub sub = new Sub(inPath);
        logPrinter.println("Adding space for: " + inPath);

        convert(sub);
        sub.writeFile(outPath);
        logPrinter.println("Space added at: " + outPath);
    }

    private void convert(Sub subRef) {
        int index = 0;
        while (index < subRef.size()) {
            Line currentLine = subRef.getLine(index);
            if (index == subRef.size() - 1) {
                logPrinter.println("Ignore last line");
                break;
            }
            Line nextLine = subRef.getLine(index + 1);

            long a = currentLine.getToMillis();
            long b = nextLine.getFromMillis();

            boolean changed = false;
            while (b - a < MIN_SPACE_MILLIS) {
                a -= 1;
                b += 1;
                logPrinter.println(currentLine.getIndex() + " Adjusting: " + a + " - " + b);
                changed = true;
            }
            if (changed) {
                currentLine.updateTo(a);
                nextLine.updateFrom(b);
            }

            index++;
        }
    }


}
