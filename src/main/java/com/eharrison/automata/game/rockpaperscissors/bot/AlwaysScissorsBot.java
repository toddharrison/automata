package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public class AlwaysScissorsBot extends RPSBot {
    public static final String TEAM_NAME = "My Team";
    public static final String NAME = "Always Scissors Bot";

    public AlwaysScissorsBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public RPSAction act(final RPSView view) {
        return RPSAction.SCISSORS;
    }
}
