package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

public class BossSweepAttackMove extends BossMove {
    private int damage = 10;
    private int charging = 0;
    private int complete_charge = 3;
    
    public BossSweepAttackMove(BattleBossPlayer boss) {
	super(boss);
    }
    
    public BossSweepAttackMove(BattleBossPlayer boss, int damage, int charging, int complete_charge) {
	super(boss);
	this.damage = damage;
	this.charging = charging;
	this.complete_charge = complete_charge;
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
	    by.concatSpecialMessageWithNewline(String.format("%s is fully charged and hits everyone for %s damage!", getName(), damage+""));
	    Stream.of(this.getBoss().getOpponents()).forEach(player -> player.setHealth(player.getHealth() - damage));
	}
	
    }

    @Override
    public String getId() {
	return "U+2694"; //⚔️
    }

    @Override
    public String getName() {
	return "Sweep Attack";
    }

}
