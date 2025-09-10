package com.eharrison.automata.game;

import java.util.Map;

public record Match(
        int gamesPlayed,
        Map<? extends Bot<?, ?, ?>, Integer> wins,
        int draws
) {}
