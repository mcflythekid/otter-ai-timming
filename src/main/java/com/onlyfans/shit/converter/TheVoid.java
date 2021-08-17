package com.onlyfans.shit.converter;

import com.onlyfans.shit.Line;
import com.onlyfans.shit.Sub;
import com.onlyfans.shit.gui.LogPrinter;

import java.io.IOException;

import static com.onlyfans.shit.util.Utils.generateOutPath;

public class TheVoid {
    private static final int MIN_SPACE_MILLIS = 30;

    private LogPrinter logPrinter;

    public TheVoid(LogPrinter logPrinter) {
        this.logPrinter = logPrinter;
    }

    public void submitAddSpace(String in) throws IOException {
        String out = generateOutPath(in, "space");
        Sub sub = new Sub(in);
        logPrinter.println("Adding space for: " + in);

        convert(sub);
        sub.writeFile(out);
        logPrinter.println("Space added at: " + out);
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
