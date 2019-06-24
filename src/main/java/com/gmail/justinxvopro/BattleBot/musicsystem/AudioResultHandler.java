package com.gmail.justinxvopro.BattleBot.musicsystem;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

class AudioResultHandler implements AudioLoadResultHandler {
    private TextChannel output;
    private TrackHandler handler;
    private Member member;
    
    public AudioResultHandler(TrackHandler handler, Member m, TextChannel output){
        this.output = output;
        this.member = m;
        this.handler = handler;
    }
    
    @Override
    public void loadFailed(FriendlyException arg0) {
        output.sendMessage("Load failure: " + arg0).queue();
    }

    @Override
    public void noMatches() {
        output.sendMessage("No matches").queue();
    }
    
    @Override
    public void trackLoaded(AudioTrack arg0) {
        handler.play(member, arg0);
        output.sendMessage(arg0.getInfo().title + " Playing").queue();
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
    }
}
