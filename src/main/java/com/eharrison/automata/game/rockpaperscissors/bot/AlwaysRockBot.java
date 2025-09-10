package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public class AlwaysRockBot extends RPSBot {
    public static final String TEAM_NAME = "My Team";
    public static final String NAME = "Always Rock Bot";

    public AlwaysRockBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public RPSAction act(final RPSView view) {
        return RPSAction.ROCK;
    }
}
