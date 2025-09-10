package com.eharrison.automata.game;

public interface Result<B extends Bot<?, ?,?>> {
    B winner();
}
