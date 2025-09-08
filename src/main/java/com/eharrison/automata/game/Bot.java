package com.eharrison.automata.game;

public interface Bot<V extends View, A extends Action> extends Player {
    String getName();

    A act(final V view);
}
