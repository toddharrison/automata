package com.eharrison.automata.game.rockpaperscissors.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSResult;
import com.eharrison.automata.game.rockpaperscissors.RPSState;
import com.eharrison.automata.game.rockpaperscissors.RPSView;

public abstract class RPSBot extends Bot<RPSState, RPSView, RPSAction, RPSResult, RPSBot> {
    public RPSBot(final String teamName, final String name) {
        super(teamName, name);
    }
}
