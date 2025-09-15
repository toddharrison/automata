package com.eharrison.automata.game;

/**
 * Defines the result of a game.
 * @param <C> The type of configuration for the game.
 * @param <S> The type of state for the game.
 * @param <V> The type of view for the game.
 * @param <A> The type of action for the game.
 * @param <R> The type of result for the game.
 * @param <B> The type of bot for the game.
 * @apiNote This is an example of CRTP, which stands for "Curiously Recurring Template Patten".
 */
public interface Result<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>> {
    /**
     * The number of the game, starting at 0.
     * @return The number of the game.
     */
    int gameNumber();

    /**
     * The state of the game at the end of the game.
     * @return The state of the game.
     */
    S state();

    // TODO handle scores instead of wins
    /**
     * The winner of the game.
     * @return The winner of the game, or null if there was a tie.
     */
    B winner();

    /**
     * If the result indicates a loss of the game due to forfeit.
     * @return True if the loss of the game was due to forfeit.
     */
    boolean wasForfeited();
}
