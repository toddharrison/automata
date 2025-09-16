package com.eharrison.automata.game;

import lombok.val;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Defines a game between bots.
 * @param <C> The type of configuration for the game.
 * @param <S> The type of state for the game.
 * @param <V> The type of view for the game.
 * @param <A> The type of action for the game.
 * @param <R> The type of result for the game.
 * @param <B> The type of bot for the game.
 * @apiNote This is an example of CRTP, which stands for "Curiously Recurring Template Patten".
 */
public abstract class Game<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>> {
    private final String name;

    /**
     * Creates a new game.
     * @param name The name of the game.
     */
    public Game(final String name) {
        this.name = name;
    }

    /**
     * Gets the name of the game.
     * @return The name of the game.
     */
    public final String getName() {
        return name;
    }

    /**
     * Verifies that the given configuration and bots are valid for this game.
     * @param config The configuration for the game.
     * @param bots The bots for the game.
     */
    public abstract void verifyRequirements(C config, List<B> bots);

    /**
     * Generates the initial state of the game.
     * @param config The configuration for the game.
     * @param bots The bots for the game.
     * @return The initial state of the game.
     */
    public abstract S generateStartingState(final C config, final List<B> bots);

    /**
     * Runs a match between the given bots, which consists of a number of games.
     * @param config The configuration for the games in the match.
     * @param bots The bots for the match.
     * @return The result of the match.
     */
    public final Match<C, S, V, A, R, B> runMatch(final C config, final List<B> bots) {
        verifyRequirements(config, bots);

        bots.forEach(b -> b.init(config));

        val results = IntStream.range(0, config.gamesInMatch())
                .mapToObj(i -> run(config, bots, i, generateStartingState(config, bots), false))
                .toList();
        return combineIntoMatchResults(results);
    }

    /**
     * Runs a single game between the given bots.
     * @param config The configuration for the game.
     * @param bots The bots for the game.
     * @param gameNumber The number of the game, starting at 0.
     * @param startingState The initial state of the game.
     * @return The result of the game.
     */
    public final R run(C config, List<B> bots, int gameNumber, S startingState, boolean displayBoard) {
        var state = startingState;

        bots.forEach(b -> b.start(gameNumber));

        while (!isGameOver(config, state)) {
            val update = updateState(config, gameNumber, state, bots);
            state = update.state();

            if (displayBoard) {
                System.out.println(state.display());
            }

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

    /**
     * Updates the state of the game, typically one round at a time.
     * @param config The configuration for the game.
     * @param gameNumber The number of the game, starting at 0.
     * @param state The current state of the game.
     * @param bots The bots for the game.
     * @return The updated state of the game, along with an optional result if the game is over.
     */
    public abstract Update<C, S, V, A, R, B> updateState(final C config, final int gameNumber, final S state, final List<B> bots);

    /**
     * Determines if the game is over, meaning no more actions can be taken.
     * @param config The configuration for the game.
     * @param state The current state of the game.
     * @return True if the game is over, false otherwise.
     */
    public abstract boolean isGameOver(C config, S state);

    /**
     * Get the result of the game after all possible actions have been taken, typically a draw or evaluation of wins.
     * @param gameNumber The number of the game, starting at 0.
     * @param state The end state of the game.
     * @return The result of the game.
     */
    public abstract R getGameOverResult(int gameNumber, S state);

    /**
     * Verify that a required condition is true, otherwise throw an exception.
     * @param condition The condition to verify.
     * @param message The message to include in the exception if the condition is not true.
     */
    protected final void require(final boolean condition, final String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Combines the results of multiple games into a match result.
     * @param results The results of the games.
     * @return The match result.
     */
    private Match<C, S, V, A, R, B> combineIntoMatchResults(final List<R> results) {
        val wins = results.stream()
                .map(Result::winner)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(id -> id, id -> 1, Integer::sum));
        val draws = (int) results.stream().filter(r -> r.winner() == null).count();
        return new Match<>(results.size(), wins, draws, results);
    }

    /**
     * Notifies the bots of the result of the game.
     * @param result The result of the game.
     * @param bots The bots participating in the game.
     * @return The result of the game.
     */
    private R notifyBotsOfResult(final R result, final List<B> bots) {
        bots.forEach(b -> b.end(result));
        return result;
    }
}
