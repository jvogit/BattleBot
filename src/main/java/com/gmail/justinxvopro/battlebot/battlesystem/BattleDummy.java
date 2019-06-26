package com.gmail.justinxvopro.battlebot.battlesystem;

import net.dv8tion.jda.api.entities.Message;

public class BattleDummy extends BattleAIPlayer {

    public BattleDummy() {
	super(2, new Move[] {new AttackMove()});
    }

    @Override
    public Message getBattlePanel() {
	return BattlePlayer.formDefaultBattlePanel(getName(), null, getStatus(), getSpecialMessage(), getHealth()+"");
    }

    @Override
    public String getName() {
	return "BATTLE DUMMY";
    }

    @Override
    public void aiTick() {
    }

    @Override
    String getTaunt() {
	return "...";
    }

}
