package com.eharrison.game.battle.brain;

import com.eharrison.game.battle.action.Action;
import com.eharrison.game.battle.automata.AutomataState;

import java.awt.*;

public interface AutomataBrain {
    String name();

    Color color();

    Action process(AutomataState status);
}
