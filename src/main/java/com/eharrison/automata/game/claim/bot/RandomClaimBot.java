package com.eharrison.automata.game.claim.bot;

import com.eharrison.automata.game.claim.ClaimAction;
import com.eharrison.automata.game.claim.ClaimView;

public class RandomClaimBot extends ClaimBot {
    public RandomClaimBot() {
        super("My Team", "Random Claim Bot");
    }

    @Override
    public ClaimAction act(final ClaimView view) {
        return ClaimAction.values()[(int) (Math.random() * ClaimAction.values().length)];
    }
}
