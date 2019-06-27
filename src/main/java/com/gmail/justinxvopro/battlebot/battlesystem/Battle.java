package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;

public abstract class Battle {

    @Getter
    @Setter
    private BattlePlayer[] involved;

    public Battle(BattlePlayer... players) {
	this.involved = players;
    }

    public void gameTick() {
	Stream.of(this.getInvolved()).forEach(player -> {
	    if(!this.hasEnded())
		player.getMessage().editMessage(player.getBattlePanel()).queue(player::setMessage);
	});
    }
    
    public void tickAllAi() {
	Stream.of(this.getInvolved()).forEach(player  -> {
	    if (player instanceof IBattleAIPlayer) {
		((IBattleAIPlayer) player).aiTick();
	    }
	});
    }
    
    public boolean checkForQueuedMoves() {
	for(BattlePlayer player : this.involved) {
	    if(!player.getMoveExecutions().isEmpty()) {
		return true;
	    }
	}
	
	return false;
    }
    
    public void executeAllQueuedMoves() {
	Stream.of(this.involved).forEach(BattlePlayer::executeQueuedMoves);
    }

    public abstract void start();

    public abstract void end();

    public abstract boolean hasEnded();
    
    public abstract boolean hasStarted();
}
