package com.eharrison.automata.game;

import java.util.List;

public interface Game<C extends Config, S extends State, V extends View, A extends Action, R extends Result, B extends Bot<V, A>> {
    String getName();

    R run(C config, List<B> bots);

    default void require(final boolean condition, final String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
