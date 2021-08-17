package com.onlyfans.shit.util;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Barn {


    private static final String DIR_SPLIT = "/";

    public static List<String> readAllLineFromResource(String rsPath) throws IOException, URISyntaxException {
        String rs = rsPath;
        if (!rs.startsWith(DIR_SPLIT)) {
            rs = DIR_SPLIT + rs;
        }
        InputStream in = Barn.class.getResourceAsStream(rs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        List<String> output = new ArrayList<>();
        while (reader.readLine() != null) {
            output.add(reader.readLine());
        }
        return output;
    }

    public static String extractContainsArrayElement(String source, List<String> elements) {
        for (String element : elements) {
            if (source.contains(element)) {
                return element;
            }
        }
        return null;
    }

    public static String extractEndWithsArrayElement(String source, List<String> elements) {
        for (String element : elements) {
            if (source.endsWith(element)) {
                return element;
            }
        }
        return null;
    }

    public static boolean stringContainArrayElement(String source, List<String> elements) {
        for (String element : elements) {
            if (source.contains(element)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> filesPathList(String folderPath) {
        List<String> output = new ArrayList<>();
        File folder = new File(folderPath);
        for (File f : folder.listFiles()) {
            String fAbsolutePath = f.getAbsolutePath();
            if (f.isDirectory()) {
                output.addAll(filesPathList(fAbsolutePath));
            } else {
                output.add(fAbsolutePath);
            }
        }
        return output;
    }

    public static String removeLastChar(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        return content.substring(0, content.length() - 1);
    }
}
