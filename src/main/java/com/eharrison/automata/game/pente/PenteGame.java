package com.eharrison.automata.game.pente;

import com.eharrison.automata.Arrays;
import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.Update;
import com.eharrison.automata.game.pente.bot.PenteBot;
import lombok.val;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PenteGame extends Game<PenteConfig, PenteState, PenteView, PenteAction, PenteResult, PenteBot> {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {1, 1}, {1, -1}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}};

    private final Random random;

    public PenteGame(final Random random) {
        super("Pente");
        this.random = random;
    }

    @Override
    public void verifyRequirements(final PenteConfig config, final List<PenteBot> bots) {
        require(config.gamesInMatch() > 0, "Pente requires at least 1 game in match.");
        require(bots.size() == 2, "Pente requires exactly 2 bots.");
        require(bots.get(0) != bots.get(1), "Pente requires different bots.");
    }

    @Override
    public PenteState generateStartingState(final PenteConfig config, final List<PenteBot> bots) {
        val bot1 = bots.get(0);
        val bot2 = bots.get(1);
        val bot1Starts = random.nextBoolean();
        return new PenteState(bot1, bot2, new UUID[config.size()][config.size()], bot1Starts);
    }

    @Override
    public Update<PenteConfig, PenteState, PenteView, PenteAction, PenteResult, PenteBot> updateState(
            final PenteConfig config,
            final int gameNumber,
            final PenteState state,
            final List<PenteBot> bots
    ) {
        val bot1 = bots.get(0);
        val bot2 = bots.get(1);
        val bot = state.currentBot();
        val action = bot.act(state.viewFor(bot));

        if (!isValidAction(state, bot, action)) {
            // Invalid action, current bot loses
            // TODO indicate forfeit in result
            val result = new PenteResult(gameNumber, state.round(), state, bot == bot1 ? bot2 : bot1);
            return new Update<>(state, result);
        }

        // Update board
        val newBoard = Arrays.deepCopy(UUID.class, state.board());
        newBoard[action.row()][action.col()] = bot.getId();
        val captures = checkForCaptures(newBoard, bot, action);
        val newState = state.next(newBoard, captures, action);

        // Check for win
        if (isWin(config, newState, bot)) {
            val result = new PenteResult(gameNumber, state.round(), state, bot);
            return new Update<>(newState, result);
        }

        return new Update<>(newState);
    }

    @Override
    public PenteResult getGameOverResult(final int gameNumber, final PenteState state) {
        // Draw
        return new PenteResult(gameNumber, state.round(), state, null);
    }

//    @Override
    private boolean isValidAction(final PenteState state, final PenteBot bot, final PenteAction action) {
        if (action == null) return false;

        boolean isFirstMove = state.round() == 0;
        if (isFirstMove) {
            int center = state.board().length / 2;
            return action.row() == center && action.col() == center && state.board()[center][center] == null;
        }

        return inBounds(state.board(), action.row(), action.col()) && state.board()[action.row()][action.col()] == null;
    }

    @Override
    public boolean isGameOver(final PenteConfig config, final PenteState state) {
        return state.round() > state.board().length * state.board()[0].length - 1;
    }

    private boolean isWin(final PenteConfig config, final PenteState state, final PenteBot bot) {
        // Check rows, columns, and diagonals for 5 in a row
        val board = state.board();
        int size = config.size();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (bot.getId() == board[r][c]) {
                    // Check right
                    if (c <= size - 5 && checkDirection(board, bot, r, c, 0, 1)) return true;
                    // Check down
                    if (r <= size - 5 && checkDirection(board, bot, r, c, 1, 0)) return true;
                    // Check down-right
                    if (r <= size - 5 && c <= size - 5 && checkDirection(board, bot, r, c, 1, 1)) return true;
                    // Check down-left
                    if (r <= size - 5 && c >= 4 && checkDirection(board, bot, r, c, 1, -1)) return true;
                }
            }
        }

        // Check for captures to win
        val capturesToWin = config.capturesToWin();
        if (bot == state.bot1() && state.bot1Captures() >= capturesToWin) return true;
        else return bot == state.bot2() && state.bot2Captures() >= capturesToWin;
    }

    private boolean checkDirection(
            final UUID[][] board,
            final PenteBot bot,
            final int startRow,
            final int startCol,
            final int dRow,
            final int dCol
    ) {
        for (int i = 0; i < 5; i++) {
            if (bot.getId() != board[startRow + i * dRow][startCol + i * dCol]) {
                return false;
            }
        }
        return true;
    }

    private int checkForCaptures(final UUID[][] board, final PenteBot bot, final PenteAction action) {
        var captures = 0;

        val row = action.row();
        val col = action.col();

        for (int[] dir : DIRECTIONS) {
            int r1 = row + dir[0], c1 = col + dir[1];
            int r2 = row + 2 * dir[0], c2 = col + 2 * dir[1];
            int r3 = row + 3 * dir[0], c3 = col + 3 * dir[1];
            if (inBounds(board, r1, c1) && inBounds(board, r2, c2) && inBounds(board, r3, c3)) {
                if (isOpponentOccupied(board, bot, r1, c1) && isOpponentOccupied(board, bot, r2, c2) && bot.getId() == board[r3][c3]) {
                    // Capture opponentId pieces
                    board[r1][c1] = null;
                    board[r2][c2] = null;
                    captures += 1;
                }
            }
        }

        return captures;
    }

    private boolean inBounds(final UUID[][] board, final int row, final int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[row].length;
    }

    private boolean isOpponentOccupied(final UUID[][] board, final PenteBot bot, final int row, final int col) {
        return board[row][col] != null && bot.getId() != board[row][col];
    }
}
