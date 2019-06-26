package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.menusystem.MenuBuilder;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public abstract class BattlePlayer {

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
	return BattlePlayer.formDefaultBattlePanel(this.getName(), this.getAvatarUrl(), getStatus(), getSpecialMessage(), getHealth()+"");
    }

    abstract String getName();
    
    abstract String getTaunt();

    public static void sendBattlePanel(BattlePlayer player, TextChannel to, Consumer<Message> messageSent) {
	if (player instanceof BattleMember) {
	    BattlePlayer.sendBattlePanel((BattleMember) player, to, messageSent);
	} else {
	    to.sendMessage(player.getBattlePanel()).queue(messageSent);
	}
    }

    public static void sendBattlePanel(BattleMember player, TextChannel to, Consumer<Message> messageSent) {
	MenuBuilder mBuilder = MenuBuilder.builder(to.getGuild()).setMessage(player.getBattlePanel())
		.setRecipient(player.getMember());

	Stream.of(player.getMoveSet()).forEach(move -> {
	    mBuilder.assign(move.getId(), (parameters) -> {
		player.queueMove(move);
		return false;
	    });
	});

	BotCore.MENU_MANAGER.submit(mBuilder.build(), to, messageSent);
    }

    public static Message formDefaultBattlePanel(String name, String avatarUrl, String status, String specialStatus, String health) {
	EmbedBuilder embedBuilder = new EmbedBuilder();
	embedBuilder.setTitle(name);
	embedBuilder.setThumbnail(avatarUrl);
	embedBuilder.getDescriptionBuilder().append(status);
	embedBuilder.addField("Health", health + "", false);
	if(specialStatus != null && !specialStatus.isEmpty()) {
	    embedBuilder.getDescriptionBuilder().append("\n\n" + specialStatus);
	}
	embedBuilder.setDescription(embedBuilder.getDescriptionBuilder().toString());

	return new MessageBuilder().setEmbed(embedBuilder.build()).build();
    }
}
