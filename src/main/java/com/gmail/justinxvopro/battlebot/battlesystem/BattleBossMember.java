package com.gmail.justinxvopro.battlebot.battlesystem;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;

public class BattleBossMember extends BattleBossPlayer implements IBattleMember {
    @Getter
    @Setter
    private Member member;
    
    public BattleBossMember(Member member, int health, Move[] moves) {
	super(health, moves);
    }
    
    @Override
    public String getName() {
	return member.getEffectiveName();
    }

    @Override
    public String getTaunt() {
	return "I am waay more powerful than all of you";
    }
}