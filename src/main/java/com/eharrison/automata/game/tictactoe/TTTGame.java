package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.Arrays;
import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.Update;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import lombok.val;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TTTGame extends Game<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> {
    private final Random random;

    public TTTGame(final Random random) {
        super("Tic-Tac-Toe");
        this.random = random;
    }

    @Override
    public void verifyRequirements(final TTTConfig config, final List<TTTBot> bots) {
        require(config.gamesInMatch() > 0, "Tic-Tac-Toe requires at least 1 game to play.");
        require(bots.size() == 2, "Tic-Tac-Toe requires exactly 2 bots.");
        require(bots.get(0) != bots.get(1), "Tic-Tac-Toe requires different bots.");
    }

    @Override
    public TTTState generateStartingState(final TTTConfig config, final List<TTTBot> bots) {
        val bot1 = bots.get(0);
        val bot2 = bots.get(1);
        val bot1Starts = random.nextBoolean();
        return new TTTState(bot1, bot2, bot1Starts);
    }

    @Override
    public Update<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> updateState(
            final TTTConfig config,
            final int gameNumber,
            final TTTState state,
            final List<TTTBot> bots
    ) {
        val bot1 = bots.get(0);
        val bot2 = bots.get(1);
        val bot = state.currentBot();

        val action = bot.act(state.viewFor(bot));
        if (!isValidAction(state, bot, action)) {
            // Invalid action, current bot loses
            val result = new TTTResult(gameNumber, state.round(), state, bot == bot1 ? bot2 : bot1);
            return new Update<>(state, result);
        }

        // Update board
        val newBoard = Arrays.deepCopy(UUID.class, state.board());
        newBoard[action.row()][action.col()] = bot.getId();
        val newState = state.next(newBoard, action);

        // Check for win
        if (isWin(newBoard, bot1)) {
            // Bot1 wins
            val result = new TTTResult(gameNumber, state.round(), state, bot1);
            return new Update<>(newState, result);
        } else if (isWin(newBoard, bot2)) {
            // Bot2 wins
            val result = new TTTResult(gameNumber, state.round(), state, bot2);
            return new Update<>(newState, result);
        }

        return new Update<>(newState);
    }

    @Override
    public TTTResult getGameOverResult(final int gameNumber, final TTTState state) {
        // Draw
        return new TTTResult(gameNumber, state.round(), state, null);
    }

//    @Override
    private boolean isValidAction(final TTTState state, final TTTBot bot, final TTTAction action) {
        return action != null &&
                action.row() >= 0 && action.row() < 3 &&
                action.col() >= 0 && action.col() < 3 &&
                state.board()[action.row()][action.col()] == null;
    }

    @Override
    public boolean isGameOver(final TTTConfig config, final TTTState state) {
        return state.round() > 8;
    }

    private boolean isWin(final UUID[][] board, final TTTBot bot) {
        for (int i = 0; i < 3; i++) {
            // Check rows and columns
            if ((bot.getId() == board[i][0] && bot.getId() == board[i][1] && bot.getId() == board[i][2]) ||
                    (bot.getId() == board[0][i] && bot.getId() == board[1][i] && bot.getId() == board[2][i])) {
                return true;
            }
        }
        // Check diagonals
        return (bot.getId() == board[0][0] && bot.getId() == board[1][1] && bot.getId() == board[2][2]) ||
                (bot.getId() == board[0][2] && bot.getId() == board[1][1] && bot.getId() == board[2][0]);
    }
}
