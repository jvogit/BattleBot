package com.gmail.justinxvopro.battlebot.battlesystem;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.gmail.justinxvopro.battlebot.BotCore;
import com.gmail.justinxvopro.battlebot.menusystem.DiscordMenu;
import com.gmail.justinxvopro.battlebot.menusystem.MenuBuilder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class VerificationManager {

    public static void submitForVerification(TextChannel output, Consumer<Member[]> onAllVerified, Member... members) {
	VerificationInfo vInfo = new VerificationInfo(members, onAllVerified);
	MenuBuilder mBuilder = MenuBuilder.builder(output.getGuild());
	Stream.of(members).forEach(member -> {
	    EmbedBuilder eBuilder = new EmbedBuilder();
	    eBuilder.setTitle("Verify for Battle");
	    eBuilder.setThumbnail(member.getUser().getAvatarUrl());
	    MessageBuilder msgBuilder = new MessageBuilder();
	    DiscordMenu menu = mBuilder.setMessage(msgBuilder.setEmbed(eBuilder.build()).build()).setRecipient(member)
		    .assign("U+2705", (parameters) -> {
			eBuilder.setTitle("Ready for Battle");
			eBuilder.setColor(Color.GREEN);
			parameters.getMessage().editMessage(msgBuilder.setEmbed(eBuilder.build()).build()).queue();
			parameters.getMessage().clearReactions().queue(done -> {
			    if (vInfo.allVerified()) {
				vInfo.consumer.accept(vInfo.members);
				vInfo.messages.forEach(msg -> msg.delete().queue());
			    }
			});
			vInfo.verify(parameters.getMember());
			return true;
		    }).build();
	    BotCore.MENU_MANAGER.submit(menu, output, vInfo.messages::add);
	});
    }

    private static class VerificationInfo {
	private Member[] members;
	private boolean[] verified;
	private Consumer<Member[]> consumer;
	private Set<Message> messages = new HashSet<>();

	private VerificationInfo(Member[] members, Consumer<Member[]> consumer) {
	    this.members = members;
	    this.consumer = consumer;

	    verified = new boolean[this.members.length];
	    Arrays.fill(verified, false);
	}

	private boolean verify(Member m) {
	    for (int i = 0; i < this.members.length; i++) {
		if (members[i].equals(m)) {
		    verified[i] = true;
		    return true;
		}
	    }
	    return false;
	}

	private boolean allVerified() {
	    for (int i = 0; i < verified.length; i++)
		if (!(verified[i]))
		    return false;

	    return true;
	}
    }
}
