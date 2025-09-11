package com.eharrison.automata.game;

public interface State<C extends Config, S extends State<C, S, V, A, R, B>, V extends View, A extends Action, R extends Result<C, S, V, A, R, B>, B extends Bot<C, S, V, A, R, B>> {
    View viewFor(final B bot);

    String display();
}
