package com.eharrison.automata.game.claim;

import com.eharrison.automata.Arrays;
import com.eharrison.automata.Location;
import com.eharrison.automata.game.State;
import com.eharrison.automata.game.claim.bot.ClaimBot;
import lombok.val;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public record ClaimState(
        int round,
        Map<ClaimBot, Location> botLocations,
        Map<ClaimBot, ClaimAction> lastActions,
        UUID[][] board
) implements State<ClaimConfig, ClaimState, ClaimView, ClaimAction, ClaimResult, ClaimBot> {
    public ClaimState(final int size, final List<ClaimBot> bots) {
        this(0, getStartingLocations(size, bots), Map.of(), new UUID[size][size]);
        botLocations.forEach((bot, loc) -> board[loc.row()][loc.col()] = bot.getId());
    }

    @Override
    public ClaimView viewFor(final ClaimBot bot) {
        val newBotLocations = botLocations.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey().getId(),
                Map.Entry::getValue
        ));
        val newLastActions = lastActions.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey().getId(),
                Map.Entry::getValue));

        return new ClaimView(round, newBotLocations, newLastActions, Arrays.deepCopy(UUID.class, board));
    }

    public ClaimState next(final Map<ClaimBot, Location> botLocations, final Map<ClaimBot, ClaimAction> lastActions, final UUID[][] board) {
        return new ClaimState(round + 1, botLocations, lastActions, board);
    }

    @Override
    public String display() {
        val size = board.length;
        val grid = new char[size][size];

        // Fill grid with periods
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                grid[r][c] = '.';
            }
        }

        // Mark claimed cells
        int botIdx = 0;
        for (val bot : botLocations.keySet()) {
            val botId = bot.getId();
            val claimChar = (char) ('a' + botIdx);
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (board[r][c] != null && board[r][c].equals(botId)) {
                        grid[r][c] = claimChar;
                    }
                }
            }
            botIdx++;
        }

        // Mark bot locations (uppercase)
        botIdx = 0;
        for (val entry : botLocations.entrySet()) {
            val loc = entry.getValue();
            val botChar = (char) ('A' + botIdx);
            grid[loc.row()][loc.col()] = botChar;
            botIdx++;
        }

        // Build string
        val sb = new StringBuilder();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                sb.append(grid[r][c]).append(" ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static Map<ClaimBot, Location> getStartingLocations(final int size, final List<ClaimBot> bots) {
        Location[] corners = {
                new Location(0, 0), // Top left
                new Location(size - 1, size - 1), // Bottom right
                new Location(0, size - 1), // Bottom left
                new Location(size - 1, 0) // Top right
        };
        val map = new HashMap<ClaimBot, Location>();
        for (int i = 0; i < bots.size(); i++) {
            map.put(bots.get(i), corners[i]);
        }
        return map;
    }
}
