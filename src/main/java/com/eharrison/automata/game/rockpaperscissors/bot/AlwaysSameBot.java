package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;
import lombok.val;

import java.util.Random;

public class AlwaysSameBot extends RPSBot {
    public static final String TEAM_NAME = "My Team";
    public static final String NAME = "Always Same Choice Bot";

    private final RPSAction choice;

    public AlwaysSameBot() {
        super(TEAM_NAME, NAME);
        val actions = RPSAction.values();
        choice = actions[new Random().nextInt(actions.length)];
    }

    @Override
    public RPSAction act(final RPSView view) {
        return choice;
    }
}
