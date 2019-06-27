package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

public class SweepAttackBossMove extends BossMove {
    private int charging = 0;
    private final int complete_charge = 3;
    
    public SweepAttackBossMove(BattleBossPlayer boss) {
	super(boss);
    }

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	if(RandomUtils.chance(50)) {
	    by.concatSpecialMessageWithNewline(String.format("%s failed to charge!", getName()));
	}else {
	    charging++;
	    by.concatSpecialMessageWithNewline(String.format("%s is charging! %s/%s", getName(), charging+"", complete_charge+""));
	}
	
	if(charging >= complete_charge) {
	    charging = 0;
	    by.concatSpecialMessageWithNewline(String.format("%s is fully charged and hits everyone for 10 damage!", getName()));
	    Stream.of(this.getBoss().getOpponents()).forEach(player -> player.setHealth(player.getHealth() - 10));
	}
	
    }

    @Override
    public String getId() {
	return "U+1F9F9";
    }

    @Override
    public String getName() {
	return "Sweep Attack";
    }

}
