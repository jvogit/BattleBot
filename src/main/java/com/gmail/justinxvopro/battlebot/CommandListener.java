package com.gmail.justinxvopro.battlebot;

import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.commands.BattleCommand;
import com.gmail.justinxvopro.battlebot.commands.Command;
import com.gmail.justinxvopro.battlebot.commands.ConfigCommand;
import com.gmail.justinxvopro.battlebot.commands.HelpCommand;
import com.gmail.justinxvopro.battlebot.utils.Config;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    public static final Command[] COMMAND_LIST = {
	    new HelpCommand(),
	    new BattleCommand(),
	    new ConfigCommand()
    };
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
	Message msg = event.getMessage();
	
	if(msg.getContentRaw().isEmpty() || !msg.getContentRaw().startsWith(Config.PREFIX))
	    return;
	
	String[] split = event.getMessage().getContentRaw().substring(Config.PREFIX.length(), event.getMessage().getContentRaw().length()).split("\\s+");
	String commandBase = split[0];
	
	Stream.of(COMMAND_LIST)
	.filter(c -> c.getCommand().equalsIgnoreCase(commandBase) || c.getAliasAsList().stream().anyMatch(a->commandBase.equalsIgnoreCase(a)))
	.forEach(c -> c.execute(event, split));
    }
    
}