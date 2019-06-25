package com.gmail.justinxvopro.battlebot.battlesystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

public class BattleDummy extends BattlePlayer {

    public BattleDummy() {
	super(1, new Move[] {new AttackMove()});
    }

    @Override
    public Message getBattlePanel() {
	EmbedBuilder embedBuilder = new EmbedBuilder();
	embedBuilder.setTitle("BATTLE DUMMY");
	embedBuilder.addField("Health", this.getHealth()+"", true);
	
	return new MessageBuilder().setEmbed(embedBuilder.build()).build();
    }

    @Override
    public String getName() {
	return "BATTLE DUMMY";
    }

}
