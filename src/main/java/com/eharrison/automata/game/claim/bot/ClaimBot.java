package com.eharrison.automata.game.claim.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.claim.*;

public abstract class ClaimBot extends Bot<ClaimConfig, ClaimState, ClaimView, ClaimAction, ClaimResult, ClaimBot> {
    public ClaimBot(final String teamName, final String type) {
        super(teamName, type);
    }
}
