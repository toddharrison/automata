package com.eharrison.automata.brain;

import com.eharrison.automata.action.Action;
import com.eharrison.automata.action.PingAction;
import com.eharrison.automata.automata.AutomataState;

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
