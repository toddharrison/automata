package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.View;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;

import java.util.UUID;

public record TTTView(
        int round,
        UUID[][] board,
        TTTAction lastAction
) implements View {}
