package com.gmail.justinxvopro.battlebot.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.slf4j.LoggerFactory;

import com.gmail.justinxvopro.battlebot.battlesystem.BattlePlayer;
import com.gmail.justinxvopro.battlebot.battlesystem.IBattleMember;

import net.dv8tion.jda.api.entities.Member;

public class RandomUtils {
    
    public static <T> T chooseRandomly(T... lT) {
	return lT[ThreadLocalRandom.current().nextInt(lT.length)];
    }
    
    public static <T> T chooseRandomly(List<T> lT) {
	return lT.get(ThreadLocalRandom.current().nextInt(lT.size()));
    }
    
    public static boolean chance(int chance) {
	return ThreadLocalRandom.current().nextInt(100) < chance;
    }
    
    public static void log(Object o, String s) {
	LoggerFactory.getLogger(o.getClass()).info(s);
    }
    
    public static void log(Class<?> o, String s) {
	LoggerFactory.getLogger(o).info(s);
    }
    
    public static Member getMemberInVoiceChannelFromBattlePlayerArray(BattlePlayer[] members) {
	return Stream.of(members)
		.filter(p -> p instanceof IBattleMember)
		.map(bp -> ((IBattleMember) bp).getMember())
		.filter(m -> m.getVoiceState().inVoiceChannel())
		.findAny()
		.orElse(null);
    }
    
}
