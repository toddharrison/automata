package com.eharrison.automata.game;

import java.util.Optional;

/**
 * An update to the game state, including a result if the game has ended.
 * @param state The new state of the game.
 * @param result The end result of the game, if completed.
 * @param <C> The type of configuration for the game.
 * @param <S> The type of state for the game.
 * @param <V> The type of view for the game.
 * @param <A> The type of action for the game.
 * @param <R> The type of result for the game.
 * @param <B> The type of bot for the game.
 * @apiNote This is an example of CRTP, which stands for "Curiously Recurring Template Patten".
 */
public record Update<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>>(
        S state,
        Optional<R> result
) {
    /**
     * Creates an update with no result, meaning the game is still in progress.
     * @param state The new state of the game.
     */
    public Update(S state) {
        this(state, Optional.empty());
    }

    /**
     * Creates an update with a result, meaning the game has ended if provided.
     * @param state The new state of the game.
     * @param result The end result of the game, or null if the game is still in progress.
     */
    public Update(S state, R result) {
        this(state, Optional.ofNullable(result));
    }
}
