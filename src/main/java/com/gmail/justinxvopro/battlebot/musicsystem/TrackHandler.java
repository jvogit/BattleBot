package com.gmail.justinxvopro.battlebot.musicsystem;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class TrackHandler extends AudioEventAdapter {

    private AudioPlayer player;
    private Queue<TrackInfo> q;
    
    public TrackHandler(AudioPlayerManager m){
        this.player = m.createPlayer();
        this.player.addListener(this);
        q = new LinkedBlockingQueue<>();
    }
    
    public void play(Member m, AudioTrack track){
        q.add(new TrackInfo(m,track));
        System.out.println("Trying to play!");
        if(player.getPlayingTrack() == null){
            player.startTrack(track, true);
        }
    }
    
    public AudioPlayer getPlayer(){
        return player;
    }
    
    @Override
    public void onPlayerPause(AudioPlayer player) {
       // Player was paused
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
       // Player was resumed
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        TrackInfo info = q.element();
        
        if(info.getMember().getVoiceState().getChannel() == null){
            player.stopTrack();
        }else{
            info.getMember().getGuild().getAudioManager().openAudioConnection(info.getMember().getVoiceState().getChannel());
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Guild g = q.poll().getMember().getGuild();
        if(q.isEmpty()){
           this.player.destroy();
           reset();
           MusicManager.getInstance().remove(g);
           g.getAudioManager().closeAudioConnection();
        }else{
           player.playTrack(q.element().getTrack());
        }
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        // An already playing track threw an exception (track end event will still be received separately)
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
       // Audio track has been unable to provide us any audio, might want to just start a new track
    }
    
    public TrackInfo getCurrentInfo(){
        return q.isEmpty() ? null : q.element();
    }
    
    public void reset(){
        q.clear();
    }
    
    public class TrackInfo{
        private final Member m;
        private final AudioTrack track;
        
        public TrackInfo(Member m, AudioTrack track){
            this.m = m;
            this.track = track;
        }
        
        public Member getMember(){
            return m;
        }
        
        public AudioTrack getTrack(){
            return track;
        }
    }
    
}
