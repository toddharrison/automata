package com.eharrison.automata.game.claim.bot;

import com.eharrison.automata.game.claim.ClaimAction;
import com.eharrison.automata.game.claim.ClaimView;

public class ClaimRandomBot extends ClaimBot {
    public ClaimRandomBot() {
        super("Default", "Random Claim Bot");
    }

    @Override
    public ClaimAction act(final ClaimView view) {
        return ClaimAction.values()[(int) (Math.random() * ClaimAction.values().length)];
    }
}
