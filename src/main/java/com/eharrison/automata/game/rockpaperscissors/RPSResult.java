package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.game.Result;
import com.eharrison.automata.game.rockpaperscissors.bot.RPSBot;

import java.util.Map;

public record RPSResult(
        int rounds,
        Map<Bot<?, ?, ?>, Integer> finalScores,
        RPSState state,
        RPSBot winner
) implements Result<RPSState, RPSBot> {}
