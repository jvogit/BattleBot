package com.gmail.justinxvopro.battlebot.musicsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Member;

class AudioResultHandler implements AudioLoadResultHandler {
    private TrackHandler handler;
    private Member member;
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    public AudioResultHandler(TrackHandler handler, Member m){
        this.member = m;
        this.handler = handler;
    }
    
    @Override
    public void loadFailed(FriendlyException arg0) {
        LOGGER.info("Load failure: " + arg0);
    }

    @Override
    public void noMatches() {
        LOGGER.info("No matches!");
    }
    
    @Override
    public void trackLoaded(AudioTrack arg0) {
        handler.play(member, arg0);
        LOGGER.info(arg0.getInfo().title + " Playing");
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
    }
}
