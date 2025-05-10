package com.eharrison.game.battle.brain;

import com.eharrison.game.battle.automata.AutomataState;
import com.eharrison.game.battle.action.Action;
import com.eharrison.game.battle.action.WaitAction;

import java.awt.*;

public class DoNothingBrain implements AutomataBrain {
    @Override
    public String name() {
        return "Empty";
    }

    @Override
    public Color color() {
        return Color.DARK_GRAY;
    }

    @Override
    public Action process(final AutomataState status) {
        return new WaitAction();
    }
}
