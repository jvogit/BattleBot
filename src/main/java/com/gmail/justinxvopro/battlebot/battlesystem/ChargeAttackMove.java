package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ChargeAttackMove implements Move {
    private int damage = 10;
    private int charges = 0;
    private int complete_charge = 3;
    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	if(RandomUtils.chance(50)) {
	    charges++;
	    by.concatSpecialMessageWithNewline(String.format("%s is charging. . . %s/%s", getName(), charges+"", complete_charge+""));
	}else {
	    by.concatSpecialMessageWithNewline(String.format("%s failed to charge!", getName()));
	}
	
	if(charges >= 3) {
	    on.setHealth(on.getHealth() - damage);
	    by.concatSpecialMessageWithNewline(String.format("%s is fully charged and hits for %s damage!", getName(), damage+""));
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
