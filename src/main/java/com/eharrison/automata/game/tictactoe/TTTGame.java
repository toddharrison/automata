package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.Match;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TTTGame extends Game<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> {
    private final Random random;

    public TTTGame(final Random random) {
        this.random = random;
    }

    @Override
    public String getName() {
        return "Tic-Tac-Toe";
    }

    @Override
    public Match<TTTState, TTTView, TTTAction, TTTResult, TTTBot> runMatch(final TTTConfig config, final List<TTTBot> bots) {
        require(config.gamesInMatch() > 0, "Tic-Tac-Toe requires at least 1 game to play.");
        require(bots.size() == 2, "Tic-Tac-Toe requires exactly 2 bots.");
        require(bots.get(0) != bots.get(1), "Tic-Tac-Toe requires different bots.");

        bots.forEach(b -> b.init(config.gamesInMatch()));

        val results = new ArrayList<TTTResult>();
        for (int i = 0; i < config.gamesInMatch(); i++) {
            val bot1 = bots.get(0);
            val bot2 = bots.get(1);
            val bot1Starts = random.nextBoolean();
            var startingState = new TTTState(bot1, bot2, bot1Starts);

            results.add(run(config, bots, i, startingState));
        }
        return processResults(results);
    }

    @Override
    public TTTResult run(final TTTConfig config, final List<TTTBot> bots, final int gameNumber, final TTTState startingState) {
        val bot1 = bots.get(0);
        val bot2 = bots.get(1);

        var state = startingState;

        bot1.start(gameNumber);
        bot2.start(gameNumber);

        TTTResult result = null;
        while (!isGameOver(state)) {
            val action = state.currentBot().act(state.viewFor(state.currentBot()));
            if (!isValidAction(state, action)) {
                // Invalid action, current bot loses
                result = new TTTResult(state.round(), state, state.currentBot() == bot1 ? bot2 : bot1);
                break;
            }

            // Update board
            val newBoard = state.board().clone();
            newBoard[action.row()][action.col()] = state.currentBot().getId();
            state = state.next(newBoard, action);

            // Check for win
            if (isWin(newBoard, bot1)) {
                result = new TTTResult(state.round(), state, bot1); // Bot1 wins
                break;
            } else if (isWin(newBoard, bot2)) {
                result = new TTTResult(state.round(), state, bot2); // Bot2 wins
                break;
            }
        }
        if (result == null) {
            // Draw
            result = new TTTResult(state.round(), state, null);
        }
        bot1.end(gameNumber, result);
        bot2.end(gameNumber, result);

        return result;
    }

    @Override
    public boolean isValidAction(final TTTState state, final TTTAction action) {
        return action.row() >= 0 && action.row() < 3 &&
                action.col() >= 0 && action.col() < 3 &&
                state.board()[action.row()][action.col()] == null;
    }

    private boolean isGameOver(final TTTState state) {
        return state.round() > 8;
    }

    private boolean isWin(final UUID[][] board, final TTTBot bot) {
        for (int i = 0; i < 3; i++) {
            if ((bot.getId() == board[i][0] && bot.getId() == board[i][1] && bot.getId() == board[i][2]) || // Row
                    (bot.getId() == board[0][i] && bot.getId() == board[1][i] && bot.getId() == board[2][i])) { // Column
                return true;
            }
        }
        return (bot.getId() == board[0][0] && bot.getId() == board[1][1] && bot.getId() == board[2][2]) || // Diagonal
                (bot.getId() == board[0][2] && bot.getId() == board[1][1] && bot.getId() == board[2][0]); // Anti-diagonal
    }
}
