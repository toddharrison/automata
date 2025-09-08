package com.eharrison.automata.game.pente.bot;

import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteView;
import lombok.val;

import java.util.ArrayList;
import java.util.UUID;

public class RandomMoveBot implements PenteBot {
    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return "Random Pente Bot";
    }

    @Override
    public PenteAction act(final PenteView view) {
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
