package com.eharrison.automata.game.pente.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.pente.*;

public abstract class PenteBot extends Bot<PenteConfig, PenteState, PenteView, PenteAction, PenteResult, PenteBot> {
    public PenteBot(final String teamName, final String botName) {
        super(teamName, botName);
    }

    public PenteAction makeRequiredFirstMoveInCenter(final PenteView view) {
        int size = view.board().length;
        return new PenteAction(size / 2, size / 2);
    }
}
