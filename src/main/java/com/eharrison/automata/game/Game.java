package com.eharrison.automata.game;

import lombok.val;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Game<C extends Config, S extends State<B>, V extends View, A extends Action, R extends Result<S, B>, B extends Bot<V, A, R>> {
    public abstract String getName();

    public abstract Match<B, R> run(C config, List<B> bots);

    public abstract boolean isValidAction(final S state, final A action);

    public void require(final boolean condition, final String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public Match<B, R> processResults(final List<R> results) {
        val wins = results.stream()
                .map(Result::winner)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        id -> id,
                        id -> 1,
                        Integer::sum
                ));
        val draws = (int) results.stream().filter(r -> r.winner() == null).count();
        return new Match<>(results.size(), wins, draws, results);
    }
}
