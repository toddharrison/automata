package com.eharrison.automata.automata;

import com.eharrison.automata.brain.AutomataBrain;

import java.util.UUID;

public record Automata(UUID id, AutomataBrain brain, Sensor sensor) {
    @Override
    public String toString() {
        return brain.name();
    }
}
