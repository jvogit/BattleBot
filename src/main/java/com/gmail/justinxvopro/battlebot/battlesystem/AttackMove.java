package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AttackMove implements Move {
    private int damage = 1;
    
    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	if(RandomUtils.chance(10)) {
	    on.setHealth(on.getHealth() - (damage * 2));
	    by.setSpecialMessage(by.getSpecialMessage() + "\n1 Attack Move hit for double damage!");
	}else {
	    on.setHealth(on.getHealth() - damage);
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
