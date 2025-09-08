package com.eharrison.automata.game.pente;

import com.eharrison.automata.game.Config;

public record PenteConfig(
        int gamesToPlay,
        int size,
        int capturesToWin
) implements Config {
    public PenteConfig(final int gamesToPlay) {
        this(gamesToPlay, 19, 5);
    }
}
