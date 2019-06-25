package com.gmail.justinxvopro.battlebot.commands;

import java.util.List;

import com.gmail.justinxvopro.battlebot.battlesystem.AttackMove;
import com.gmail.justinxvopro.battlebot.battlesystem.Battle;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleDummy;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleManager;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleMember;
import com.gmail.justinxvopro.battlebot.battlesystem.DualBattle;
import com.gmail.justinxvopro.battlebot.battlesystem.VerificationManager;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BattleCommand implements Command {

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
	TextChannel channel = e.getTextChannel();
	List<Member> mentioned = e.getMessage().getMentionedMembers();
	BattleManager bManager = BattleManager.getBattleManager(e.getGuild());
	
	if(args.length <= 1) {
	    channel.sendMessage("battle - dual - spar - boss").queue();
	    return true;
	}
	
	if(bManager.isThereOngoingBattle()) {
	    channel.sendMessage("There is an ongoing battle!").queue();
	    return true;
	}
	
	if(mentioned.size() == 0) {
	    Battle battle = new DualBattle(new BattleDummy(), new BattleMember(e.getMember(), 10, new AttackMove()), channel);
	    bManager.setBattle(battle);
	    VerificationManager.submitForVerification(e.getTextChannel(), (members)->{
		bManager.startBattle(battle);
	    }, e.getMember());
	}
	
	return true;
    }

    @Override
    public String getCommand() {
	return "battle";
    }

    @Override
    public String getDescription() {
	return "battle command";
    }

    @Override
    public String[] getAlias() {
	return null;
    }

    @Override
    public String getCategory() {
	return "games";
    }

}
