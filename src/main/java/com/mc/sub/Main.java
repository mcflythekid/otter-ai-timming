package com.mc.sub;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String file="What Are Shit Tests";
        Converter.submit(
                "C:/Users/nha/Desktop/tmp/otter.ai/" + file + ".srt",
                "C:/Users/nha/Desktop/tmp/pre-process/" + file + ".srt",
                150);
    }
}
