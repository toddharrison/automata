package com.eharrison.game.battle.brain;

import com.eharrison.game.battle.action.Action;
import com.eharrison.game.battle.action.PingAction;
import com.eharrison.game.battle.automata.AutomataState;

import java.awt.*;

public class PingBrain implements AutomataBrain {
    @Override
    public String name() {
        return "Ping";
    }

    @Override
    public Color color() {
        return Color.GREEN;
    }

    @Override
    public Action process(final AutomataState status) {
        return new PingAction(100.0, 4);
    }
}
