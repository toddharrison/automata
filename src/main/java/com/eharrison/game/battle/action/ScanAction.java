package com.eharrison.game.battle.action;

import com.eharrison.game.battle.automata.AutomataState;
import com.eharrison.game.battle.game.Signal;
import lombok.val;

public record ScanAction(double angleStart, double angleEnd, double amplitude, int frequency) implements Action {
    public Signal emission(final AutomataState state) {
        val scanLocation = state.location();
        val scanSignal = new Signal(scanLocation, angleStart, angleEnd, amplitude, frequency, false);

        return scanSignal;
    }
}
