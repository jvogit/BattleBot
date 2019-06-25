package com.gmail.justinxvopro.BattleBot.battlesystem;

import lombok.Getter;

public abstract class Battle {
    
    @Getter
    private BattlePlayer[] involved;
    
    public Battle(BattlePlayer... players) {
	this.involved = players;
    }
    
    abstract void gameTick();
    
    abstract void start();
    
    abstract void end();
}
