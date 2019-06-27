package com.gmail.justinxvopro.battlebot.battlesystem;

public class BattleDummy extends BattlePlayer {

    public BattleDummy() {
	super(50, new Move[] {new AttackMove()});
    }

    @Override
    public String getName() {
	return "BATTLE DUMMY";
    }
    
    @Override
    public String getTaunt() {
	return "...";
    }

}
