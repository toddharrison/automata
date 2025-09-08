package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.View;

public record RPSView(
        int round,
        int totalRounds,
        int score,
        int opponentScore,
        RPSAction lastAction,
        RPSAction opponentLastAction
) implements View {}
