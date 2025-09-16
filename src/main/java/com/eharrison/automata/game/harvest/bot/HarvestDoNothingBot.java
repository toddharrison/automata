package com.eharrison.automata.game.harvest.bot;

import com.eharrison.automata.game.harvest.HarvestAction;
import com.eharrison.automata.game.harvest.HarvestView;

public class HarvestDoNothingBot extends HarvestBot {
    public HarvestDoNothingBot() {
        super("Default", "Do Nothing Harvest Bot");
    }

    @Override
    public HarvestAction act(final HarvestView view) {
        return HarvestAction.HOLD_POSITION;
    }
}
