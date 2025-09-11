package com.eharrison.automata.game.tictactoe.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.tictactoe.*;

public abstract class TTTBot extends Bot<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> {
    public TTTBot(final String teamName, final String name) {
        super(teamName, name);
    }
}
