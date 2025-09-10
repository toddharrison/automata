package com.eharrison.automata.game;

public interface Result<S extends State<B>, B extends Bot<?, ?,?>> {
    S state();
    B winner();
}
