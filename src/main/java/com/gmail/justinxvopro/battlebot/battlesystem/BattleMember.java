package com.gmail.justinxvopro.battlebot.battlesystem;

import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class BattleMember extends BattlePlayer {
    @Getter
    private Member member;
    
    public BattleMember(Member member, int health, Move...moves) {
	super(health, moves);
	this.member = member;
    }

    @Override
    public Message getBattlePanel() {
	RandomUtils.log(this, this.getSpecialMessage());
	return BattlePlayer.formDefaultBattlePanel(getName(), member.getUser().getAvatarUrl(), getStatus(), getSpecialMessage(), getHealth()+"");
    }

    @Override
    public String getName() {
	return member.getEffectiveName();
    }
    
    public static BattleMember formDefaultBattleMember(Member m) {
	return new BattleMember(m, 50, new AttackMove(), new HealMove());
    }
}
