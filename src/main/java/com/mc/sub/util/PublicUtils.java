package com.mc.sub.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PublicUtils {




    private static final String DIR_SPLIT = "/";

    public static List<String> readAllLineFromResource(String rsPath) throws IOException, URISyntaxException {
        String rs = rsPath;
        if (!rs.startsWith(DIR_SPLIT)) {
            rs = DIR_SPLIT + rs;
        }
        URL url = PublicUtils.class.getResource(rs);
        Path path = Paths.get(url.toURI());
        List<String> result = Files.readAllLines(path);
        return result;
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
