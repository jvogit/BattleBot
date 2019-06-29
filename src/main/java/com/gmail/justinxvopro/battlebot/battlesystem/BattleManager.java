package com.gmail.justinxvopro.battlebot.battlesystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class BattleManager {
    private static Map<Guild, BattleManager> BIG_DICT = new HashMap<>();
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private Battle onGoingBattle;
    private static Logger LOGGER = LoggerFactory.getLogger(BattleManager.class);
    
    @Setter
    @Getter
    private boolean started = false;
    
    static {
	executor.scheduleWithFixedDelay(() -> {
	    BattleManager.BIG_DICT.values().stream().filter(BattleManager::isThereOngoingBattle)
		    .filter(bm -> bm.onGoingBattle.hasStarted()).forEach(bm -> {
			try {
			    bm.tickBattle();
			} catch (Exception ex) {
			    ex.printStackTrace();
			    LOGGER.info(String.format("An error has occured while ticking battle! " + bm.onGoingBattle));
			}
		    });
	}, 0l, 1, TimeUnit.SECONDS);
    }
    
    public boolean isThereOngoingBattle() {
	return onGoingBattle != null && started;
    }
    
    public void setBattle(Battle battle) {
	LOGGER.info("Setting battle " + battle);
	this.onGoingBattle = battle;
    }
    
    public void startBattle(Battle battle) {
	LOGGER.info("Starting battle " + battle.getClass().getName());
	this.setBattle(battle);
	this.setStarted(true);
	this.onGoingBattle.start();
    }
    
    public void tickBattle() {
	this.onGoingBattle.gameTick();
    }
    
    public void stopBattle() {
	LOGGER.info("Stopping battle " + onGoingBattle.getClass().getName());
	onGoingBattle.end();
	this.setBattle(null);
	this.setStarted(false);
    }
    
    public static void update(JDA jda) {
	jda.getGuilds().forEach(guild -> {
	    if(!BIG_DICT.containsKey(guild)) {
		LOGGER.info("Add " + guild.getId() + " to battle manager dictionary.");
		BIG_DICT.put(guild, new BattleManager());
	    }
	});
    }
    
    public static void remove(Guild event) {
	if(BIG_DICT.containsKey(event)) {
	    BattleManager man = BIG_DICT.remove(event);
	    if(man.onGoingBattle != null)
		man.stopBattle();
	}
    }
    
    public static BattleManager getBattleManager(Guild g) {
	return BIG_DICT.get(g);
    }
}
