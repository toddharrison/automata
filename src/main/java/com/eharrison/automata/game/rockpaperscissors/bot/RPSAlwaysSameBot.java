package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;
import lombok.val;

import java.util.Random;

public class RPSAlwaysSameBot extends RPSBot {
    public static final String TEAM_NAME = "Default";
    public static final String NAME = "Always Same Choice Bot";

    private RPSAction choice;

    public RPSAlwaysSameBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public void start(final int gameNumber) {
        val actions = RPSAction.values();
        choice = actions[new Random().nextInt(actions.length)];
    }

    @Override
    public RPSAction act(final RPSView view) {
        return choice;
    }
}
