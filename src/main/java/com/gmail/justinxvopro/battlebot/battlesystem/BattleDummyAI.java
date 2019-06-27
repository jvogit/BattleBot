package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BattleDummyAI extends BattleDummy implements IBattleAIPlayer {
    private boolean canAttack = false;
    
    @Override
    public void aiTick() {
	Random random = ThreadLocalRandom.current();
	canAttack = random.nextBoolean();
	if(canAttack && getHealth() > 0) {
	    this.queueMove(this.getMoveSet()[random.nextInt(this.getMoveSet().length)]);
	}
    }
}
