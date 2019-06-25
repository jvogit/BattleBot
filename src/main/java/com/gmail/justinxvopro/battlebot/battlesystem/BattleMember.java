package com.gmail.justinxvopro.battlebot.battlesystem;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class BattleMember extends BattlePlayer {
    @Getter
    private Member member;
    
    public BattleMember(Member member, int health, Move...moves) {
	super(health, moves);
	this.member = member;
    }

    @Override
    public Message getBattlePanel() {
	EmbedBuilder embedBuilder = new EmbedBuilder();
	embedBuilder.setTitle(member.getEffectiveName());
	embedBuilder.setThumbnail(member.getUser().getAvatarUrl());
	embedBuilder.addField("Health", this.getHealth()+"", true);
	
	return new MessageBuilder().setEmbed(embedBuilder.build()).build();
    }

    @Override
    public String getName() {
	return member.getEffectiveName();
    }
    
}
