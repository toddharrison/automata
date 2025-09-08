package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import lombok.val;

import java.util.List;
import java.util.UUID;

public class TTTGame implements Game<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> {
    @Override
    public String getName() {
        return "Tic-Tac-Toe";
    }

    @Override
    public TTTResult run(final TTTConfig config, final List<TTTBot> bots) {
        require(config.gamesToPlay() > 0, "Tic-Tac-Toe requires at least 1 game to play.");
        require(bots.size() == 2, "Tic-Tac-Toe requires exactly 2 bots.");

        val bot1 = bots.get(0);
        val bot2 = bots.get(1);
        val bot1Starts = Math.random() < 0.5;

        var state = new TTTState(bot1, bot2, bot1Starts);

        // TODO Games to play
        while (!isGameOver(state)) {
            val action = state.currentBot().act(state.viewFor(state.currentBot()));
            if (!isValidAction(action, state)) {
                return new TTTResult(state.round(), state.currentBot() == bot1 ? bot2 : bot1); // Invalid action, current bot loses
            }

            // Update board
            val newBoard = state.board().clone();
            newBoard[action.row()][action.col()] = state.currentBot().getId();
            state = state.next(newBoard, action);

            // Check for win
            if (isWin(newBoard, bot1)) {
                return new TTTResult(state.round(), bot1); // Bot1 wins
            } else if (isWin(newBoard, bot2)) {
                return new TTTResult(state.round(), bot2); // Bot2 wins
            }
        }

        // Draw
        return new TTTResult(state.round(), null);
    }

    private boolean isValidAction(final TTTAction action, final TTTState state) {
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
