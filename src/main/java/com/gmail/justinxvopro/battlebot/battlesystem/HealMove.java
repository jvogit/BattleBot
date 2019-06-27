package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class HealMove implements Move {
    private int heal = 1;
    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	if(RandomUtils.chance(30)) {
	    by.setHealth(by.getHealth() + heal);
	}else {
	    by.concatSpecialMessageWithNewline(String.format("1 %s has failed!", getName()));
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
