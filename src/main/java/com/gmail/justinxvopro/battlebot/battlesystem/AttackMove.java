package com.gmail.justinxvopro.battlebot.battlesystem;

import org.slf4j.LoggerFactory;

public class AttackMove implements Move {

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	LoggerFactory.getLogger(AttackMove.class).info("Called! " + by + " " + on);
	on.setHealth(on.getHealth() - 1);
    }

    @Override
    public String getId() {
	return "U+1F44A"; //👊
    }
    
}
