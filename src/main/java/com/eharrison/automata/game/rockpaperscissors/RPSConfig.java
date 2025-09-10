package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.Config;

public record RPSConfig(
        int gamesInMatch,
        int rounds
) implements Config {}
