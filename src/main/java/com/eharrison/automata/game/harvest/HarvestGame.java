package com.eharrison.automata.game.harvest;

import com.eharrison.automata.Arrays;
import com.eharrison.automata.Location;
import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.Update;
import com.eharrison.automata.game.harvest.bot.HarvestBot;
import lombok.val;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HarvestGame extends Game<HarvestConfig, HarvestState, HarvestView, HarvestAction, HarvestResult, HarvestBot> {
    public HarvestGame() {
        super("Harvest");
    }

    @Override
    public void verifyRequirements(final HarvestConfig config, final List<HarvestBot> bots) {
        require(config.gamesInMatch() > 0, "Harvest requires at least 1 game in match.");
        require(bots.size() >= 2 && bots.size() <= 4, "Harvest requires 2 to 4 bots.");
        require(bots.stream().distinct().count() == bots.size(), "Harvest requires different bots.");
    }

    @Override
    public HarvestState generateStartingState(final HarvestConfig config, final List<HarvestBot> bots) {
        return new HarvestState(config.size(), bots);
    }

    @Override
    public Update<HarvestConfig, HarvestState, HarvestView, HarvestAction, HarvestResult, HarvestBot> updateState(
            final HarvestConfig config,
            final int gameNumber,
            final HarvestState state,
            final List<HarvestBot> bots
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
        val newBoard = Arrays.deepCopy(Integer.class, state.board());
        val newScores = new HashMap<>(state.scores());
        verifiedNewLocations.forEach((bot, loc) -> {
            val score = newBoard[loc.row()][loc.col()] == null ? 0 : newBoard[loc.row()][loc.col()];
            newBoard[loc.row()][loc.col()] = null;
            newScores.merge(bot, score, Integer::sum);
        });
        val newState = state.next(verifiedNewLocations, actions, newScores, newBoard);

        System.out.println(newState.display());

        return new Update<>(newState);
    }

    @Override
    public boolean isGameOver(final HarvestConfig config, final HarvestState state) {
        return state.round() >= config.maxRounds();
    }

    @Override
    public HarvestResult getGameOverResult(final int gameNumber, final HarvestState state) {
        val maxScore = state.scores().values().stream().max(Integer::compareTo).orElse(null);
        if (maxScore == null) {
            return null;
        }
        val winners = state.scores().entrySet().stream()
                .filter(e -> e.getValue().equals(maxScore))
                .map(Map.Entry::getKey)
                .toList();
        val winnerId = winners.size() == 1 ? winners.get(0).getId() : null;
        val winner = state.scores().entrySet().stream()
                .filter(e -> e.getKey().getId().equals(winnerId))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("Final Scores:");
        state.scores().forEach((bot, score) -> System.out.println("Bot "+ bot.getId() + ": " + score));

        return new HarvestResult(gameNumber, state, winner);
    }

    private Location applyAction(final Location loc, final HarvestAction action) {
        return switch (action) {
            case MOVE_UP -> loc.translate(-1, 0);
            case MOVE_DOWN -> loc.translate(1, 0);
            case MOVE_LEFT -> loc.translate(0, -1);
            case MOVE_RIGHT -> loc.translate(0, 1);
            case HOLD_POSITION -> loc;
        };
    }
}
