package com.gmail.justinxvopro.battlebot.battlesystem;

public class AttackMove implements Move {

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	on.setHealth(on.getHealth() - 1);
    }

    @Override
    public String getId() {
	return "U+1F44A"; //👊
    }

    @Override
    public String getName() {
	return "Attack Move";
    }
    
}
