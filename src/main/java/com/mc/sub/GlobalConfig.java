package com.mc.sub;

import com.mc.sub.util.PublicUtils;

import java.util.List;
import java.util.stream.Collectors;

public class GlobalConfig {
    public static boolean FINALIZE_REMOVE_COMMA = false;

    //!!!!!!!!
    public static boolean FINALIZE_ADD_SMART_BREAKER = false;
    public static String FINALIZE_SMART_BREAKER = "_";

    public static List<String> PAUSES;
    public static List<String> PAUSES_TEST;

    static {
        try {
            PAUSES = PublicUtils.readAllLineFromResource("pauses.properties")
                    .stream().filter(s -> !s.startsWith("#")).collect(Collectors.toList());

            PAUSES_TEST = PublicUtils.readAllLineFromResource("pauses_test.properties")
                    .stream().filter(s -> !s.startsWith("#")).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
