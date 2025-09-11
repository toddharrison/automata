package com.eharrison.automata.game;

/**
 * Defines a state in a game.
 * @param <C> The type of configuration for the game.
 * @param <S> The type of state for the game.
 * @param <V> The type of view for the game.
 * @param <A> The type of action for the game.
 * @param <R> The type of result for the game.
 * @param <B> The type of bot for the game.
 * @apiNote This is an example of CRTP, which stands for "Curiously Recurring Template Patten".
 */
public interface State<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>> {
    /**
     * Get the view for a particular bot.
     * @param bot The bot for which to get the view.
     * @return The view for the bot.
     */
    View viewFor(final B bot);

    /**
     * Get a displayable representation of this state.
     * @return A displayable representation of this state.
     */
    String display();
}
