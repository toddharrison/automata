package com.eharrison.automata.game.rockpaperscissors;

import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.Match;
import com.eharrison.automata.game.rockpaperscissors.bot.RPSBot;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RPSGame extends Game<RPSConfig, RPSState, RPSView, RPSAction, RPSResult, RPSBot> {
    @Override
    public String getName() {
        return "Rock Paper Scissors";
    }

    @Override
    public Match<RPSBot, RPSResult> run(final RPSConfig config, final List<RPSBot> bots) {
        require(config.gamesToPlay() > 0, "Rock Paper Scissors requires at least 1 game to play.");
        require(config.rounds() > 0, "Rock Paper Scissors requires at least 1 round.");
        require(bots.size() == 2, "Rock Paper Scissors requires exactly 2 bots.");
        require(bots.get(0) != bots.get(1), "Rock Paper Scissors requires different bots.");

        val bot1 = bots.get(0);
        val bot2 = bots.get(1);

        bot1.init();
        bot2.init();

        val results = new ArrayList<RPSResult>();
        for (int i = 0; i < config.gamesToPlay(); i++) {
            var state = new RPSState(config.rounds(), bot1, bot2);

            bot1.start(i);
            bot2.start(i);

            while (!isGameOver(state)) {
                int score1 = state.bot1Score();
                int score2 = state.bot2Score();

                val action1 = bot1.act(state.viewFor(bot1));
                val action2 = bot2.act(state.viewFor(bot2));

                val action1Valid = isValidAction(state, action1);
                val action2Valid = isValidAction(state, action2);
                if (!action1Valid && action2Valid) {
                    // forfeit to bot2
                    score2 += 1;
                } else if (action1Valid && !action2Valid) {
                    // forfeit to bot1
                    score1 += 1;
                } else if (action1Valid) {
                    score1 = isWin(action1, action2) ? state.bot1Score() + 1 : state.bot1Score();
                    score2 = isWin(action2, action1) ? state.bot2Score() + 1 : state.bot2Score();
                }

                state = state.next(score1, score2, action1, action2);
            }
            val winner = state.bot1Score() > state.bot2Score() ? state.bot1() : state.bot2();
            val result = new RPSResult(state.round(), Map.of(state.bot1(), state.bot1Score(), state.bot2(), state.bot2Score()), state, winner);
            bot1.end(result);
            bot2.end(result);

            results.add(result);
        }
        return processResults(results);
    }

    @Override
    public boolean isValidAction(final RPSState state, final RPSAction action) {
        return action == RPSAction.ROCK || action == RPSAction.PAPER || action == RPSAction.SCISSORS;
    }

    private boolean isGameOver(final RPSState state) {
        return state.round() >= state.totalRounds();
    }

    private boolean isWin(final RPSAction a1, final RPSAction a2) {
        return (a1 == RPSAction.ROCK && a2 == RPSAction.SCISSORS) ||
               (a1 == RPSAction.PAPER && a2 == RPSAction.ROCK) ||
               (a1 == RPSAction.SCISSORS && a2 == RPSAction.PAPER);
    }
}
