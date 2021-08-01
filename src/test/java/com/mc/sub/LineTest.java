package com.mc.sub;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineTest {

    private static final String WITH_1_LINES = "\n1\n" +
            "11:11:11,111 --> 22:22:22,222\n" +
            "l1\n" +
            "\n\n";

    private static final String WITH_2_LINES = "\n1\n" +
            "11:11:11,111 --> 22:22:22,222\n" +
            "l1\n" +
            "l2\n" +
            "\n\n";

    private static final String WITH_3_LINES = "\n1\n" +
            "11:11:11,111 --> 22:22:22,222\n" +
            "l1\n" +
            "l2\n" +
            "l3\n" +
            "\n\n";

    private static final String WITH_MIDDLE_BLANK_LINES = "\n1\n" +
            "11:11:11,111 --> 22:22:22,222\n" +
            "l1\n" +
            "\n" +
            "l2\n" +
            "\n\n";

    @Test
    public void test1Line(){
        Line line = new Line(WITH_1_LINES);
        assertEquals("l1", line.getContent());
    }

    @Test
    public void test2Line(){
        Line line = new Line(WITH_2_LINES);
        assertEquals("l1\nl2", line.getContent());
    }

    @Test
    public void test3Line(){
        Line line = new Line(WITH_3_LINES);
        assertEquals("l1\nl2\nl3", line.getContent());
    }

    @Test
    public void testMiddleBlank(){
        Line line = new Line(WITH_MIDDLE_BLANK_LINES);
        assertEquals("l1\n\nl2", line.getContent());
    }
}