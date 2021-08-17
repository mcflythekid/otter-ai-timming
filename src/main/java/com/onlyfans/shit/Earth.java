package com.onlyfans.shit;

import com.onlyfans.shit.util.Barn;

import java.util.List;
import java.util.stream.Collectors;

public class Earth {
    public static boolean FINALIZE_REMOVE_COMMA = true;
    public static boolean FINALIZE_ADD_SMART_BREAKER = false;
    public static String FINALIZE_SMART_BREAKER = "_";

    public static List<String> PAUSES;
    public static List<String> PAUSES_TEST;

    static {
        try {
            PAUSES = Barn.readAllLineFromResource("kiss.properties")
                    .stream().filter(s -> !s.startsWith("#")).collect(Collectors.toList());

            PAUSES_TEST = Barn.readAllLineFromResource("qc.properties")
                    .stream().filter(s -> !s.startsWith("#")).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
