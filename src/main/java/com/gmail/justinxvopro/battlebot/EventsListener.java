package com.gmail.justinxvopro.battlebot;

import com.gmail.justinxvopro.battlebot.battlesystem.BattleManager;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class EventsListener implements EventListener {

    @Override
    public void onEvent(GenericEvent event) {
	if(event instanceof ReadyEvent || event instanceof GuildJoinEvent) {
	    BattleManager.update(BotCore.BOT_JDA);
	}
    }
}
