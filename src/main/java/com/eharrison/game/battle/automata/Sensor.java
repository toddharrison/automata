package com.eharrison.game.battle.automata;

import com.eharrison.game.battle.game.Signal;
import com.eharrison.game.battle.game.Vector2D;
import lombok.val;

import java.util.Collection;

public record Sensor(int directionalResolution, int frequencyCount) {
    public static final int HIT_FREQUENCY = 0;
    public static final int MOVE_FREQUENCY = 1;
    public static final int FIRE_FREQUENCY = 2;

    public double[][] sense(final AutomataState state, final Collection<Signal> signals) {
        val signalGrid = new double[directionalResolution][frequencyCount];

        signals.forEach(signal -> {
            val angleFromSignal = signal.location().angleTo(state.location());

            if (state.location() != signal.location() && Vector2D.isInWedge(angleFromSignal, signal.minAngle(), signal.maxAngle())) {
                val angleFromDetector = state.location().angleTo(signal.location());
                val sector = angleToSector(angleFromDetector);
                val frequency = signal.frequency();

                val arcLength = Vector2D.arcLength(signal.minAngle(), signal.maxAngle());
                val amplitude = Signal.adjustAmplitude(signal.amplitude(), arcLength, state.location(), signal.location());

                signalGrid[sector][frequency] += amplitude;
            }
        });

        return signalGrid;
    }

    public int angleToSector(double angle) {
        val normalized = Vector2D.normalizeAngle(angle);
        val sectorSize = Vector2D.TAU / directionalResolution;

        return (int) (normalized / sectorSize);
    }
}
