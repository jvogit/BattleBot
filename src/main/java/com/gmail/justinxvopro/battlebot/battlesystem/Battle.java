package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.stream.Stream;

import lombok.Getter;

public abstract class Battle {

    @Getter
    private BattlePlayer[] involved;

    public Battle(BattlePlayer... players) {
	this.involved = players;
    }

    public void gameTick() {
	Stream.of(this.getInvolved()).forEach(player -> {
	    if (player instanceof BattleAIPlayer) {
		((BattleAIPlayer) player).aiTick();
	    }
	    if(!this.hasEnded())
		player.getMessage().editMessage(player.getBattlePanel()).queue(player::setMessage);
	});
    };

    public abstract void start();

    public abstract void end();

    public abstract boolean hasEnded();
    
    public abstract boolean hasStarted();
}
