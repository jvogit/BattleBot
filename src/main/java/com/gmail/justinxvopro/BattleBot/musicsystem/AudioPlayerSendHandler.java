package com.gmail.justinxvopro.BattleBot.musicsystem;

import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;

class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
	this.audioPlayer = audioPlayer;
    }

    public boolean canProvide() {
	lastFrame = audioPlayer.provide();
	return lastFrame != null;
    }

    public ByteBuffer provide20MsAudio() {
	return ByteBuffer.wrap(lastFrame.getData());
    }

    public boolean isOpus() {
	return true;
    }
}
