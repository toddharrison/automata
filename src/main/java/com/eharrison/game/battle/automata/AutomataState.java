package com.eharrison.game.battle.automata;

import com.eharrison.game.battle.game.Vector2D;
import lombok.Builder;

import java.util.Arrays;

@Builder(toBuilder = true)
public record AutomataState(Vector2D location, double[][] signals, byte[] memory, int ammo) {
    @Override
    public String toString() {
        return "AutomataState{" +
                "location=" + location +
                ", signals=" + Arrays.deepToString(signals) +
                ", memory=" + Arrays.toString(memory) +
                ", ammo=" + ammo +
                '}';
    }
}
