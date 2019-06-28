package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.commands.BattleCommand;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class DualBattle extends Battle {
    private TextChannel output;
    private boolean hasEnded = false;
    private boolean hasStarted = false;
    private long ticks = 0;
    private Message preMessage;

    public DualBattle(BattlePlayer one, BattlePlayer two, TextChannel output) {
	super(one, two);
	one.setOpponent(two);
	two.setOpponent(one);
	this.output = output;
    }

    @Override
    public void gameTick() {
	ticks++;
	if (ticks == 1) {
	    this.getInvolved()[0].setStatus(this.getInvolved()[0].getTaunt());
	    this.getInvolved()[1].setStatus(this.getInvolved()[1].getTaunt());
	    preMessage = output.sendMessage(this.getInvolved()[0].getBattlePanel()).complete();
	}
	if (ticks == 2) {
	    preMessage.editMessage(this.getInvolved()[1].getBattlePanel()).complete();
	}
	if (ticks == 3) {
	    preMessage.delete().complete();
	    Stream.of(this.getInvolved()).forEach(player -> {
		BattlePlayer.sendBattlePanel(player, output, player::setMessage);
	    });
	}
	if (ticks > 3) {
	    this.tickAllAi();
	} else {
	    return;
	}
	if (!this.checkForQueuedMoves() || ticks % 3 != 0)
	    return;
	this.executeAllQueuedMoves();
	super.gameTick();
	if (Stream.of(this.getInvolved()).anyMatch(bp -> bp.getHealth() <= 0)) {
	    BattleManager.getBattleManager(output.getGuild()).stopBattle();
	    Stream.of(this.getInvolved()).forEach(player -> {
		BotCore.MENU_MANAGER.removeId(player.getMessage().getId());
		this.output.sendMessage(player.getBattlePanel()).queue();
		player.getMessage().delete().queue();
	    });
	    BattlePlayer winner = determineWinner();
	    this.output.sendMessage(BattleCommand.getFormattedMessage(winner.getName() + " has won the dual!")).queue();
	} else {
	    Stream.of(this.getInvolved()).forEach(player -> {
		player.setStatus("");
		player.setSpecialMessage("");
	    });
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
	    String status = executions.entrySet().stream()
		    .map(entry -> entry.getKey().getName() + " has executed " + entry.getValue() + " times.")
		    .collect(Collectors.joining("\n"));
	    if (status.isEmpty())
		return;
	    player.setStatus(status);
	});
    }

    @Override
    public void start() {
	this.hasStarted = true;
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
