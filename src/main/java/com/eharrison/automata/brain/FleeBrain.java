package com.eharrison.automata.brain;

import com.eharrison.automata.action.Action;
import com.eharrison.automata.action.MoveAction;
import com.eharrison.automata.action.WaitAction;
import com.eharrison.automata.automata.AutomataState;
import com.eharrison.automata.game.Vector2D;
import lombok.val;

import java.awt.*;
import java.util.Arrays;
import java.util.OptionalDouble;

public class FleeBrain implements AutomataBrain {
    @Override
    public String name() {
        return "Flee";
    }

    @Override
    public Color color() {
        return Color.RED;
    }

    @Override
    public Action process(final AutomataState status) {
        if (status.signals() == null) return new WaitAction();

        val foo = Arrays.stream(status.signals())
                .map(sector -> {
                    var sum = 0.0;
                    for (double s : sector) {
                        sum += Math.abs(s);
                    }
                    return sum;
                })
                .toList();
        System.out.println(foo);
        val total = foo.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        val moves = foo.stream()
                .map(a -> (a / total) * 10)
                .toList();
        System.out.println(moves);

        var move = new Vector2D(0, 0);
        for (int i = 0; i < moves.size(); i++) {
            var angle = Vector2D.TAU / moves.size() * i;
            move = move.translateTo(angle, moves.get(i));
        }
        return new MoveAction(move.angle(), move.magnitude());
    }
}
