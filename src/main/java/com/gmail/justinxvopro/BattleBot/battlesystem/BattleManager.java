package com.gmail.justinxvopro.BattleBot.battlesystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class BattleManager {
    private Battle onGoingBattle;
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    static {
	executor.scheduleWithFixedDelay(()->{
	    BattleManager.BIG_DICT.values().stream().filter(BattleManager::isThereOngoingBattle).forEach(BattleManager::tickBattle);
	}, 0l, 1, TimeUnit.SECONDS);
    }
    
    public boolean isThereOngoingBattle() {
	return onGoingBattle != null;
    }
    
    public void startBattle(Battle battle) {
	this.onGoingBattle = battle;
	this.onGoingBattle.start();
    }
    
    public void tickBattle() {
	this.onGoingBattle.gameTick();
    }
    
    public void stopBattle() {
	onGoingBattle.end();
	this.onGoingBattle = null;
    }
    
    private static Map<Guild, BattleManager> BIG_DICT = new HashMap<>();
    public static void update(JDA jda) {
	BIG_DICT.clear();
	jda.getGuilds().forEach(guild -> {
	    BIG_DICT.put(guild, new BattleManager());
	});
    }
    
    public static BattleManager getBattleManager(Guild g) {
	return BIG_DICT.get(g);
    }
}