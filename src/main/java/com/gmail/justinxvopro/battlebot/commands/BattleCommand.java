package com.gmail.justinxvopro.battlebot.commands;

import java.awt.Color;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.battlesystem.AttackMove;
import com.gmail.justinxvopro.battlebot.battlesystem.Battle;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleBossPlayer;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleDummyAI;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleManager;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleMember;
import com.gmail.justinxvopro.battlebot.battlesystem.BattlePlayer;
import com.gmail.justinxvopro.battlebot.battlesystem.BattleWumpusBoss;
import com.gmail.justinxvopro.battlebot.battlesystem.BossBattle;
import com.gmail.justinxvopro.battlebot.battlesystem.DualBattle;
import com.gmail.justinxvopro.battlebot.battlesystem.Move;
import com.gmail.justinxvopro.battlebot.battlesystem.NukeMove;
import com.gmail.justinxvopro.battlebot.battlesystem.VerificationManager;

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
	List<Member> mentioned = e.getMessage().getMentionedMembers();
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
	    BattleMember member = BattleMember.formDefaultBattleMember(e.getMember());
	    member.setMoveSet(new Move[] { new AttackMove(), new NukeMove() });
	    this.setUpBossBattle(e.getTextChannel(), new BattleWumpusBoss(), member, new BattleDummyAI());
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

    private Message getHelpMessage() {
	MessageBuilder mBuilder = new MessageBuilder();
	EmbedBuilder embedBuilder = new EmbedBuilder();

	embedBuilder
		.setThumbnail("https://support.discordapp.com/hc/en-us/article_attachments/203595007/DiscordKnightMini.png")
		.setAuthor("BattleBot", "https://discord.gg", BotCore.BOT_JDA.getSelfUser().getAvatarUrl())
		.setTitle("Battle commands").setColor(Color.RED)
		.addField("!battle spar", "Train with a Battle Dummy!", false)
		.addField("!battle dual", "Dual with an opponent!", false)
		.addField("!battle boss", "Fight the boss!", false);

	return mBuilder.setEmbed(embedBuilder.build()).build();
    }

    private void setUpDualBattle(BattlePlayer player1, BattlePlayer player2, TextChannel out) {
	BattleManager bManager = BattleManager.getBattleManager(out.getGuild());
	Battle battle = new DualBattle(player1, player2, out);
	bManager.setBattle(battle);
	Set<Member> potential_members = Stream.of(player1, player2).filter(bp -> bp instanceof BattleMember)
		.map(bp -> ((BattleMember) bp).getMember()).collect(Collectors.toSet());
	VerificationManager.submitForVerification(out, (members) -> {
	    if (!bManager.isStarted()) {
		bManager.setStarted(true);
		out.sendMessage("Starting battle. . .").queue(msg -> {
		    msg.delete().queueAfter(5, TimeUnit.SECONDS, (v) -> {
			bManager.startBattle(battle);
		    });
		});
	    } else {
		out.sendMessage(Stream.of(members).map(mem -> mem.getAsMention()).collect(Collectors.joining("\n"))
			+ "\nYour battle has expired!").queue();
	    }
	}, potential_members.toArray(new Member[potential_members.size()]));
    }

    private void setUpBossBattle(TextChannel out, BattleBossPlayer boss, BattlePlayer... players) {
	BattleManager bManager = BattleManager.getBattleManager(out.getGuild());
	Battle battle = new BossBattle(out, boss, players);
	bManager.setBattle(battle);
	Set<Member> potential_members = Stream.of(players).filter(bp -> bp instanceof BattleMember)
		.map(bp -> ((BattleMember) bp).getMember()).collect(Collectors.toSet());
	VerificationManager.submitForVerification(out, (members) -> {
	    if (!bManager.isStarted()) {
		bManager.setStarted(true);
		out.sendMessage("Starting battle. . .").queue(msg -> {
		    msg.delete().queueAfter(5, TimeUnit.SECONDS, (v) -> {
			bManager.startBattle(battle);
		    });
		});
	    } else {
		out.sendMessage(Stream.of(members).map(mem -> mem.getAsMention()).collect(Collectors.joining("\n"))
			+ "\nYour battle has expired!").queue();
	    }
	}, potential_members.toArray(new Member[potential_members.size()]));
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
