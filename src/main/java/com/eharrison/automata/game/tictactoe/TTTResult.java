package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;

public record TTTResult(
        int gameNumber,
        int rounds,
        TTTState state,
        TTTBot winner
) implements Result<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> {}
