package com.gmail.justinxvopro.battlebot.musicsystem;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class MusicManager {
    private Map<String, TrackHandler> musicHandle = new HashMap<>();
    private AudioPlayerManager audioManager;
    private static MusicManager instance;
    
    private MusicManager(){
        audioManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(audioManager);
    }  
    
    public void register(Guild g){
        if(!musicHandle.containsKey(g.getId())){
            TrackHandler handler = new TrackHandler(audioManager);
            
            musicHandle.put(g.getId(), handler);
        }
    }
    
    public void remove(Guild g){
        if(musicHandle.containsKey(g.getId())){
            musicHandle.remove(g.getId());
        }
    }
    
    public void play(String identifier, Member requestor){
        register(requestor.getGuild());
        requestor.getGuild().getAudioManager().setSendingHandler(new AudioPlayerSendHandler(musicHandle.get(requestor.getGuild().getId()).getPlayer()));
        audioManager.loadItemOrdered(musicHandle.get(requestor.getGuild().getId()), identifier, new AudioResultHandler(musicHandle.get(requestor.getGuild().getId()), requestor));
    }
    
    public void reset(Guild g) {
	g.getAudioManager().setSendingHandler(null);
	if(musicHandle.containsKey(g.getId())) {
	    musicHandle.get(g.getId()).getPlayer().destroy();
	    musicHandle.remove(g.getId());
	}
    }
    
    public static MusicManager getInstance(){
        if(instance == null){
            instance = new MusicManager();
        }
        
        return instance;
    }
}
