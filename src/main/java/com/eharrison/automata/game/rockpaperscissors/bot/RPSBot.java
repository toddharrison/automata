package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.rockpaperscissors.*;

public abstract class RPSBot extends Bot<RPSConfig, RPSState, RPSView, RPSAction, RPSResult, RPSBot> {
    public RPSBot(final String teamName, final String name) {
        super(teamName, name);
    }
}
