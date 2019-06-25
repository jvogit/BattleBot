package com.gmail.justinxvopro.battlebot.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.justinxvopro.battlebot.CommandListener;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand implements Command {
    private static Map<String, List<String>> mappedCat = new HashMap<>();

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
	this.loadCommandsAndCategories();

	TextChannel t = e.getTextChannel();

	if (args.length == 1) {
	    this.sendCategoryList(t);

	    return true;
	}
	String category = Command.joinArguments(args).toLowerCase();

	this.sendCommandsFromCategory(t, category);

	return true;
    }

    private void loadCommandsAndCategories() {
	if (!mappedCat.isEmpty())
	    return;

	for (Command c : CommandListener.COMMAND_LIST) {
	    if (c.getCategory() == null)
		continue;

	    if (mappedCat.containsKey(c.getCategory())) {
		mappedCat.get(c.getCategory()).add(c.getFullDescription());
	    } else {
		List<String> l = new ArrayList<>();
		l.add(c.getFullDescription());
		mappedCat.put(c.getCategory(), l);
	    }
	}
    }

    private void sendCategoryList(TextChannel t) {
	String msg = "**CATEGORIES** - type !help <desired category>\n\n";

	for (String c : mappedCat.keySet()) {
	    if (msg.length() + this.formatCategory(c).length() > 2000) {
		t.sendMessage(msg.trim()).queue();
		msg = formatCategory(c);

		continue;
	    }
	    msg += formatCategory(c);
	}

	t.sendMessage(msg.trim()).queue();
    }

    private void sendCommandsFromCategory(TextChannel t, String cat) {
	if (!mappedCat.containsKey(cat)) {
	    t.sendMessage("No such category!").queue();
	    return;
	}

	String msg = formatCategory(cat);
	List<String> l = mappedCat.get(cat);

	for (String commandDescription : l) {
	    // logic for sendingmessage > 2000 char limit. sends in batches!
	    if (msg.length() + commandDescription.length() + "\n".length() > 2000) {
		t.sendMessage(msg.trim()).queue();
		msg = commandDescription + "\n";
		continue;
	    }
	    msg += commandDescription + "\n";
	}

	t.sendMessage(msg.trim()).queue();
	;
    }

    private String formatCategory(String s) {
	return ("**[" + s.toUpperCase() + "]**\n\n");
    }

    // implement methods!
    @Override
    public String getCategory() {
	return "general";
    }

    @Override
    public String getCommand() {
	return "help";
    }

    @Override
    public String getDescription() {
	return "List of commands separated by categories!";
    }

    @Override
    public String[] getAlias() {
	// can be null
	return new String[] { "?", "commands" };
    }

}
