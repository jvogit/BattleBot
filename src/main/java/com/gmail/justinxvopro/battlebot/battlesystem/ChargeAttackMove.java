package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

public class ChargeAttackMove implements Move {
    private int charges = 0;
    private static final int complete_charge = 3;
    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	if(RandomUtils.chance(50)) {
	    charges++;
	    by.setSpecialMessage(by.getSpecialMessage() + "\n" + getName() + " charged " + charges + "/" + complete_charge);
	}else {
	    by.setSpecialMessage(by.getSpecialMessage() + "\n" + getName() + " failed to charge!");
	}
	
	if(charges >= 3) {
	    on.setHealth(on.getHealth() - 10);
	    by.setSpecialMessage(by.getSpecialMessage() + "\n" + getName() + " has fully charged and hit " + on.getName() + " for 10 damage.");
	    charges = 0;
	}
    }

    @Override
    public String getId() {
	return "U+270A"; //✊
    }

    @Override
    public String getName() {
	return "Charged Attack";
    }

}
