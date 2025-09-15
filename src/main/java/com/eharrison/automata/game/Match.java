package com.eharrison.automata.game;

import java.util.List;
import java.util.Map;

/**
 * A match between two bots, which consists of a number of games played.
 * @param gamesPlayed The number of games played.
 * @param wins The number of wins for each bot.
 * @param draws The number of draws.
 * @param results The results of the games.
 * @param <C> The type of configuration for the games in the match.
 * @param <S> The type of state for the games in the match.
 * @param <V> The type of view for the games in the match.
 * @param <A> The type of action for the games in the match.
 * @param <R> The type of result for the games in the match.
 * @param <B> The type of bot for the games in the match.
 * @apiNote This is an example of CRTP, which stands for "Curiously Recurring Template Patten".
 */
public record Match<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>>(
        int gamesPlayed,
        Map<B, Integer> wins,
        int draws,
        List<R> results
) {}
