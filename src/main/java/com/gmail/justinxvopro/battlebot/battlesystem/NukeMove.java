package com.gmail.justinxvopro.battlebot.battlesystem;

public class NukeMove implements Move {

    @Override
    public void performMove(BattlePlayer by, BattlePlayer on) {
	by.setSpecialMessage(by.getSpecialMessage() + "\nInstant kill used!");
	on.setHealth(0);
    }

    @Override
    public String getId() {
	return "U+2622";
    }

    @Override
    public String getName() {
	return "Instant Kill";
    }

}
