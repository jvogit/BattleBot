package com.gmail.justinxvopro.battlebot.battlesystem;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;

public class BattleMember extends BattlePlayer implements IBattleMember {
    @Getter
    private Member member;
    
    public BattleMember(Member member, int health, Move...moves) {
	super(health, moves);
	this.member = member;
	this.setAvatarUrl(this.member.getUser().getAvatarUrl());
    }

    @Override
    public String getName() {
	return member.getEffectiveName();
    }
    
    public static BattleMember formDefaultBattleMember(Member m) {
	return new BattleMember(m, 50, new AttackMove(), new HealMove(), new ChargeAttackMove());
    }

    @Override
    public String getTaunt() {
	return "You will not defeat me!";
    }
}
