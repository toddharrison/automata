package com.eharrison.automata.game.harvest;

import com.eharrison.automata.Arrays;
import com.eharrison.automata.Location;
import com.eharrison.automata.game.State;
import com.eharrison.automata.game.harvest.bot.HarvestBot;
import lombok.val;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public record HarvestState(
        int round,
        Map<HarvestBot, Location> botLocations,
        Map<HarvestBot, HarvestAction> lastActions,
        Map<HarvestBot, Integer> scores,
        Integer[][] board
) implements State<HarvestConfig, HarvestState, HarvestView, HarvestAction, HarvestResult, HarvestBot> {
    public HarvestState(final int size, final List<HarvestBot> bots) {
        this(0, getStartingLocations(size, bots), Map.of(), Map.of(), new Integer[size][size]);
        generateWeightedBoard(size);
        botLocations.forEach((bot, loc) -> board[loc.row()][loc.col()] = null);
    }

    @Override
    public HarvestView viewFor(final HarvestBot bot) {
        val newBotLocations = botLocations.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey().getId(),
                Map.Entry::getValue
        ));
        val newLastActions = lastActions.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey().getId(),
                Map.Entry::getValue));

        return new HarvestView(round, newBotLocations, newLastActions, Arrays.deepCopy(Integer.class, board));
    }

    public HarvestState next(
            final Map<HarvestBot, Location> botLocations,
            final Map<HarvestBot, HarvestAction> lastActions,
            final Map<HarvestBot, Integer> scores,
            final Integer[][] board
    ) {
        return new HarvestState(round + 1, botLocations, lastActions, scores, board);
    }

    @Override
    public String display() {
        val size = board.length;
        val grid = new char[size][size];

        // Fill grid with periods
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                grid[r][c] = board[r][c] == null ? '.' : (char) ('0' + board[r][c]);
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

    private static Map<HarvestBot, Location> getStartingLocations(final int size, final List<HarvestBot> bots) {
        Location[] corners = {
                new Location(0, 0), // Top left
                new Location(size - 1, size - 1), // Bottom right
                new Location(0, size - 1), // Bottom left
                new Location(size - 1, 0) // Top right
        };
        val map = new HashMap<HarvestBot, Location>();
        for (int i = 0; i < bots.size(); i++) {
            map.put(bots.get(i), corners[i]);
        }
        return map;
    }

    private void generateWeightedBoard(final int size) {
        // TODO move random to game
        val rand = new Random();
        // Weights: lower numbers are more likely
        double[] weights = new double[10];
        double total = 0;
        for (int i = 0; i < 10; i++) {
            weights[i] = Math.pow(0.5, i); // e.g., 0:1, 1:0.5, 2:0.25, ...
            total += weights[i];
        }
        // Normalize weights
        for (int i = 0; i < 10; i++) {
            weights[i] /= total;
        }
        // Cumulative weights
        double[] cumulative = new double[10];
        cumulative[0] = weights[0];
        for (int i = 1; i < 10; i++) {
            cumulative[i] = cumulative[i - 1] + weights[i];
        }
        // Fill board
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                double p = rand.nextDouble();
                int value = 0;
                while (value < 9 && p > cumulative[value]) value++;
                board[r][c] = value;
            }
        }
    }
}
