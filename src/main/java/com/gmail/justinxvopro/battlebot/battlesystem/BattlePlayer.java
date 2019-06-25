package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.function.Consumer;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.menusystem.MenuBuilder;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public abstract class BattlePlayer {
    
    @Getter
    @Setter
    private int health;
    @Getter
    @Setter
    private Move[] moveSet;
    @Getter
    @Setter
    private BattlePlayer opponent;
    @Getter
    @Setter
    private Message message;
    
    public BattlePlayer(int health, Move...moves) {
	this.moveSet = moves;
	this.health = health;
    }
    
    abstract Message getBattlePanel();
    abstract String getName();
    
    public static void sendBattlePanel(BattlePlayer player, TextChannel to, Consumer<Message> messageSent) {
	if(player instanceof BattleMember) {
	    BattlePlayer.sendBattlePanel((BattleMember) player, to, messageSent);
	}else {
	    to.sendMessage(player.getBattlePanel()).queue(messageSent);
	}
    }
    
    public static void sendBattlePanel(BattleMember player, TextChannel to, Consumer<Message> messageSent) {
	MenuBuilder mBuilder = MenuBuilder.builder(to.getGuild()).setRecipient(player.getMember());
	
	Stream.of(player.getMoveSet()).forEach(move -> {
	    mBuilder.assign(move.getId(), (parameters)->{
		move.performMove(player, player.getOpponent());
		return false;
	    });
	});
	
	BotCore.MENU_MANAGER.submit(mBuilder.build(), to, messageSent);
    }
}
