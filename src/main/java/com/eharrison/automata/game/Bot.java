package com.eharrison.automata.game;

import java.util.UUID;

public abstract class Bot<S extends State<B>, V extends View, A extends Action, R extends Result<S, B>, B extends Bot<S, V, A, R, B>> implements Player {
    private final UUID id = UUID.randomUUID();
    private final String teamName;
    private final String name;

    public Bot(final String teamName, final String name) {
        this.teamName = teamName;
        this.name = name;
    }

    public final UUID getId() {
        return id;
    }

    public final String getTeamName() {
        return teamName;
    }

    public final String getName() {
        return name;
    }

    public void init(final int gamesInMatch) {};

    public void start(final int gameNumber) {};

    public abstract A act(final V view);

    public void end(final int gameNumber, final R result) {};
}
