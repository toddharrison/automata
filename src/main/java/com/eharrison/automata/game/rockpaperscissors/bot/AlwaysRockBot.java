package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

import java.util.UUID;

public class AlwaysRockBot implements RPSBot {
    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return "Always Rock Bot";
    }

    @Override
    public RPSAction act(final RPSView view) {
        return RPSAction.ROCK;
    }
}
