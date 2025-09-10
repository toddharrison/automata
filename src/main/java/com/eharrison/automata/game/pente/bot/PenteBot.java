package com.eharrison.automata.game.pente.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteResult;
import com.eharrison.automata.game.pente.PenteView;

public interface PenteBot extends Bot<PenteView, PenteAction, PenteResult> {
    default PenteAction makeRequiredFirstMoveInCenter(final PenteView view) {
        int size = view.board().length;
        return new PenteAction(size / 2, size / 2);
    }
}
