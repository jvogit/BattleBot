package com.gmail.justinxvopro.battlebot.battlesystem;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class BattleBossMember extends BattleBossPlayer implements IBattleMember {
    @Getter
    @Setter
    private Member member;
    
    public BattleBossMember(Member member, int health) {
	super(health, null);
	this.member = member;
	this.setAvatarUrl(member.getUser().getAvatarUrl());
    }
    
    @Override
    public String getName() {
	return member.getEffectiveName();
    }
    
    @Override
    public Message getBattlePanel() {
	return BattleBossPlayer.applyBossDecoration(super.getBattlePanel());
    }

    @Override
    public String getTaunt() {
	return "I am waay more powerful than all of you";
    }
}
