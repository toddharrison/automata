package com.eharrison.automata.brain;

import com.eharrison.automata.action.Action;
import com.eharrison.automata.automata.AutomataState;

import java.awt.*;

public interface AutomataBrain {
    String name();

    Color color();

    Action process(AutomataState status);
}
