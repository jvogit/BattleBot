package com.gmail.justinxvopro.battlebot.battlesystem;

public class AttackMove implements Move {

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	on.setHealth(on.getHealth() - 1);
    }

    @Override
    public String getId() {
	return "1F44A"; //👊
    }
    
}
