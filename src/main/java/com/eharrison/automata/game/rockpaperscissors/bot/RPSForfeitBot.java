package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public class RPSForfeitBot extends RPSBot {
    public static final String TEAM_NAME = "Forfeit";
    public static final String NAME = "Forfeit Rock Paper Scissors Bot";

    public RPSForfeitBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public RPSAction act(final RPSView view) {
        return null; // Forfeit
    }
}
