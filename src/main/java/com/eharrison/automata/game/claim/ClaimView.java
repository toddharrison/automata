package com.eharrison.automata.game.claim;

import com.eharrison.automata.game.Location;
import com.eharrison.automata.game.View;

import java.util.Map;
import java.util.UUID;

public record ClaimView(
        int round,
        Map<UUID, Location> botIds,
        Map<UUID, ClaimAction> lastActions,
        UUID[][] board
) implements View {}
