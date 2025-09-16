package com.eharrison.automata.game.tictactoe;

import com.eharrison.automata.game.State;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import com.eharrison.automata.util.Arrays;
import lombok.val;

import java.util.UUID;

public record TTTState(
        int round,
        TTTBot bot1,
        TTTBot bot2,
        UUID[][] board,
        TTTAction lastAction,
        TTTBot currentBot
) implements State<TTTConfig, TTTState, TTTView, TTTAction, TTTResult, TTTBot> {
    public TTTState(final TTTBot bot1, final TTTBot bot2, final boolean bot1Starts) {
        this(0, bot1, bot2, new UUID[3][3], null, bot1Starts ? bot1 : bot2);
    }

    @Override
    public TTTView viewFor(final TTTBot bot) {
        return new TTTView(round, Arrays.deepCopy(UUID.class, board), lastAction);
    }

    public TTTState next(final UUID[][] newBoard, final TTTAction lastAction) {
        return new TTTState(round + 1, bot1, bot2, newBoard, lastAction, currentBot == bot1 ? bot2 : bot1);
    }

    @Override
    public String display() {
        val sb = new StringBuilder();
        for (val cells : board) {
            for (int c = 0; c < cells.length; c++) {
                if (cells[c] == null) {
                    sb.append(".");
                } else if (bot1.getId() == cells[c]) {
                    sb.append("X");
                } else {
                    sb.append("O");
                }
                if (c < cells.length - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
