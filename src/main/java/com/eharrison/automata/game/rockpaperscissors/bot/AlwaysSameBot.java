package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;
import lombok.val;

import java.util.Random;

public class AlwaysSameBot implements RPSBot {
    private final RPSAction choice;

    public AlwaysSameBot() {
        val actions = RPSAction.values();
        choice = actions[new Random().nextInt(actions.length)];
    }

    @Override
    public String getName() {
        return "Always Same Choice Bot";
    }

    @Override
    public RPSAction act(final RPSView view) {
        return choice;
    }
}
