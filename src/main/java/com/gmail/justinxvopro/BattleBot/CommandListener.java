package com.gmail.justinxvopro.BattleBot;

import java.util.stream.Stream;

import com.gmail.justinxvopro.BattleBot.commands.BattleCommand;
import com.gmail.justinxvopro.BattleBot.commands.Command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    public static final Command[] COMMAND_LIST = {
	    new BattleCommand()
    };
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
	Message msg = event.getMessage();
	
	if(msg.getContentRaw().charAt(0) != BotCore.PREFIX)
	    return;
	
	String[] split = event.getMessage().getContentRaw().substring(1, event.getMessage().getContentRaw().length()).split("\\s+");
	String commandBase = split[0];
	
	Stream.of(COMMAND_LIST)
	.filter(c -> c.getCommand().equalsIgnoreCase(commandBase) || c.getAliasAsList().stream().anyMatch(a->commandBase.equalsIgnoreCase(a)))
	.forEach(c -> c.execute(event, split));
    }
    
}
