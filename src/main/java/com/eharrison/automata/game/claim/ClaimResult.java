package com.eharrison.automata.game.claim;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.claim.bot.ClaimBot;

public record ClaimResult(
        int gameNumber,
        ClaimState state,
        ClaimBot winner,
        boolean wasForfeited
) implements Result<ClaimConfig, ClaimState, ClaimView, ClaimAction, ClaimResult, ClaimBot> {
    public ClaimResult(final int gameNumber, final ClaimState state, final ClaimBot winner) {
        this(gameNumber, state, winner, false);
    }
}
