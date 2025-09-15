package com.eharrison.automata.game.pente.bot;

import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteView;
import lombok.val;

import java.util.ArrayList;

public class RandomMoveBot extends PenteBot {
    public static final String TEAM_NAME = "My Team";
    public static final String NAME = "Random Pente Bot";

    public RandomMoveBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public PenteAction act(final PenteView view) {
        if (view.round() == 0) {
            return makeRequiredFirstMoveInCenter(view);
        } else {
            return makeRandomMove(view);
        }
    }

    private PenteAction makeRandomMove(final PenteView view) {
        val legalMoves = new ArrayList<PenteAction>();
        for (int r = 0; r < view.board().length; r++) {
            for (int c = 0; c < view.board()[r].length; c++) {
                if (view.board()[r][c] == null) {
                    legalMoves.add(new PenteAction(r, c));
                }
            }
        }
        return legalMoves.get((int)(Math.random() * legalMoves.size()));
    }
}
