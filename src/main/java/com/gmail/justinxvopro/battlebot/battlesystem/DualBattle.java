package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.stream.Stream;

import net.dv8tion.jda.api.entities.TextChannel;

public class DualBattle extends Battle {
    private TextChannel output;
    
    public DualBattle(BattlePlayer one, BattlePlayer two, TextChannel output) {
	super(one, two);
	one.setOpponent(two);
	two.setOpponent(one);
	this.output = output;
    }

    @Override
    public void gameTick() {
	Stream.of(this.getInvolved()).forEach(player -> {
	    player.getMessage().editMessage(player.getBattlePanel()).queue(player::setMessage);
	});
	if(Stream.of(this.getInvolved()).anyMatch(bp -> bp.getHealth() <= 0)) {
	    determineWinner();
	    BattleManager.getBattleManager(output.getGuild()).stopBattle();
	}
    }
    
    private BattlePlayer determineWinner() {
	return this.getInvolved()[0].getHealth() >= this.getInvolved()[1].getHealth() ? this.getInvolved()[0] : this.getInvolved()[1];
    }

    @Override
    public void start() {
	Stream.of(this.getInvolved()).forEach(player -> {
	    BattlePlayer.sendBattlePanel(player, output, player::setMessage);
	});
    }

    @Override
    public void end() {
    }
}
