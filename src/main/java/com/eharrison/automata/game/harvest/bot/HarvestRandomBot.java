package com.eharrison.automata.game.harvest.bot;

import com.eharrison.automata.game.harvest.HarvestAction;
import com.eharrison.automata.game.harvest.HarvestView;

public class HarvestRandomBot extends HarvestBot {
    public HarvestRandomBot() {
        super("Default", "Random Claim Bot");
    }

    @Override
    public HarvestAction act(final HarvestView view) {
        return HarvestAction.values()[(int) (Math.random() * HarvestAction.values().length)];
    }
}
