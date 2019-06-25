package com.gmail.justinxvopro.battlebot.battlesystem;

import lombok.Getter;

public abstract class Battle {
    
    @Getter
    private BattlePlayer[] involved;
    
    public Battle(BattlePlayer... players) {
	this.involved = players;
    }
    
    public abstract void gameTick();
    
    public abstract void start();
    
    public abstract void end();
    
    public abstract boolean hasEnded();
}
