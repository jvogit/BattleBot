package com.gmail.justinxvopro.BattleBot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class BotCore {
    private static String TOKEN;
    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(BotCore.class);
    private static JDA BOT_JDA;
    public static final char PREFIX = ',';

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
	if (args.length >= 2 && args[0].equalsIgnoreCase("-token")) {
	    LOGGER.info("Detected -token arguments using token provided");
	    TOKEN = args[1];
	} else {
	    LOGGER.info("Using config.json token");
	    try {
		JsonNode config = OBJECT_MAPPER.readTree(configFile);
		JsonNode token = config.findPath("token");
		TOKEN = token.asText();
	    } catch (IOException ex) {
		ex.printStackTrace();
		LOGGER.warn("IOException while trying to retrieve token from config.json: " + ex.getMessage());
	    }
	}

	try {
	    BOT_JDA = new JDABuilder().setToken(TOKEN).build();
	} catch (LoginException e) {
	    e.printStackTrace();
	    LOGGER.error("Unable to login: " + e.getMessage());
	}
    }
}
