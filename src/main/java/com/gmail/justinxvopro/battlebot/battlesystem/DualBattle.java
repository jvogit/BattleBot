package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.justinxvopro.battlebot.BotCore;

import net.dv8tion.jda.api.entities.TextChannel;

public class DualBattle extends Battle {
    private TextChannel output;
    private boolean hasEnded = false;
    private boolean hasStarted = false;
    private long ticks = 1;
    
    public DualBattle(BattlePlayer one, BattlePlayer two, TextChannel output) {
	super(one, two);
	one.setOpponent(two);
	two.setOpponent(one);
	this.output = output;
    }

    @Override
    public void gameTick() {
	ticks++;
	if(ticks > 3) {
	    this.tickAllAi();
	}
	if(!this.hasStarted || !this.checkForQueuedMoves() || ticks % 3 != 0)
	    return;
	this.executeAllQueuedMoves();
	super.gameTick();
	if (Stream.of(this.getInvolved()).anyMatch(bp -> bp.getHealth() <= 0)) {
	    BattleManager.getBattleManager(output.getGuild()).stopBattle();
	    Stream.of(this.getInvolved()).forEach(player -> {
		BotCore.MENU_MANAGER.removeId(player.getMessage().getId());
		player.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
	    });
	    BattlePlayer winner = determineWinner();
	    this.output.sendMessage(winner.getName() + " has won the dual!").queue();
	}
    }

    private BattlePlayer determineWinner() {
	return this.getInvolved()[0].getHealth() >= this.getInvolved()[1].getHealth() ? this.getInvolved()[0]
		: this.getInvolved()[1];
    }
    
    @Override
    public void executeAllQueuedMoves() {
	Stream.of(this.getInvolved()).forEach(player -> {
	    Map<Move, Integer> executions = player.executeQueuedMoves();
	    String status = executions.entrySet().stream().map(entry -> entry.getKey().getName() + " has executed " + entry.getValue() + " times.").collect(Collectors.joining("\n"));
	    if(status.isEmpty())
		return;
	    player.setStatus(status);
	});
    }

    @Override
    public void start() {
	this.hasStarted = true;
	Stream.of(this.getInvolved()).forEach(player -> {
	    BattlePlayer.sendBattlePanel(player, output, player::setMessage);
	});
    }

    @Override
    public void end() {
	this.hasEnded = true;
    }

    @Override
    public boolean hasEnded() {
	return this.hasEnded;
    }

    @Override
    public boolean hasStarted() {
	return this.hasStarted;
    }
}
