package com.eharrison.automata.game;

import java.util.Optional;

public record Update<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>>(
        S state,
        Optional<R> result
) {
    public Update(S state) {
        this(state, Optional.empty());
    }
}
