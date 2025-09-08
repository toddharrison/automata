package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Config;

public record TTTConfig(
        int gamesToPlay
) implements Config {}
