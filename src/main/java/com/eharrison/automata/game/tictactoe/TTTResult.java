package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;

public record TTTResult(
        int gameNumber,
        int rounds,
        TTTState state,
        TTTBot winner,
        boolean wasForfeited
) implements Result<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> {
    public TTTResult(final int gameNumber, final int rounds, final TTTState state, final TTTBot winner) {
        this(gameNumber, rounds, state, winner, false);
    }
}
