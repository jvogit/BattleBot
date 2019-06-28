package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BattleDummyAI extends BattleDummy implements IBattleAIPlayer {
    private boolean canAttack = false;
    
    public BattleDummyAI() {
	super();
	this.setAvatarUrl("https://images.discordapp.net/avatars/508012980194115595/c51f9f8ed4f1d288e137e179d6ab8447.png?size=512");
    }
    
    @Override
    public void aiTick() {
	Random random = ThreadLocalRandom.current();
	canAttack = random.nextBoolean();
	if(canAttack && getHealth() > 0) {
	    this.queueMove(this.getMoveSet()[random.nextInt(this.getMoveSet().length)]);
	}
    }
}
