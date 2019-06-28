package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.menusystem.MenuBuilder;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public abstract class BattlePlayer implements IBattlePlayer {
    @Getter
    @Setter
    private int health;
    @Getter
    @Setter
    private Move[] moveSet;
    @Getter
    @Setter
    private BattlePlayer opponent;
    @Getter
    @Setter
    private Message message;
    @Getter
    @Setter
    private String status = "";
    @Getter
    @Setter
    private String specialMessage = "";
    @Getter
    @Setter
    private String avatarUrl;
    @Getter
    private Map<Move, Integer> moveExecutions = new HashMap<>();

    public BattlePlayer(int health, Move... moves) {
	this.moveSet = moves;
	this.health = health;
    }

    public void queueMove(Move move) {
	if (!moveExecutions.containsKey(move)) {
	    moveExecutions.put(move, 1);
	    return;
	}

	moveExecutions.put(move, moveExecutions.get(move) + 1);
    }

    public Map<Move, Integer> executeQueuedMoves() {
	moveExecutions.entrySet().forEach(entry -> {
	    for (int i = 0; i < entry.getValue(); i++) {
		entry.getKey().performMove(this, this.getOpponent());
	    }
	});
	Map<Move, Integer> copy = new HashMap<>(this.moveExecutions);
	this.moveExecutions.clear();

	return copy;
    }

    public Message getBattlePanel() {
	return BattlePlayer.formDefaultBattlePanel(this);
    }
    
    public void concatSpecialMessageWithNewline(String s) {
	setSpecialMessage(getSpecialMessage() + "\n" + s);
    }

    public abstract String getName();
    
    public abstract String getTaunt();

    public static void sendBattlePanel(BattlePlayer player, TextChannel to) {
	if (player instanceof IBattleMember) {
	    BattlePlayer.sendBattlePanel((IBattleMember) player, to);
	} else {
	    to.sendMessage(player.getBattlePanel()).queue(msg -> {
		player.setMessage(msg);
	    });
	}
    }

    public static void sendBattlePanel(IBattleMember player, TextChannel to) {
	MenuBuilder mBuilder = MenuBuilder.builder(to.getGuild()).setMessage(player.getBattlePanel())
		.setRecipient(player.getMember());

	Stream.of(player.getMoveSet()).forEach(move -> {
	    mBuilder.assign(move.getId(), (parameters) -> {
		player.queueMove(move);
		return false;
	    });
	});

	BotCore.MENU_MANAGER.submit(mBuilder.build(), to, (msg)->{
	    msg.editMessage(player.getMember().getAsMention()).queue();
	    player.setMessage(msg);
	});
    }

    public static Message formDefaultBattlePanel(BattlePlayer player) {
	EmbedBuilder embedBuilder = new EmbedBuilder();
	embedBuilder.setAuthor("Battle Panel", "https://discord.gg", BotCore.BOT_JDA.getSelfUser().getAvatarUrl());
	embedBuilder.setFooter("Your moves down below!");
	embedBuilder.setTitle(player.getName());
	embedBuilder.setThumbnail(player.getAvatarUrl());
	embedBuilder.getDescriptionBuilder().append(player.getStatus());
	embedBuilder.addField("**Your Health**", player.getHealth() + "", false);
	embedBuilder.addField(player.getOpponent().getName() + "'s Health", player.getOpponent().getHealth()+"", false);
	String specialStatus = player.getSpecialMessage();
	if(specialStatus != null && !specialStatus.isEmpty()) {
	    embedBuilder.getDescriptionBuilder().append("\n\n" + specialStatus);
	}
	embedBuilder.setDescription(embedBuilder.getDescriptionBuilder().toString());

	return new MessageBuilder().setEmbed(embedBuilder.build()).build();
    }
}
