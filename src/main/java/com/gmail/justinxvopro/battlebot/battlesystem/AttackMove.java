package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

public class AttackMove implements Move {

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	if(RandomUtils.chance(10)) {
	    on.setHealth(on.getHealth() - 2);
	    by.setSpecialMessage(by.getSpecialMessage() + "\n1 Attack Move hit for double damage!");
	    RandomUtils.log(this, by.getSpecialMessage());
	}else {
	    on.setHealth(on.getHealth() - 1);
	}
    }

    @Override
    public String getId() {
	return "U+1F44A"; //👊
    }

    @Override
    public String getName() {
	return "Attack";
    }
    
}
