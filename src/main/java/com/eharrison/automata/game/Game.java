package com.eharrison.automata.game;

import lombok.val;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Game<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>> {
    private final String name;

    public Game(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public abstract void verifyRequirements(C config, List<B> bots);

    public abstract S generateStartingState(final C config, final List<B> bots);

    public final Match<C, S, V, A, R, B> runMatch(final C config, final List<B> bots) {
        verifyRequirements(config, bots);

        bots.forEach(b -> b.init(config));

        val results = IntStream.range(0, config.gamesInMatch())
                .mapToObj(i -> run(config, bots, i, generateStartingState(config, bots)))
                .toList();
        return combineIntoMatch(results);
    }

    public final R run(C config, List<B> bots, int gameNumber, S startingState) {
        var state = startingState;

        bots.forEach(b -> b.start(gameNumber));

        while (!isGameOver(state)) {
            val update = updateState(config, gameNumber, state, bots);
            state = update.state();
            if (update.result().isPresent()) {
                // Game complete before isGameOver()
                val result = update.result().get();
                return notifyBotsOfResult(result, bots);
            }
        }

        // Game over after isGameOver()
        val result = getGameOverResult(gameNumber, state);
        return notifyBotsOfResult(result, bots);
    }

    public abstract Update<C, S, V, A, R, B> updateState(final C config, final int gameNumber, final S state, final List<B> bots);

    public abstract boolean isValidAction(S state, A action);

    public abstract boolean isGameOver(S state);

    public abstract R getGameOverResult(int gameNumber, S state);

    protected final void require(final boolean condition, final String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    private Match<C, S, V, A, R, B> combineIntoMatch(final List<R> results) {
        val wins = results.stream()
                .map(Result::winner)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(id -> id, id -> 1, Integer::sum));
        val draws = (int) results.stream().filter(r -> r.winner() == null).count();
        return new Match<>(results.size(), wins, draws, results);
    }

    private R notifyBotsOfResult(final R result, final List<B> bots) {
        bots.forEach(b -> b.end(result));
        return result;
    }
}
