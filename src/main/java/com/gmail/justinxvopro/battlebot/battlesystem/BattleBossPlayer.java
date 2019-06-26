package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
import lombok.Setter;

public abstract class BattleBossPlayer extends BattleAIPlayer {
    @Getter
    @Setter
    private BattlePlayer[] opponents;
    
    public BattleBossPlayer(int health, Move[] moves) {
	super(health, moves);
    }
    
    @Override
    public BattlePlayer getOpponent() {
	Random random = ThreadLocalRandom.current();
	return opponents[random.nextInt(opponents.length)];
    }
}
