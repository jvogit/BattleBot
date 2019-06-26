package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BattleDummyAI extends BattleDummy {
    private boolean canAttack = false;
    
    public BattleDummyAI() {
	super();
    }
    
    @Override
    public void aiTick() {
	Random random = ThreadLocalRandom.current();
	canAttack = random.nextBoolean();
	if(canAttack) {
	    this.queueMove(this.getMoveSet()[random.nextInt(this.getMoveSet().length)]);
	}
    }
}
