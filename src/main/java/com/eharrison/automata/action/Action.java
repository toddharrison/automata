package com.eharrison.automata.action;

public sealed interface Action permits FireAction, /*HideAction,*/ MoveAction, PingAction, ScanAction, WaitAction {
}
