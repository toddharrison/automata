package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.View;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;

public record TTTView(
        int round,
        TTTBot[][] board,
        TTTAction lastAction
) implements View {}
