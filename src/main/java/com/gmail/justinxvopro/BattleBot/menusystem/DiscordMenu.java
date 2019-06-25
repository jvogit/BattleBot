package com.gmail.justinxvopro.BattleBot.menusystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.justinxvopro.BattleBot.menusystem.MenuActionPlayer.WrappedActionParameters;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;

public class DiscordMenu {
    private Message m;
    private String guildId;
    private List<String> receps;
    private Map<String, MenuActionPlayer> mappedActions = new HashMap<>();
    private List<String> ordered;

    public DiscordMenu(String gid, Message m, List<String> receps, Map<String, MenuActionPlayer> actions,
	    List<String> orderedReacts) {
	this.guildId = gid;
	this.m = m;
	this.receps = receps;
	this.mappedActions = new HashMap<>(actions);
	this.ordered = orderedReacts;

    }

    public List<String> getRecepientsId() {
	return new ArrayList<>(receps);
    }

    public Message getMessage() {
	return m;
    }

    public Collection<String> reactions() {
	return ordered;
    }

    public boolean belongGuild(Guild g) {

	return g.getId().equalsIgnoreCase(this.guildId);

    }

    public boolean execute(String emote[], MessageReaction react, Member mem, Message m) {
	if (mappedActions.containsKey(emote[0]) || mappedActions.containsKey(emote[1])) {
	    String decided = mappedActions.containsKey(emote[0]) ? emote[0] : emote[1];
	    return mappedActions.get(decided).call(new WrappedActionParameters(decided, react, mem, m));

	}

	return false;
    }

}
