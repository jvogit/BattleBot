package com.gmail.justinxvopro.battlebot.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
    private static JsonNode CONFIG;
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static Logger LOGGER = LoggerFactory.getLogger(Config.class);
    public static String TOKEN, DUAL_MUSIC, BOSS_MUSIC, WUMPUS_MUSIC, PREFIX;
    public static int IDLE_TIMEOUT;

    public static void loadConfig(File configFile) {
	try {
	    CONFIG = OBJECT_MAPPER.readTree(configFile);
	    loadValues();
	} catch (IOException ex) {
	    ex.printStackTrace();
	    LOGGER.warn("IOException while trying to retrieve token from config.json: " + ex.getMessage());
	}
    }

    private static void loadValues() {
	TOKEN = CONFIG.get("token").asText();
	DUAL_MUSIC = CONFIG.get("dual-music").asText();
	BOSS_MUSIC = CONFIG.get("boss-music").asText();
	WUMPUS_MUSIC = CONFIG.get("wumpus-music").asText();
	PREFIX = CONFIG.get("prefix").asText();
	IDLE_TIMEOUT = CONFIG.get("idle-timeout").asInt();
    }
}
