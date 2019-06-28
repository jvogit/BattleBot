package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.musicsystem.MusicManager;
import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;

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
    
    public static void queueBattleMusic(Battle battle, String id) {
	Member voiceMember = RandomUtils.getMemberInVoiceChannelFromBattlePlayerArray(battle.getInvolved());
	if(voiceMember != null) {
	    MusicManager.getInstance().play(id, voiceMember);
	}
    }

    public abstract void start();

    public abstract void end();

    public abstract boolean hasEnded();
    
    public abstract boolean hasStarted();
}
