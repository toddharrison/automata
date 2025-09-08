package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.State;
import com.eharrison.automata.game.rockpaperscissors.bot.RPSBot;

public record RPSState(
        int round,
        int totalRounds,
        RPSBot bot1,
        RPSBot bot2,
        int bot1Score,
        int bot2Score,
        RPSAction lastBot1Action,
        RPSAction lastBot2Action
) implements State<RPSBot> {
    public RPSState(int totalRounds, RPSBot bot1, RPSBot bot2) {
        this(0, totalRounds, bot1, bot2, 0, 0, null, null);
    }

    @Override
    public RPSView viewFor(final RPSBot bot) {
        if (bot == bot1) {
            return new RPSView(round, totalRounds, bot1Score, bot2Score, lastBot1Action, lastBot2Action);
        } else if (bot == bot2) {
            return new RPSView(round, totalRounds, bot2Score, bot1Score, lastBot2Action, lastBot1Action);
        } else {
            throw new IllegalArgumentException("Bot not part of this game state.");
        }
    }

    public RPSState next(final int score1, final int score2, final RPSAction action1, final RPSAction action2) {
        return new RPSState(round + 1, totalRounds, bot1, bot2, score1, score2, action1, action2);
    }
}
