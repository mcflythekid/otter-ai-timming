package com.mc.sub;

import com.mc.sub.converter.SmartLineConverter;
import com.mc.sub.gui.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void mainx(String[] args) throws IOException, InterruptedException {
        String[] list = new String[]{
                "Anal Sex",
                "Control Your Woman's Self Esteem",
                "CRP Patreon Exclusive- Women Are Like Dogs",
                "Hookers and Escorts",
                "Human Nature Is Real",
                "Keep Your Insights To Yourself",
                "Never Take A Girl Back",
                "The Importance of Fucking a Girl Good",
                "What Are Shit Tests"
        };

        for (String file : list) {
            SmartLineConverter.submit(
                    "C:/Users/nha/Desktop/tmp/otter.ai/" + file + ".srt",
                    "C:/Users/nha/Desktop/tmp/pre-process/" + file + ".srt",
                    150);
            Thread.sleep(50);
        }
    }

    public static void main2(String[] args) throws IOException {
        SmartLineConverter.submit(
                "test.srt",
                "test-out.srt",
                150);
    }

    public static void main(String[] args) {
        new GUI().start();
    }
}
