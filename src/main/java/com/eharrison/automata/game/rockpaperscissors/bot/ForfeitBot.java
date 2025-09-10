package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public class ForfeitBot extends RPSBot {
    public static final String TEAM_NAME = "Forfeit";
    public static final String NAME = "Forfeit Rock Paper Scissors Bot";

    public ForfeitBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public RPSAction act(final RPSView view) {
        return null; // Forfeit
    }
}
