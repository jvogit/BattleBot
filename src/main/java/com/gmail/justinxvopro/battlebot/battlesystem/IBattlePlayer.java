package com.gmail.justinxvopro.battlebot.battlesystem;

import net.dv8tion.jda.api.entities.Message;

public interface IBattlePlayer {
    public Move[] getMoveSet();
    public Message getBattlePanel();
    public void queueMove(Move move);
    public int getHealth();
    public BattlePlayer getOpponent();
    public void setOpponent(BattlePlayer player);
    public String getName();
    public String getAvatarUrl();
    public void setMessage(Message msg);
    public Message getMessage();
}
