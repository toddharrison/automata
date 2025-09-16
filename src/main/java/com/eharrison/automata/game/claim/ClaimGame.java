package com.eharrison.automata.game.claim;

import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.Location;
import com.eharrison.automata.game.Update;
import com.eharrison.automata.game.claim.bot.ClaimBot;
import com.eharrison.automata.util.Arrays;
import lombok.val;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClaimGame extends Game<ClaimConfig, ClaimState, ClaimView, ClaimAction, ClaimResult, ClaimBot> {
    public ClaimGame() {
        super("Claim");
    }

    @Override
    public void verifyRequirements(final ClaimConfig config, final List<ClaimBot> bots) {
        require(config.gamesInMatch() > 0, "Claim requires at least 1 game in match.");
        require(bots.size() >= 2 && bots.size() <= 4, "Claim requires 2 to 4 bots.");
        require(bots.stream().distinct().count() == bots.size(), "Claim requires different bots.");
    }

    @Override
    public ClaimState generateStartingState(final ClaimConfig config, final List<ClaimBot> bots) {
        return new ClaimState(config.size(), bots);
    }

    @Override
    public Update<ClaimConfig, ClaimState, ClaimView, ClaimAction, ClaimResult, ClaimBot> updateState(
            final ClaimConfig config,
            final int gameNumber,
            final ClaimState state,
            final List<ClaimBot> bots
    ) {
        val actions = bots.stream()
                .collect(Collectors.toMap(b -> b, b -> b.act(state.viewFor(b))));
        val locations = state.botLocations();
        val newLocations = locations.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> applyAction(e.getValue(), actions.get(e.getKey()))));
        val verifiedNewLocations = newLocations.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    val loc = e.getValue();
                    val size = config.size();
                    if (loc.row() < 0 || loc.row() >= size || loc.col() < 0 || loc.col() >= size) {
                        // Out of bounds, don't move
                        return locations.get(e.getKey());
                    }
                    if (newLocations.values().stream().filter(l -> l.equals(loc)).count() > 1) {
                        // Collision, don't move
                        return locations.get(e.getKey());
                    }
                    return loc;
                }));
        val newBoard = Arrays.deepCopy(UUID.class, state.board());
        verifiedNewLocations.forEach((bot, loc) -> newBoard[loc.row()][loc.col()] = bot.getId());
        val newState = state.next(verifiedNewLocations, actions, newBoard);

        return new Update<>(newState);
    }

    @Override
    public boolean isGameOver(final ClaimConfig config, final ClaimState state) {
        return state.round() >= config.maxRounds();
    }

    @Override
    public ClaimResult getGameOverResult(final int gameNumber, final ClaimState state) {
        val board = state.board();
        val counts = new HashMap<UUID, Integer>();
        for (val row : board) {
            for (val cell : row) {
                if (cell != null) {
                    counts.merge(cell, 1, Integer::sum);
                }
            }
        }
        val maxCount = counts.values().stream().max(Integer::compareTo).orElse(null);
        if (maxCount == null) {
            return null;
        }
        val winners = counts.entrySet().stream()
                .filter(e -> e.getValue().equals(maxCount))
                .map(Map.Entry::getKey)
                .toList();
        val winnerId = winners.size() == 1 ? winners.get(0) : null;
        val winner = state.botLocations().keySet().stream()
                .filter(bot -> bot.getId().equals(winnerId))
                .findFirst()
                .orElse(null);

        return new ClaimResult(gameNumber, state, winner);
    }

    private Location applyAction(final Location loc, final ClaimAction action) {
        return switch (action) {
            case MOVE_UP -> loc.translate(-1, 0);
            case MOVE_DOWN -> loc.translate(1, 0);
            case MOVE_LEFT -> loc.translate(0, -1);
            case MOVE_RIGHT -> loc.translate(0, 1);
            case HOLD_POSITION -> loc;
        };
    }
}
