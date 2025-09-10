package com.eharrison.automata.game;

import java.util.List;
import java.util.Map;

public record Match<B extends Bot<?, ?, R>, R extends Result<?, B>>(
        int gamesPlayed,
        Map<B, Integer> wins,
        int draws,
        List<R> results
) {}
