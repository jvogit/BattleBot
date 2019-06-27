package com.gmail.justinxvopro.battlebot.battlesystem;

import net.dv8tion.jda.api.entities.Message;

public class BattleWumpusBoss extends BattleBossPlayer implements IBattleAIPlayer {

    public BattleWumpusBoss() {
	super(1000, new Move[] {});
	this.setAvatarUrl("https://images.discordapp.net/avatars/562551251699630081/23f23de7b41aa0bb7fac1cb4edd5deed.png?size=512");
    }

    @Override
    public void aiTick() {
    }

    @Override
    public Message getBattlePanel() {
	return BattleBossPlayer.applyBossDecoration(super.getBattlePanel());
    }

    @Override
    public String getName() {
	return "Wumpus";
    }

    @Override
    public String getTaunt() {
	return "Oink";
    }

}
