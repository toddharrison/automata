package com.eharrison.automata.game.harvest.bot;

import com.eharrison.automata.game.harvest.HarvestAction;
import com.eharrison.automata.game.harvest.HarvestView;

public class RandomHarvestBot extends HarvestBot {
    public RandomHarvestBot() {
        super("My Team", "Random Claim Bot");
    }

    @Override
    public HarvestAction act(final HarvestView view) {
        return HarvestAction.values()[(int) (Math.random() * HarvestAction.values().length)];
    }
}
