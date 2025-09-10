package com.eharrison.automata.game;

public interface State<B extends Bot<?, ?, ?>> {
    View viewFor(final B bot);
}
