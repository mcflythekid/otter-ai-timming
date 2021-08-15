package com.mc.sub;

import com.mc.sub.converter.SmartLineConverter;
import com.mc.sub.gui.GUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SmartLineConverter.submit(
                "C:\\Users\\nha\\Desktop\\a.srt",
                "C:\\Users\\nha\\Desktop\\b.srt",
                50);
    }

    public static void main2(String[] args) {
        new GUI().start();
    }
}
