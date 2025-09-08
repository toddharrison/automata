package com.eharrison.automata.game.pente;

import com.eharrison.automata.game.View;
import com.eharrison.automata.game.pente.bot.PenteBot;

public record PenteView(
        int round,
        PenteBot[][] board,
        PenteAction lastAction
) implements View {}
