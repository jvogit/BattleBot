package com.gmail.justinxvopro.battlebot.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.LoggerFactory;

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
    
}
