package com.onlyfans.shit.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static String getSlash() {
        return System.getProperty("file.separator");
    }

    public static String removeBomMarker(String s) {
        if (s == null) {
            return null;
        }
        return s.replace("\uFEFF", "");
    }

    public static String getDesktop() {
        return System.getProperty("user.home") + getSlash() + "Desktop";
    }

    public static String getDirFromPath(String path) {
        return new File(path).getParent();
    }

    public static String getFileNameWithoutExtFromPath(String path) {
        return FilenameUtils.getBaseName(new File(path).getName());
    }

    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String s = new String(encoded, StandardCharsets.UTF_8);
        return removeBomMarker(s);
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

    public static boolean contains(String str, String[] marks) {
        for (String mark : marks) {
            if (str.contains(mark)) {
                return true;
            }
        }
        return false;
    }

    private static final char[] NONE_CHAR = new char[]{' ', ',', ';'};

    public static int countCharUse(String str1) {
        int length = str1.length();
        int count = 0;
        for (int i = 0; i < str1.length(); i++) {
            if (ArrayUtils.contains(NONE_CHAR, str1.charAt(i))) {
                count++;
            }
        }
        return length - count;
    }

    public static String generateOutPath(String inPath, String suffix) {
        String dir = Utils.getDirFromPath(inPath);
        String fileName = Utils.getFileNameWithoutExtFromPath(inPath);
        return dir + Utils.getSlash() + fileName + "-" + suffix + ".srt";
    }

    public static String removeAllLastCharNotLetterAndNumber(String str) {
        char lastChar = str.charAt(str.length() - 1);
        if (!Character.isLetter(lastChar) && !Character.isDigit(lastChar)) {
            return removeAllLastCharNotLetterAndNumber(Barn.removeLastChar(str));
        }
        return str;
    }
}
