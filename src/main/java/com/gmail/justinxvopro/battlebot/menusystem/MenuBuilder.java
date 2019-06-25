package com.gmail.justinxvopro.battlebot.menusystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class MenuBuilder {
    private String guildId;
    private Message m;
    private List<String> memIds = new ArrayList<>();
    private List<String> orderedReacts = new ArrayList<>();
    private Map<String, MenuActionPlayer> mappedActions = new HashMap<>();
    
    public MenuBuilder(Guild g){
        guildId = g.getId();
    }
    
    public MenuBuilder setMessage(Message m){
        this.m = m;
        
        return this;
    }
    
    public MenuBuilder setRecipient(Member... m){
        memIds.addAll(Stream.of(m).map(mem -> mem.getUser().getId()).collect(Collectors.toList()));
        
        return this;
    }
    
    public MenuBuilder assign(String emoteId, MenuActionPlayer action){
        if(emoteId.startsWith("U+")) {
            emoteId = "U+" + emoteId.substring(2).toLowerCase();
        }
        this.orderedReacts.add(emoteId);
        this.mappedActions.put(emoteId, action);
        
        return this;
    }
    
    public MenuBuilder assign(char emote, MenuActionPlayer action){
        this.orderedReacts.add(String.valueOf(emote));
        this.mappedActions.put(String.valueOf(emote), action);
        
        return this;
    }
    
    public DiscordMenu build(){
        return new DiscordMenu(guildId, m, memIds, mappedActions, orderedReacts);
        
    }
    
    public static MenuBuilder builder(Guild g){
        return new MenuBuilder(g);
    }
    
}
