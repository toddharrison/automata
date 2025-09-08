package com.eharrison.automata.util;

public class Verify {
    public static void require(final boolean require, final String message) {
        if (!require) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void check(final boolean check, final String message) {
        if (!check) {
            throw new IllegalStateException(message);
        }
    }
}
