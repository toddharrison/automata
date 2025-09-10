package com.eharrison.automata.game.pente.bot;

import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteView;
import lombok.val;

import java.util.Random;
import java.util.UUID;

public class MinMaxPenteBot implements PenteBot {
    private static final int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};

    private final UUID id = UUID.randomUUID();

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return "MinMax Pente Bot";
    }

    @Override
    public PenteAction act(final PenteView view) {
        if (view.round() == 0) {
            return makeRequiredFirstMoveInCenter(view);
        } else {
            return makeMove(view);
        }
    }

    private PenteAction makeMove(final PenteView view) {
        val board = view.board();
        val opponentId = view.opponentId();
        val size = board.length;
        val maxDepth = 2;

        PenteAction bestAction = null;
        int bestScore = Integer.MIN_VALUE;

        // Search all possible moves
        // TODO save all best actions by score and pick randomly from them
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == null) {
                    board[r][c] = id;
                    int score = minimax(board, maxDepth - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE, opponentId);
                    board[r][c] = null;
                    if (score > bestScore) {
                        bestScore = score;
                        bestAction = new PenteAction(r, c);
                    }
                }
            }
        }

        return bestAction != null ? bestAction : randomFallback(board);
    }

    private int minimax(final UUID[][] board, final int depth, final boolean maximizing, int alpha, int beta, final UUID opponentId) {
        if (depth == 0 || isTerminal(board, opponentId)) {
            return evaluate(board, opponentId);
        }

        int size = board.length;
        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (board[r][c] == null) {
                        board[r][c] = id;
                        int eval = minimax(board, depth - 1, false, alpha, beta, opponentId);
                        board[r][c] = null;
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) return maxEval; // prune
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (board[r][c] == null) {
                        board[r][c] = id;
                        int eval = minimax(board, depth - 1, true, alpha, beta, opponentId);
                        board[r][c] = null;
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) return minEval; // prune
                    }
                }
            }
            return minEval;
        }
    }

    private int evaluate(final UUID[][] board, final UUID opponentId) {
        return scoreBoard(board, id) - scoreBoard(board, opponentId);
    }

    private int scoreBoard(final UUID[][] board, final UUID playerId) {
        int score = 0;
        int size = board.length;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == playerId) {
                    for (int[] d : directions) {
                        int count = 1;
                        int openEnds = 0;

                        // Forward
                        int rr = r + d[0], cc = c + d[1];
                        while (rr >= 0 && rr < size && cc >= 0 && cc < size && board[rr][cc] == playerId) {
                            count++;
                            rr += d[0]; cc += d[1];
                        }
                        if (rr >= 0 && rr < size && cc >= 0 && cc < size && board[rr][cc] == null) openEnds++;

                        // Backward
                        rr = r - d[0]; cc = c - d[1];
                        while (rr >= 0 && rr < size && cc >= 0 && cc < size && board[rr][cc] == playerId) {
                            count++;
                            rr -= d[0]; cc -= d[1];
                        }
                        if (rr >= 0 && rr < size && cc >= 0 && cc < size && board[rr][cc] == null) openEnds++;

                        // Heuristic scoring
                        if (count >= 5) score += 100000;                      // win
                        else if (count == 4 && openEnds == 2) score += 10000; // open 4
                        else if (count == 4 && openEnds == 1) score += 1000;  // closed 4
                        else if (count == 3 && openEnds == 2) score += 500;   // open 3
                        else if (count == 2 && openEnds == 2) score += 100;   // open 2
                    }
                }
            }
        }
        return score;
    }

    private boolean isTerminal(final UUID[][] board, final UUID opponentId) {
        return hasWin(board, id) || hasWin(board, opponentId) || isFull(board);
    }

    private boolean hasWin(final UUID[][] board, final UUID playerId) {
        int size = board.length;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (playerId == board[r][c]) {
                    for (int[] d : directions) {
                        int count = 1;
                        int rr = r + d[0], cc = c + d[1];
                        while (rr >= 0 && rr < size && cc >= 0 && cc < size && playerId == board[rr][cc]) {
                            count++;
                            if (count >= 5) return true;
                            rr += d[0]; cc += d[1];
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isFull(final UUID[][] board) {
        for (val row : board) {
            for (val c : row) {
                if (c == null) return false;
            }
        }
        return true;
    }

    private PenteAction randomFallback(final UUID[][] board) {
        val rand = new Random();
        val size = board.length;
        while (true) {
            int r = rand.nextInt(size);
            int c = rand.nextInt(size);
            if (board[r][c] == null) {
                return new PenteAction(r, c);
            }
        }
    }
}
