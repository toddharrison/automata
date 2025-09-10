package com.eharrison.automata.game;

import java.util.UUID;

public interface Bot<V extends View, A extends Action, R extends Result> extends Player {
    default void init() {}
    default void start(final int gameNumber) {}
    default void end(final Result result) {}

    UUID getId();

    String getName();

    A act(final V view);
}
