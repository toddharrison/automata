package com.eharrison.game.battle.automata;

import com.eharrison.game.battle.brain.AutomataBrain;

import java.util.UUID;

public record Automata(UUID id, AutomataBrain brain, Sensor sensor) {
}
