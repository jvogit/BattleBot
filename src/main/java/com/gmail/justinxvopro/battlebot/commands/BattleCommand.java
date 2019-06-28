package com.gmail.justinxvopro.battlebot.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.battlesystem.Battle;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleBossMember;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleBossPlayer;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleDummyAI;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleManager;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleMember;
import com.gmail.justinxvopro.battlebot.battlesystem.BattlePlayer;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleWumpusBoss;
import com.gmail.justinxvopro.battlebot.battlesystem.BossAttackMove;
import com.gmail.justinxvopro.battlebot.battlesystem.BossBattle;
import com.gmail.justinxvopro.battlebot.battlesystem.BossSweepAttackMove;
import com.gmail.justinxvopro.battlebot.battlesystem.DualBattle;
import com.gmail.justinxvopro.battlebot.battlesystem.HealMove;
import com.gmail.justinxvopro.battlebot.battlesystem.IBattleMember;
import com.gmail.justinxvopro.battlebot.battlesystem.Move;
import com.gmail.justinxvopro.battlebot.battlesystem.VerificationManager;
import com.gmail.justinxvopro.battlebot.utils.RandomUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BattleCommand implements Command {

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
	TextChannel channel = e.getTextChannel();
	List<Member> mentioned = e.getMessage().getMentionedMembers().stream().filter(m -> !m.equals(e.getMember())).collect(Collectors.toList());
	BattleManager bManager = BattleManager.getBattleManager(e.getGuild());
	if (args.length <= 1) {
	    channel.sendMessage(this.getHelpMessage()).queue();
	    return true;
	}

	if (bManager.isThereOngoingBattle()) {
	    channel.sendMessage("There is an ongoing battle!").queue();
	    return true;
	}

	if (args[1].equalsIgnoreCase("boss")) {
	    if(e.getMessage().getMentionedMembers().size() < 1) {
		channel.sendMessage(getFormattedMessage("You need to mention at least 1 other player!")).queue();
		return true;
	    }
	    BattlePlayer[] players = new BattlePlayer[mentioned.size()];
	    BattleBossPlayer boss = this.chooseBossAndSetPlayers(e.getMember(), mentioned, players);
	    this.setUpBossBattle(e.getTextChannel(), boss, players);
	    return true;
	}
	
	if(args[1].equalsIgnoreCase("wumpus")) {
	    BattlePlayer[] players;
	    if(e.getMessage().getMentionedMembers().size() < 1) {
		players = new BattlePlayer[] {BattleMember.formDefaultBattleMember(e.getMember()), new BattleDummyAI(), new BattleDummyAI()};
		channel.sendMessage(getFormattedMessage(":( Where are your friends? 2 Battle Dummies have joined your battle against Wumpus!")).queue();
	    } else {
		players = new BattlePlayer[mentioned.size()+1];
		mentioned.add(e.getMember());
		mentioned.stream().map(member -> BattleMember.formDefaultBattleMember(member)).collect(Collectors.toList()).toArray(players);
	    }
	    this.setUpBossBattle(e.getTextChannel(), new BattleWumpusBoss(), players);
	    return true;
	}

	if (mentioned.size() == 0) {
	    this.setUpDualBattle(new BattleDummyAI(), BattleMember.formDefaultBattleMember(e.getMember()),
		    e.getTextChannel());
	} else {
	    Member target = mentioned.get(0);
	    this.setUpDualBattle(BattleMember.formDefaultBattleMember(e.getMember()),
		    BattleMember.formDefaultBattleMember(target), e.getTextChannel());
	}

	return true;
    }
    
    private BattleBossPlayer chooseBossAndSetPlayers(Member original, Collection<Member> mentioned, BattlePlayer[] toPopulateWithPlayers) {
	List<Member> list = new ArrayList<>(mentioned);
	list.add(original);
	BattleBossMember random = new BattleBossMember(RandomUtils.chooseRandomly(list), 500);
	random.setMoveSet(new Move[] {new BossAttackMove(random), new BossSweepAttackMove(random), new HealMove(10)});
	list.remove(random.getMember());
	
	for(int i = 0; i < toPopulateWithPlayers.length; i++) {
	    toPopulateWithPlayers[i] = BattleMember.formDefaultBattleMember(list.get(i));
	}
	
	return random;
    }

    private Message getHelpMessage() {
	MessageBuilder mBuilder = new MessageBuilder();
	EmbedBuilder embedBuilder = new EmbedBuilder();

	embedBuilder
		.setThumbnail("https://support.discordapp.com/hc/en-us/article_attachments/203595007/DiscordKnightMini.png")
		.setAuthor("BattleBot", "https://discord.gg", BotCore.BOT_JDA.getSelfUser().getAvatarUrl())
		.setTitle("Battle commands").setColor(Color.RED)
		.addField("!battle spar", "Train with a Battle Dummy!", false)
		.addField("!battle dual", "Dual with an opponent!", false)
		.addField("!battle boss", "Create a boss battle with your friends!", false)
		.addField("!battle wumpus", "Fight Wumpus with friends!", false);

	return mBuilder.setEmbed(embedBuilder.build()).build();
    }
    
    public static Message getFormattedMessage(String s) {
	MessageBuilder mBuilder = new MessageBuilder();
	EmbedBuilder embedBuilder = new EmbedBuilder();

	embedBuilder
		.setThumbnail("https://support.discordapp.com/hc/en-us/article_attachments/203595007/DiscordKnightMini.png")
		.setAuthor("BattleBot", "https://discord.gg", BotCore.BOT_JDA.getSelfUser().getAvatarUrl())
		.setDescription(s);

	return mBuilder.setEmbed(embedBuilder.build()).build();
    }

    private void setUpDualBattle(BattlePlayer player1, BattlePlayer player2, TextChannel out) {
	BattleManager bManager = BattleManager.getBattleManager(out.getGuild());
	Battle battle = new DualBattle(player1, player2, out);
	bManager.setBattle(battle);
	Set<Member> potential_members = Stream.of(player1, player2).filter(bp -> bp instanceof IBattleMember)
		.map(bp -> ((IBattleMember) bp).getMember()).collect(Collectors.toSet());
	this.verifyMembersForBattle(out, battle, potential_members.toArray(new Member[potential_members.size()]));
    }

    private void setUpBossBattle(TextChannel out, BattleBossPlayer boss, BattlePlayer... players) {
	BattleManager bManager = BattleManager.getBattleManager(out.getGuild());
	Battle battle = new BossBattle(out, boss, players);
	bManager.setBattle(battle);
	Set<Member> potential_members = Stream.of(battle.getInvolved()).filter(bp -> bp instanceof IBattleMember)
		.map(bp -> ((IBattleMember) bp).getMember()).collect(Collectors.toSet());
	this.verifyMembersForBattle(out, battle, potential_members.toArray(new Member[potential_members.size()]));
    }
    
    private void verifyMembersForBattle(TextChannel out, Battle battle, Member...membersList) {
	BattleManager bManager = BattleManager.getBattleManager(out.getGuild());
	VerificationManager.submitForVerification(out, (members) -> {
	    if (!bManager.isStarted()) {
		bManager.setStarted(true);
		out.sendMessage(getFormattedMessage("Starting battle. . .")).queue(msg -> {
		    msg.delete().queueAfter(2, TimeUnit.SECONDS, (v) -> {
			bManager.startBattle(battle);
		    });
		});
	    } else {
		out.sendMessage(getFormattedMessage(Stream.of(members).map(mem -> mem.getAsMention()).collect(Collectors.joining("\n"))
			+ "\nYour battle has expired!")).queue();
	    }
	}, membersList);
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
