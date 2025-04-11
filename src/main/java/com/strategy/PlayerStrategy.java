package com.strategy;

import com.gameplay.GameEngine;
import com.gameplay.Order;
import com.gameplay.Parsing;
import com.gameplay.Player;

public abstract class PlayerStrategy {
    private final GameEngine d_gameEngine;
    private final Player d_player;

    public PlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
    }

    public GameEngine getgameEngine() {
        return d_gameEngine;
    }

    public Player getplayer() {
        return d_player;
    }

    public abstract PlayerBehavior getPlayerBehavior();

    public abstract Order createOrder(Parsing p_parsing);
}
