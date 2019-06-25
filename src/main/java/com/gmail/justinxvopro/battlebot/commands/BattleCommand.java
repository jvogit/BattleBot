package com.gmail.justinxvopro.battlebot.commands;

import com.gmail.justinxvopro.battlebot.battlesystem.AttackMove;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleDummy;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleManager;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleMember;
import com.gmail.justinxvopro.battlebot.battlesystem.DualBattle;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BattleCommand implements Command {

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
	TextChannel channel = e.getTextChannel();
	BattleManager.getBattleManager(channel.getGuild()).startBattle(new DualBattle(new BattleDummy(), new BattleMember(e.getMember(), 10, new AttackMove()), channel));
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
