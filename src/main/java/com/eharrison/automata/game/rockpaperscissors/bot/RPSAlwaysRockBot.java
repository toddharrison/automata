package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public class RPSAlwaysRockBot extends RPSBot {
    public static final String TEAM_NAME = "Default";
    public static final String NAME = "Always Rock Bot";

    public RPSAlwaysRockBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public RPSAction act(final RPSView view) {
        return RPSAction.ROCK;
    }
}
