package com.eharrison.automata.game;

import java.util.List;
import java.util.Map;

public record Match<S extends State<B>, B extends Bot<?, ?, ?, R, B>, R extends Result<S, B>>(
        int gamesPlayed,
        Map<B, Integer> wins,
        int draws,
        List<R> results
) {}
