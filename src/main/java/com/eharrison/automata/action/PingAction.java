package com.eharrison.automata.action;

import com.eharrison.automata.automata.AutomataState;
import com.eharrison.automata.game.Signal;
import com.eharrison.automata.game.Vector2D;
import lombok.val;

public record PingAction(double amplitude, int frequency) implements Action {
    public Signal emission(final AutomataState state) {
        val pingLocation = state.location();
        val pingSignal = new Signal(pingLocation, 0, Vector2D.TAU, amplitude, frequency, false);

        return pingSignal;
    }
}
