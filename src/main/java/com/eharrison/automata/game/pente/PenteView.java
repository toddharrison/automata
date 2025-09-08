package com.eharrison.automata.game.pente;

import com.eharrison.automata.game.View;
import com.eharrison.automata.game.pente.bot.PenteBot;

import java.util.UUID;

public record PenteView(
        int round,
        UUID opponentId,
        UUID[][] board,
        PenteAction lastAction
) implements View {}
