package com.eharrison.automata.brain;

import com.eharrison.automata.automata.AutomataState;
import com.eharrison.automata.game.Vector2D;
import com.eharrison.automata.action.Action;
import com.eharrison.automata.action.MoveAction;
import lombok.val;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMoveBrain implements AutomataBrain {
    @Override
    public String name() {
        return "Random";
    }

    @Override
    public Color color() {
        return Color.PINK;
    }

    @Override
    public Action process(final AutomataState status) {
        val random = ThreadLocalRandom.current();
        val angle = random.nextDouble() * Vector2D.TAU;
        val distance = random.nextDouble() * 10;

        return new MoveAction(angle, distance);
    }
}
