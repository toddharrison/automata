package com.eharrison.automata.game;

public record Location(
        int row,
        int col
) {
    public Location translate(final Location other) {
        return translate(other.row, other.col);
    }

    public Location translate(final int dx, final int dy) {
        return new Location(row + dx, col + dy);
    }
}
