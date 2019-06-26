package com.gmail.justinxvopro.battlebot.battlesystem;

import net.dv8tion.jda.api.entities.Message;

public class BattleWumpusBoss extends BattleBossPlayer {

    public BattleWumpusBoss() {
	super(1000, new Move[] {});
    }

    @Override
    public void aiTick() {
    }

    @Override
    public Message getBattlePanel() {
	return BattlePlayer.formDefaultBattlePanel(this.getName(), "https://images.discordapp.net/avatars/562551251699630081/23f23de7b41aa0bb7fac1cb4edd5deed.png?size=512", getStatus(), getSpecialMessage(), getHealth()+"");
    }

    @Override
    public String getName() {
	return "Wumpus";
    }

}
