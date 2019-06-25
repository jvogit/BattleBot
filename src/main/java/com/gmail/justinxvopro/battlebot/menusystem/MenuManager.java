package com.gmail.justinxvopro.battlebot.menusystem;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class MenuManager implements EventListener {
    private Map<String, DiscordMenu> mappedMessages = new WeakHashMap<>();

    public void submit(DiscordMenu menu, TextChannel t) {
        submit(menu, t, (msg)->{});
    }

    public void submit(DiscordMenu menu, TextChannel t, Consumer<Message> to) {
        t.sendMessage(menu.getMessage()).queue(msg -> {
            for (String id : menu.reactions().stream().sorted().collect(Collectors.toList())) {
                try {
                    Emote emote = msg.getGuild().getEmotesByName(id, true).get(0);

                    msg.addReaction(emote).queue();
                } catch (Exception e) {
                    msg.addReaction(id).queue();
                }
            }
            mappedMessages.put(msg.getId(), menu);

            to.accept(msg);
        });

    }

    @Override
    public void onEvent(GenericEvent e) {
        if (e instanceof MessageReactionAddEvent) {
            MessageReactionAddEvent event = (MessageReactionAddEvent) e;

            if (!event.getChannelType().isGuild())
                return;

            if (event.getMember().getUser().isBot())
                return;

            String emoji[] = event.getReactionEmote().isEmoji()
                    ? new String[] { event.getReactionEmote().getAsCodepoints(), event.getReactionEmote().getEmoji() }
                    : new String[] { event.getReactionEmote().getName().toLowerCase(), event.getReactionEmote().getId() };

            if (!mappedMessages.containsKey(event.getMessageId()))
                return;

            DiscordMenu menu = mappedMessages.get(event.getMessageId());

            if (menu.belongGuild(event.getGuild()) && (menu.getRecepientsId().stream()
                    .anyMatch(s -> s.equalsIgnoreCase(event.getMember().getUser().getId()))
                    || menu.getRecepientsId().isEmpty())) {
                event.getTextChannel().retrieveMessageById(event.getMessageId()).queue(msg -> {
                    if (menu.execute(emoji, event.getReaction(), event.getMember(), msg))
                        mappedMessages.remove(event.getMessageId());
                });
            }
        }

    }

    public void removeId(String id) {
        mappedMessages.remove(id);
    }

}
