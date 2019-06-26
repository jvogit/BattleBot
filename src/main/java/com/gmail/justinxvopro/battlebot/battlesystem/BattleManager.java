package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class BattleManager {
    private static Map<Guild, BattleManager> BIG_DICT = new HashMap<>();
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
    private Battle onGoingBattle;
    @Setter
    @Getter
    private boolean started = false;
    
    static {
	executor.scheduleWithFixedDelay(()->{
	    try {
		BattleManager.BIG_DICT.values().stream().filter(BattleManager::isThereOngoingBattle).forEach(BattleManager::tickBattle);
	    }catch(Exception ex) {
		ex.printStackTrace();
	    }
	}, 0l, 1, TimeUnit.SECONDS);
    }
    
    public boolean isThereOngoingBattle() {
	return onGoingBattle != null && started;
    }
    
    public void setBattle(Battle battle) {
	this.onGoingBattle = battle;
    }
    
    public void startBattle(Battle battle) {
	this.setBattle(battle);
	this.setStarted(true);
	this.onGoingBattle.start();
    }
    
    public void tickBattle() {
	this.onGoingBattle.gameTick();
    }
    
    public void stopBattle() {
	onGoingBattle.end();
	this.setBattle(null);
	this.setStarted(false);
    }
    
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
