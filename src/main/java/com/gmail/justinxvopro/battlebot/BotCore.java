package com.gmail.justinxvopro.battlebot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.justinxvopro.battlebot.menusystem.MenuManager;
import com.gmail.justinxvopro.battlebot.utils.Config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;

public class BotCore {
    private static String TOKEN;
    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(BotCore.class);
    public static JDA BOT_JDA;
    public static final char PREFIX = '!';
    public static final MenuManager MENU_MANAGER = new MenuManager();

    public static void main(String args[]) {
	File configFile = new File("config.json");
	if (!configFile.exists()) {
	    try {
		Files.copy(BotCore.class.getResourceAsStream("/config.json"), configFile.toPath());
	    } catch (IOException e) {
		LOGGER.error("Unable to create config.json");
		return;
	    }
	}
	Config.loadConfig(configFile);
	if (args.length >= 2 && args[0].equalsIgnoreCase("-token")) {
	    LOGGER.info("Detected -token arguments using token provided");
	    TOKEN = args[1];
	} else {
	    LOGGER.info("Using config.json token");
	    TOKEN = Config.TOKEN;
	}

	try {
	    BOT_JDA = new JDABuilder().addEventListeners(new CommandListener(), BotCore.MENU_MANAGER, new EventsListener()).setToken(TOKEN).build();
	    LOGGER.info(BOT_JDA.getInviteUrl(Permission.values()));
	} catch (LoginException e) {
	    e.printStackTrace();
	    LOGGER.error("Unable to login: " + e.getMessage());
	}
    }
}
