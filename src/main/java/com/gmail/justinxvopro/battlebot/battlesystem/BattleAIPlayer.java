package com.gmail.justinxvopro.battlebot.battlesystem;

public abstract class BattleAIPlayer extends BattlePlayer {

    public BattleAIPlayer(int health, Move[] moves) {
	super(health, moves);
    }
    
    public abstract void aiTick();
    
}
