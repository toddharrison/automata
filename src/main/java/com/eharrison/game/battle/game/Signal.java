package com.eharrison.game.battle.game;

import com.eharrison.game.battle.automata.AutomataState;
import lombok.val;

public record Signal(Vector2D location, double minAngle, double maxAngle, double amplitude, int frequency, boolean isReflection) {
    public static double adjustAmplitude(
            final double amplitude,
            final double arcLength,
            final Vector2D start,
            final Vector2D end
    ) {
        val distance = (start.distanceTo(end) * arcLength / Vector2D.TAU) + 1;

        return amplitude * (1 / distance);
    }

    public static Signal generateReflection(final Signal emission, final AutomataState state) {
        val angleToEmission = state.location().angleTo(emission.location);
        val emissionArcLength = Vector2D.arcLength(emission.minAngle, emission.maxAngle);
        val minAngle = angleToEmission - Math.PI / 8;
        val maxAngle = angleToEmission + Math.PI / 8;
        val amplitude = -Signal.adjustAmplitude(emission.amplitude, emissionArcLength, emission.location, state.location());
        val frequency = emission.frequency;

        return new Signal(state.location(), minAngle, maxAngle, amplitude, frequency, true);
    }
}
