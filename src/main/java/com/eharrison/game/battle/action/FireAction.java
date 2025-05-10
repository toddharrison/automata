package com.eharrison.game.battle.action;

import com.eharrison.game.battle.automata.AutomataState;
import com.eharrison.game.battle.automata.Sensor;
import com.eharrison.game.battle.game.Signal;
import lombok.val;

public record FireAction(double angle, double distance) implements Action {
    public Signal emission(final AutomataState state) {
        val fireLocation = state.location();
        val fireAmplitude = distance; // TODO strength of signal based on distance
        val fireSignal = new Signal(fireLocation, 0, 0, fireAmplitude, Sensor.FIRE_FREQUENCY, false);

        return fireSignal;
    }

    public AutomataState execute(final AutomataState state) {
        return state.toBuilder()
                .ammo(Math.max(0, state.ammo() - 1))
                .build();
    }
}
