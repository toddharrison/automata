package com.eharrison.automata.game.pente;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.pente.bot.PenteBot;

public record PenteResult(
        int gameNumber,
        int rounds,
        PenteState state,
        PenteBot winner
) implements Result<PenteConfig, PenteState, PenteView, PenteAction, PenteResult, PenteBot> {}
