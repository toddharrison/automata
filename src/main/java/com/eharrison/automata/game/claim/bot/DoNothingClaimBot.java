package com.eharrison.automata.game.claim.bot;

import com.eharrison.automata.game.claim.ClaimAction;
import com.eharrison.automata.game.claim.ClaimView;

public class DoNothingClaimBot extends ClaimBot {
    public DoNothingClaimBot() {
        super("My Team", "Do Nothing Claim Bot");
    }

    @Override
    public ClaimAction act(final ClaimView view) {
        return ClaimAction.HOLD_POSITION;
    }
}
