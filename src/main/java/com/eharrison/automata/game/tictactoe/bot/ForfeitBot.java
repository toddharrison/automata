package com.eharrison.automata.game.tictactoe.bot;

import com.eharrison.automata.game.tictactoe.TTTAction;
import com.eharrison.automata.game.tictactoe.TTTView;

public class ForfeitBot extends TTTBot {
    public static final String TEAM_NAME = "Forfeit";
    public static final String NAME = "Forfeit Tic-Tac-Toe Bot";

    public ForfeitBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public TTTAction act(final TTTView view) {
        return null; // Forfeit
    }
}
