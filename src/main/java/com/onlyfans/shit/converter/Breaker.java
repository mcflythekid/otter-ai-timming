package com.onlyfans.shit.converter;

import com.onlyfans.shit.Line;

@FunctionalInterface
public interface Breaker {
    boolean isBreak(Line line);
}
