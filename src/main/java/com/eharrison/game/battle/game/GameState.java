package com.eharrison.game.battle.game;

import com.eharrison.game.battle.automata.Automata;
import com.eharrison.game.battle.automata.AutomataState;

import java.util.Collection;
import java.util.Map;

public record GameState(Zone zone, Map<Automata, AutomataState> automata, Collection<Signal> signals, int round) {
}
