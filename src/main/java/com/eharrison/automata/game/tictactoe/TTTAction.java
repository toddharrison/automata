package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Action;

public record TTTAction(
        int row,
        int col
) implements Action {}
