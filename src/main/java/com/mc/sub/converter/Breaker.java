package com.mc.sub.converter;

import com.mc.sub.Line;

@FunctionalInterface
public interface Breaker {
    boolean isBreak(Line line);
}
