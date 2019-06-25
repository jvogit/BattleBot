package com.gmail.justinxvopro.BattleBot.battlesystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

public class BattleDummy extends BattlePlayer {

    public BattleDummy() {
	super(10, new Move[] {new AttackMove()});
    }

    @Override
    Message getBattlePanel() {
	EmbedBuilder embedBuilder = new EmbedBuilder();
	embedBuilder.setTitle("BATTLE DUMMY");
	embedBuilder.addField("Health", this.getHealth()+"", true);
	
	return new MessageBuilder().setEmbed(embedBuilder.build()).build();
    }

    @Override
    String getName() {
	return "BATTLE DUMMY";
    }

}
