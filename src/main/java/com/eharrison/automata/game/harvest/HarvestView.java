package com.eharrison.automata.game.harvest;

import com.eharrison.automata.Location;
import com.eharrison.automata.game.View;

import java.util.Map;
import java.util.UUID;

public record HarvestView(
        int round,
        Map<UUID, Location> botIds,
        Map<UUID, HarvestAction> lastActions,
        Integer[][] board
) implements View {}
