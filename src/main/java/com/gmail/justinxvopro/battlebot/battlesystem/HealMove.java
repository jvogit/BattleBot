package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

public class HealMove implements Move {

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	if(RandomUtils.chance(30)) {
	    by.setHealth(by.getHealth() + 1);
	}else {
	    by.setSpecialMessage(by.getSpecialMessage() + "\n1 Heal move failed!");
	}
    }

    @Override
    public String getId() {
	return "U+2764"; //❤️
    }

    @Override
    public String getName() {
	return "Heal";
    }

}
