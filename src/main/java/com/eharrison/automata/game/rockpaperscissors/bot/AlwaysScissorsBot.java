package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public class AlwaysScissorsBot implements RPSBot {
    @Override
    public String getName() {
        return "Always Rock Bot";
    }

    @Override
    public RPSAction act(final RPSView view) {
        return RPSAction.SCISSORS;
    }
}
