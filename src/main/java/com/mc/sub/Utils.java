package com.mc.sub;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    public static final String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public static void writeFile(String path, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(content);
        fileWriter.close();
    }

    public static boolean endWiths(String str, String[] marks) {
        for (String mark : marks) {
            if (str.endsWith(mark)) {
                return true;
            }
        }
        return false;
    }
}
