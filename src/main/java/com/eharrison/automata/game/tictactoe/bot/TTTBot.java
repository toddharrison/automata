package com.eharrison.automata.game.tictactoe.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.tictactoe.TTTAction;
import com.eharrison.automata.game.tictactoe.TTTResult;
import com.eharrison.automata.game.tictactoe.TTTState;
import com.eharrison.automata.game.tictactoe.TTTView;

public abstract class TTTBot extends Bot<TTTState, TTTView, TTTAction, TTTResult, TTTBot> {
    public TTTBot(final String teamName, final String name) {
        super(teamName, name);
    }
}
