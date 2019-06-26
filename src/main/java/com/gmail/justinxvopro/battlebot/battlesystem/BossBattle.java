package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class BossBattle extends Battle {
    private boolean ended = false, started = false;
    private long ticks = 0;
    private TextChannel output;
    private BattleBossPlayer boss;
    private BattlePlayer[] players;
    private Message dialogue;

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
	RandomUtils.log(this, "Boss ticking. . . " + ticks);
	ticks++;
	if (ticks == 1) {
	    boss.setStatus(boss.getTaunt());
	    players[0].setStatus(players[0].getTaunt());
	    dialogue = output.sendMessage(boss.getBattlePanel()).complete();
	}
	if (ticks == 2) {
	    dialogue.editMessage(players[0].getBattlePanel()).complete();
	}
	if (ticks == 3) {
	    dialogue.delete().complete();
	    Stream.of(this.getInvolved()).forEach(player -> {
		BattlePlayer.sendBattlePanel(player, output, player::setMessage);
	    });
	}
	if (ticks > 3) {
	    super.tickAllAi();
	} else {
	    return;
	}
	if (!this.checkForQueuedMoves() || ticks % (this.getInvolved().length+1) != 0) {
	    return;
	}
	this.executeAllQueuedMoves();
	super.gameTick();
	if (boss.getHealth() <= 0) {
	    this.stopBattle(false);
	} else if (Stream.of(players).filter(player -> player.getHealth() <= 0).count() == players.length) {
	    this.stopBattle(true);
	} else {
	    Stream.of(this.getInvolved()).forEach(player -> {
		player.setStatus("");
		player.setSpecialMessage("");
	    });
	}
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

    private void stopBattle(boolean bossWon) {
	BattleManager.getBattleManager(output.getGuild()).stopBattle();
	Stream.of(this.getInvolved()).forEach(player -> {
	    BotCore.MENU_MANAGER.removeId(player.getMessage().getId());
	});
	if (bossWon) {
	    output.sendMessage("Boss won!").queue();
	} else {
	    output.sendMessage("Players won!").queue();
	}
    }

    @Override
    public void start() {
	this.started = true;
    }

    @Override
    public void end() {
	this.ended = true;
    }

    @Override
    public boolean hasEnded() {
	return this.ended;
    }

    @Override
    public boolean hasStarted() {
	return this.started;
    }

}
