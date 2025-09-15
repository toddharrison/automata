package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.rockpaperscissors.bot.RPSBot;

import java.util.Map;

public record RPSResult(
        int gameNumber,
        int rounds,
        Map<RPSBot, Integer> finalScores,
        RPSState state,
        RPSBot winner,
        boolean wasForfeited
) implements Result<RPSConfig, RPSState, RPSView, RPSAction, RPSResult, RPSBot> {
    public RPSResult(final int gameNumber, final int rounds, final Map<RPSBot, Integer> finalScores, final RPSState state, final RPSBot winner) {
        this(gameNumber, rounds, finalScores, state, winner, false);
    }
}
