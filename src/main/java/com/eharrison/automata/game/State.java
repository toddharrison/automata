package com.eharrison.automata.game;

public interface State<B extends Bot<?, ?, ?, ?, B>> {
    View viewFor(final B bot);

    String display();
}
