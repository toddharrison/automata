package com.eharrison.automata.game;

import java.util.List;
import java.util.Map;

public record Match<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>>(
        int gamesPlayed,
        Map<B, Integer> wins,
        int draws,
        List<R> results
) {}
