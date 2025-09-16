package com.eharrison.automata.game.claim.bot;

import com.eharrison.automata.game.claim.ClaimAction;
import com.eharrison.automata.game.claim.ClaimView;

public class ClaimDoNothingBot extends ClaimBot {
    public ClaimDoNothingBot() {
        super("Default", "Do Nothing Claim Bot");
    }

    @Override
    public ClaimAction act(final ClaimView view) {
        return ClaimAction.HOLD_POSITION;
    }
}
