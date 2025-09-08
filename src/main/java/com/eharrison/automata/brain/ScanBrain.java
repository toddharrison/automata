package com.eharrison.automata.brain;

import com.eharrison.automata.action.Action;
import com.eharrison.automata.action.ScanAction;
import com.eharrison.automata.automata.AutomataState;
import lombok.val;

import java.awt.*;

public class ScanBrain implements AutomataBrain {
    private double angle = 0;

    @Override
    public String name() {
        return "Scan";
    }

    @Override
    public Color color() {
        return Color.CYAN;
    }

    @Override
    public Action process(final AutomataState status) {
        val angleStart = angle - Math.PI / 4;
        val angleEnd = angle + Math.PI / 4;
        val amplitude = 100.0;
        val frequency = 4;

        angle += Math.PI / 8;

        return new ScanAction(angleStart, angleEnd, amplitude, frequency);
    }
}
