package com.eharrison.automata.game.pente.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteResult;
import com.eharrison.automata.game.pente.PenteState;
import com.eharrison.automata.game.pente.PenteView;

public abstract class PenteBot extends Bot<PenteState, PenteView, PenteAction, PenteResult, PenteBot> {
    public PenteBot(final String teamName, final String botName) {
        super(teamName, botName);
    }

    public PenteAction makeRequiredFirstMoveInCenter(final PenteView view) {
        int size = view.board().length;
        return new PenteAction(size / 2, size / 2);
    }
}
