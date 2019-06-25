package com.gmail.justinxvopro.battlebot.menusystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;

@FunctionalInterface
public interface MenuActionPlayer {
    boolean call(WrappedActionParameters parameters);
    
    @AllArgsConstructor
    public static class WrappedActionParameters {
	@Getter
	private String emoteId;
	@Getter
	private MessageReaction emote;
	@Getter
	private Member member;
	@Getter
	private Message message;
    }
}
