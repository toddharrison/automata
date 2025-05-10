package com.eharrison.game.battle.game;

import com.eharrison.game.battle.action.*;
import com.eharrison.game.battle.automata.Automata;
import com.eharrison.game.battle.automata.AutomataState;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    public GameState runRound(final GameState gameState) {
        val zone = gameState.zone();
        val states = gameState.automata();

        val actions = generateActions(states);
        val emissions = generateEmissions(actions, states);
        val reflections = generateReflections(emissions, states);
        val signals = Stream.concat(emissions.stream(), reflections.stream()).toList();

        val newStates = generateNewStates(zone, actions, states, signals);

        return new GameState(gameState.zone(), newStates, signals, gameState.round() + 1);
    }

    private Map<Automata, Action> generateActions(
            final Map<Automata, AutomataState> states
    ) {
        return states.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getKey().brain().process(entry.getValue())));
    }

    private List<Signal> generateEmissions(
            final Map<Automata, Action> actions,
            final Map<Automata, AutomataState> states
    ) {
        return actions.entrySet().stream()
                .map(entry -> {
                    val automata = entry.getKey();
                    val action = entry.getValue();
                    val state = states.get(automata);

                    return switch (action) {
                        case MoveAction move -> move.emission(state);
                        case PingAction ping -> ping.emission(state);
                        case ScanAction scan -> scan.emission(state);
                        case FireAction fire -> fire.emission(state);
                        default -> null;
                    };
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private List<Signal> generateReflections(
            final List<Signal> emissions,
            final Map<Automata, AutomataState> states
    ) {
        return emissions.stream()
                .flatMap(emission ->
                        states.values().stream()
                                .filter(state -> state.location() != emission.location())
                                .filter(state -> {
                                    val angleFromEmission = emission.location().angleTo(state.location());
                                    return Vector2D.isInWedge(angleFromEmission, emission.minAngle(), emission.maxAngle());
                                })
                                .map(state -> Signal.generateReflection(emission, state))
                )
                .toList();
    }

    private Map<Automata, AutomataState> generateNewStates(
            final Zone zone,
            final Map<Automata, Action> actions,
            final Map<Automata, AutomataState> states,
            final List<Signal> signals
    ) {
        return actions.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (entry) -> {
                            val automata = entry.getKey();
                            val action = entry.getValue();
                            val state = states.get(automata);
                            val signalData = automata.sensor().sense(state, signals);

                            val newState = AutomataState.builder()
                                    .location(state.location())
                                    .signals(signalData)
                                    .memory(state.memory())
                                    .ammo(state.ammo())
                                    .build();
                            return switch (action) {
                                case MoveAction move -> move.execute(newState, zone);
                                case FireAction fire -> fire.execute(newState);
                                default -> newState;
                            };
                        })
                );
    }
}
