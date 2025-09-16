package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public class RPSAlwaysScissorsBot extends RPSBot {
    public static final String TEAM_NAME = "Default";
    public static final String NAME = "Always Scissors Bot";

    public RPSAlwaysScissorsBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public RPSAction act(final RPSView view) {
        return RPSAction.SCISSORS;
    }
}
