package com.eharrison.automata.game.claim;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.claim.bot.ClaimBot;

public record ClaimResult(
        int gameNumber,
        ClaimState state,
        ClaimBot winner
) implements Result<ClaimConfig, ClaimState, ClaimView, ClaimAction, ClaimResult, ClaimBot> {}
