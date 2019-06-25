package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;

import net.dv8tion.jda.api.entities.TextChannel;

public class DualBattle extends Battle {
    private TextChannel output;
    private boolean hasEnded = false;

    public DualBattle(BattlePlayer one, BattlePlayer two, TextChannel output) {
	super(one, two);
	one.setOpponent(two);
	two.setOpponent(one);
	this.output = output;
    }

    @Override
    public void gameTick() {
	super.gameTick();
	if (Stream.of(this.getInvolved()).anyMatch(bp -> bp.getHealth() <= 0)) {
	    BattleManager.getBattleManager(output.getGuild()).stopBattle();
	    Stream.of(this.getInvolved()).forEach(player -> {
		BotCore.MENU_MANAGER.removeId(player.getMessage().getId());
		player.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
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
    public void start() {
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
}
