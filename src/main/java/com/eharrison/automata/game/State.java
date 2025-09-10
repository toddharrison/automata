package com.eharrison.automata.game;

public interface State<S extends State<S, V, A, R, B>, V extends View, A extends Action, R extends Result<S, V, A, R, B>, B extends Bot<S, V, A, R, B>> {
    View viewFor(final B bot);

    String display();
}
