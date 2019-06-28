package com.gmail.justinxvopro.battlebot.commands;

import java.io.File;

import com.gmail.justinxvopro.battlebot.utils.Config;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ConfigCommand implements Command {

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
	if(e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
	    e.getTextChannel().sendMessage("Reloading config. . .").queue(msg -> {
		Config.loadConfig(new File("config.json"));
		msg.editMessage("Reloaded config!").queue();
	    });
	}
	return true;
    }

    @Override
    public String getCommand() {
	return "config";
    }

    @Override
    public String getDescription() {
	return "updates config";
    }

    @Override
    public String[] getAlias() {
	return new String[] {"reload"};
    }

    @Override
    public String getCategory() {
	return "admin";
    }

}
