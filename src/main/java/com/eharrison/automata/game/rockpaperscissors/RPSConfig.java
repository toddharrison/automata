package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.Config;

public record RPSConfig(
        int gamesToPlay,
        int rounds
) implements Config {}
