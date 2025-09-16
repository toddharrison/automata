package com.eharrison.automata.game.tictactoe.bot;

import com.eharrison.automata.game.tictactoe.TTTAction;
import com.eharrison.automata.game.tictactoe.TTTView;
import lombok.val;

import java.util.ArrayList;

public class TTTRandomMoveBot extends TTTBot {
    public static final String TEAM_NAME = "Default";
    public static final String NAME = "Random Tic-Tac-Toe Bot";

    public TTTRandomMoveBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public TTTAction act(final TTTView view) {
        val legalMoves = new ArrayList<TTTAction>();
        for (int r = 0; r < view.board().length; r++) {
            for (int c = 0; c < view.board()[r].length; c++) {
                if (view.board()[r][c] == null) {
                    legalMoves.add(new TTTAction(r, c));
                }
            }
        }
        return legalMoves.get((int)(Math.random() * legalMoves.size()));
    }
}
