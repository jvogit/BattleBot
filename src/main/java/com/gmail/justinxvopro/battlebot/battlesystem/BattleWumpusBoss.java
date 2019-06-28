package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import net.dv8tion.jda.api.entities.Message;

public class BattleWumpusBoss extends BattleBossPlayer implements IBattleAIPlayer {
    private long ticks;
    
    public BattleWumpusBoss() {
	super(500, new Move[] {});
	this.setMoveSet(new Move[] {new BossAttackMove(this), new BossSweepAttackMove(this)});
	this.setAvatarUrl("https://images.discordapp.net/avatars/562551251699630081/23f23de7b41aa0bb7fac1cb4edd5deed.png?size=512");
    }

    @Override
    public void aiTick() {
	ticks++;
	
	if(RandomUtils.chance(90)) {
	    this.queueMove(this.getMoveSet()[0]);
	}
	
	if(RandomUtils.chance(10)) {
	    this.queueMove(this.getMoveSet()[1]);
	}
	
	if(ticks % 10 == 0) {
	    this.queueMove(this.getMoveSet()[1]);
	}
    }

    @Override
    public Message getBattlePanel() {
	return BattleBossPlayer.applyBossDecoration(super.getBattlePanel());
    }

    @Override
    public String getName() {
	return "Wumpus";
    }

    @Override
    public String getTaunt() {
	return "Oink";
    }

}
