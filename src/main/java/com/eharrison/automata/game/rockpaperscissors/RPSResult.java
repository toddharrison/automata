package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.Result;

import java.util.Map;

public record RPSResult(
        int rounds,
        Map<Bot<?, ?>, Integer> finalScores
) implements Result {}
