package com.eharrison.automata.action;

import com.eharrison.automata.automata.AutomataState;
import com.eharrison.automata.game.Signal;

public record FireAction(double angle, double distance) implements Action {
    public Signal emission(final AutomataState state) {
        return null;

//        val fireLocation = state.location();
//        val fireAmplitude = distance; // TODO strength of signal based on distance
//        val fireSignal = new Signal(fireLocation, 0, 0, fireAmplitude, Sensor.FIRE_FREQUENCY, false);
//
//        return fireSignal;
    }

    public AutomataState execute(final AutomataState state) {
        return state.toBuilder()
                .ammo(Math.max(0, state.ammo() - 1))
                .build();
    }
}
