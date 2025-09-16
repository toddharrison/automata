package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;
import lombok.val;

import java.util.Random;

public class RPSRandomBot extends RPSBot {
    public static final String TEAM_NAME = "Default";
    public static final String NAME = "Random Choice Bot";

    public RPSRandomBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public RPSAction act(final RPSView view) {
        val actions = RPSAction.values();
        return actions[new Random().nextInt(actions.length)];
    }
}
