package com.eharrison.automata.game.harvest;

import com.eharrison.automata.game.Config;

public record HarvestConfig(
        int gamesInMatch,
        int maxRounds,
        int size
) implements Config {}
