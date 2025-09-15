package com.eharrison.automata.game.harvest;

import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.harvest.bot.HarvestBot;

public record HarvestResult(
        int gameNumber,
        HarvestState state,
        HarvestBot winner,
        boolean wasForfeited
) implements Result<HarvestConfig, HarvestState, HarvestView, HarvestAction, HarvestResult, HarvestBot> {
    public HarvestResult(final int gameNumber, final HarvestState state, final HarvestBot winner) {
        this(gameNumber, state, winner, false);
    }
}
