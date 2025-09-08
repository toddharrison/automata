package com.eharrison.automata.game.pente;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.pente.bot.PenteBot;

public record PenteResult(
        int rounds,
        PenteBot winner
) implements Result {}
