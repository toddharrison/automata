package com.eharrison.automata.game.claim;

import com.eharrison.automata.game.Config;

public record ClaimConfig(
        int gamesInMatch,
        int maxRounds,
        int size
) implements Config {}
