package com.gmail.justinxvopro.BattleBot.battlesystem;

public interface Move {
    void performMove(BattlePlayer by, BattlePlayer on);
    String getId();
}
