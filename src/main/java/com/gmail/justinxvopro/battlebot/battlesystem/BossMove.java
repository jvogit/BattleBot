package com.gmail.justinxvopro.battlebot.battlesystem;

import lombok.Getter;

public abstract class BossMove implements Move {
    @Getter
    private BattleBossPlayer boss;
    
    public BossMove(BattleBossPlayer boss) {
	this.boss = boss;
    }
    
}
