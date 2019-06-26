package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import net.dv8tion.jda.api.entities.TextChannel;

public class BossBattle extends Battle {
    private boolean ended = false, started = false;
    private long ticks = 0;
    private TextChannel output;
    private BattleBossPlayer boss;
    private BattlePlayer[] players;
    
    public BossBattle(TextChannel output, BattleBossPlayer boss, BattlePlayer... players) {
	this.output = output;
	this.boss = boss;
	this.players = players;
	boss.setOpponents(players);
	Stream.of(players).forEach(p -> p.setOpponent(boss));
	BattlePlayer[] involved = new BattlePlayer[players.length + 1];
	List<BattlePlayer> list = new ArrayList<>(Arrays.asList(players));
	list.add(0, boss);
	this.setInvolved(list.toArray(involved));
    }
    
    @Override
    public void gameTick() {
	ticks++;
	if(ticks > 1 && ticks <= 3) {
	    boss.setSpecialMessage("I will defeat you!");
	    boss.getMessage().editMessage(boss.getBattlePanel()).queue();
	}
    }
    
    @Override
    public void start() {
	this.started = true;
	BattlePlayer.sendBattlePanel(boss, output, boss::setMessage);
    }

    @Override
    public void end() {
    }

    @Override
    public boolean hasEnded() {
	return false;
    }

    @Override
    public boolean hasStarted() {
	return false;
    }

}
