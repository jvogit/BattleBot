package com.gmail.justinxvopro.battlebot.battlesystem;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

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
    
    public static Message applyBossDecoration(Message m) {
	MessageBuilder mBuilder = new MessageBuilder();
	EmbedBuilder eBuilder = new EmbedBuilder(m.getEmbeds().get(0));
	MessageEmbed emb = m.getEmbeds().get(0);
	
	eBuilder.setColor(Color.MAGENTA);
	eBuilder.setTitle(emb.getTitle() + " - BOSS");
	
	return mBuilder.setEmbed(eBuilder.build()).build();
    }
}
