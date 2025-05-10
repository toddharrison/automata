package com.eharrison.game.battle.action;

public sealed interface Action permits FireAction, HideAction, MoveAction, PingAction, ScanAction, WaitAction {
}
