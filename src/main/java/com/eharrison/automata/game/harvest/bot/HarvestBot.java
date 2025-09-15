package com.eharrison.automata.game.harvest.bot;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.harvest.*;

public abstract class HarvestBot extends Bot<HarvestConfig, HarvestState, HarvestView, HarvestAction, HarvestResult, HarvestBot> {
    public HarvestBot(final String teamName, final String type) {
        super(teamName, type);
    }
}
