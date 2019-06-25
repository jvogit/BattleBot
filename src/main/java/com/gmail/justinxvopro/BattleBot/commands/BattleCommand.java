package com.gmail.justinxvopro.BattleBot.commands;

import com.gmail.justinxvopro.BattleBot.battlesystem.AttackMove;
import com.gmail.justinxvopro.BattleBot.battlesystem.BattleDummy;
import com.gmail.justinxvopro.BattleBot.battlesystem.BattleManager;
import com.gmail.justinxvopro.BattleBot.battlesystem.BattleMember;
import com.gmail.justinxvopro.BattleBot.battlesystem.DualBattle;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BattleCommand implements Command {

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
	TextChannel channel = e.getTextChannel();
	BattleManager.getBattleManager(channel.getGuild()).startBattle(new DualBattle(new BattleMember(e.getMember(), 10, new AttackMove()), new BattleDummy(), channel));
	return true;
    }

    @Override
    public String getCommand() {
	return "battle";
    }

    @Override
    public String getDescription() {
	return "battle command";
    }

    @Override
    public String[] getAlias() {
	return null;
    }

    @Override
    public String getCategory() {
	return "games";
    }

}
