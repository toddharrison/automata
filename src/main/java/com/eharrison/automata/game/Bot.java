package com.eharrison.automata.game;

import java.util.UUID;

/**
 * Defines an autonomous player in a game.
 * @param <C> The type of configuration for the game.
 * @param <S> The type of state for the game.
 * @param <V> The type of view for the game.
 * @param <A> The type of action for the game.
 * @param <R> The type of result for the game.
 * @param <B> The type of bot for the game.
 * @apiNote This is an example of CRTP, which stands for "Curiously Recurring Template Patten".
 */
public abstract class Bot<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>> implements Player {
    private final UUID id = UUID.randomUUID();
    private final String teamName;
    private final String type;

    /**
     * Creates a new bot.
     * @param teamName The type of the team this bot belongs to.
     * @param type The type of this bot.
     */
    public Bot(final String teamName, final String type) {
        this.teamName = teamName;
        this.type = type;
    }

    /**
     * Gets the unique ID of this bot.
     * @return The unique ID of this bot.
     */
    public final UUID getId() {
        return id;
    }

    /**
     * Gets the name of the team this bot belongs to.
     * @return The name of the team this bot belongs to.
     */
    public final String getTeamName() {
        return teamName;
    }

    /**
     * Gets the type of this bot.
     * @return The type of this bot.
     */
    public final String getType() {
        return type;
    }

    /**
     * Called when the bot is initialized before any of the games in the match are started.
     * @param config The configuration for the games in the match.
     */
    public void init(final C config) {};

    /**
     * Called when the bot is started for a particular game.
     * @param gameNumber The number of the game, starting at 0.
     */
    public void start(final int gameNumber) {};

    /**
     * Given a view of the game state, determine the action the bot chooses to take.
     * @param view The view of the game state.
     * @return The action the bot chooses to take.
     */
    public abstract A act(final V view);

    /**
     * Called when the bot is finished for a particular game.
     * @param result The result of the game.
     */
    public void end(final R result) {};

    @Override
    public String toString() {
        return String.format("%s (%s)", type, teamName);
    }
}
