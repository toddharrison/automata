package com.eharrison.automata.game.pente.bot;

import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteView;

public class PenteForfeitBot extends PenteBot {
    public static final String TEAM_NAME = "Forfeit";
    public static final String NAME = "Forfeit Pente Bot";

    public PenteForfeitBot() {
        super(TEAM_NAME, NAME);
    }

    @Override
    public PenteAction act(final PenteView view) {
        return null; // Forfeit
    }
}
