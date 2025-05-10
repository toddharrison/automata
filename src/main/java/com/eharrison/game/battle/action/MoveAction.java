package com.eharrison.game.battle.action;

import com.eharrison.game.battle.automata.AutomataState;
import com.eharrison.game.battle.game.Signal;
import com.eharrison.game.battle.game.Zone;

public record MoveAction(double angle, double distance) implements Action {
    public Signal emission(final AutomataState state) {
        return null;

//        val location = state.location();
//        val amplitude = distance; // TODO strength of signal based on distance
//
//        return new Signal(location, 0, 0, amplitude, Sensor.MOVE_FREQUENCY, false);
    }

    public AutomataState execute(final AutomataState state, final Zone zone) {
        return state.toBuilder()
                .location(state.location()
                        .translateTo(angle, distance)
                        .clamp(0, zone.width(), 0, zone.height()))
                .build();
    }
}
