package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;

public record TTTResult(
        int rounds,
        TTTBot winner
) implements Result<TTTBot> {}
