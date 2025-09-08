package com.eharrison.automata.game;

import java.util.UUID;

public interface Bot<V extends View, A extends Action> extends Player {
    UUID getId();

    String getName();

    A act(final V view);
}
