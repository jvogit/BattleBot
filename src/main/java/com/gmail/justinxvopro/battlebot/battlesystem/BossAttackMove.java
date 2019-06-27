package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

public class BossAttackMove extends BossMove {
    private AttackMove internal = new AttackMove();
    
    public BossAttackMove(BattleBossPlayer boss) {
	super(boss);
    }

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	BattlePlayer random = RandomUtils.chooseRandomly(this.getBoss().getOpponents());
	this.getBoss().concatSpecialMessageWithNewline(String.format("%s executed on %s", getName(), random.getName()));
	internal.performMove(by, on);
    }

    @Override
    public String getId() {
	return "U+1F44A";
    }

    @Override
    public String getName() {
	return "Attack Move";
    }

}
