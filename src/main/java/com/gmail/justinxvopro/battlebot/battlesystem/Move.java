package com.gmail.justinxvopro.battlebot.battlesystem;

public interface Move {
    void performMove(BattlePlayer by, BattlePlayer on);
    String getId();
    String getName();
}
