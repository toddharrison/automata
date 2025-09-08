package com.eharrison.automata.game;

import com.eharrison.automata.automata.Automata;
import com.eharrison.automata.automata.AutomataState;

import java.util.Collection;
import java.util.Map;

public record GameState(Zone zone, Map<Automata, AutomataState> automata, Collection<Signal> signals, int round) {
}
