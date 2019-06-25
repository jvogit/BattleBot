package com.gmail.justinxvopro.battlebot.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {
    public boolean execute(MessageReceivedEvent e, String[] args);

    public String getCommand();

    public String getDescription();

    public String[] getAlias();

    public String getCategory();

    public default String getFullDescription() {
        return "__**" + getCommand() + "**__ ~ " + (getDescription() == null ? "" : getDescription());
    };

    public default Collection<String> getAliasAsList() {
        return (getAlias() == null) ? new ArrayList<String>() : Arrays.asList(getAlias());
    }

    public static String joinArguments(String[] args) {
        return joinArguments(args, 1);
    }

    public static String joinArguments(String[] args, int start) {
        String joined = "";

        for (int i = start; i < args.length; i++) {
            joined += args[i] + " ";
        }

        return joined.trim();
    }
}
