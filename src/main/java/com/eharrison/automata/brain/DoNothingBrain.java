package com.eharrison.automata.brain;

import com.eharrison.automata.automata.AutomataState;
import com.eharrison.automata.action.Action;
import com.eharrison.automata.action.WaitAction;

import java.awt.*;

public class DoNothingBrain implements AutomataBrain {
    @Override
    public String name() {
        return "Do Nothing";
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
