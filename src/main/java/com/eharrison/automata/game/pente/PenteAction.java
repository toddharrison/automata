package com.eharrison.automata.game.pente;

import com.eharrison.automata.game.Action;

public record PenteAction(
        int row,
        int col
) implements Action {}
