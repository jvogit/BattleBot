package com.gmail.justinxvopro.BattleBot.menusystem;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;

@FunctionalInterface
public interface MenuActionPlayer {
    boolean call(String emoteId, MessageReaction emote, Member mem, Message m);
}
