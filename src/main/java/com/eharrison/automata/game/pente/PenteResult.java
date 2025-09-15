package com.eharrison.automata.game.pente;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.pente.bot.PenteBot;

public record PenteResult(
        int gameNumber,
        int rounds,
        PenteState state,
        PenteBot winner,
        boolean wasForfeited
) implements Result<PenteConfig, PenteState, PenteView, PenteAction, PenteResult, PenteBot> {
    public PenteResult(final int gameNumber, final int rounds, final PenteState state, final PenteBot winner) {
        this(gameNumber, rounds, state, winner, false);
    }
}
