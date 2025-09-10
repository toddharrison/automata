package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;

public record TTTResult(
        int rounds,
        TTTState state,
        TTTBot winner
) implements Result<TTTState, TTTView, TTTAction, TTTResult, TTTBot> {}
