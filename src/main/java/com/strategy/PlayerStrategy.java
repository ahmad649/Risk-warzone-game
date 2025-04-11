package com.strategy;

import com.gameplay.GameEngine;
import com.orders.Order;
import com.gameplay.Parsing;
import com.gameplay.Player;

/**
 * The Player strategy used in Strategy pattern.
 */
public abstract class PlayerStrategy {
    private final GameEngine d_gameEngine;
    private final Player d_player;

    /**
     * Instantiates a new Player strategy.
     *
     * @param p_gameEngine the game engine
     * @param p_player     the player
     */
    public PlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
    }

    /**
     * Get game engine.
     *
     * @return the game engine
     */
    public GameEngine getgameEngine() {
        return d_gameEngine;
    }

    /**
     * Get player.
     *
     * @return the
     */
    public Player getplayer() {
        return d_player;
    }

    /**
     * Get player behavior.
     *
     * @return the player behavior
     */
    public abstract PlayerBehavior getPlayerBehavior();

    /**
     * Create Order depending on the player strategy.
     *
     * @param p_parsing the Parsing object which contains parsed user command
     * @return the Order object
     */
    public abstract Order createOrder(Parsing p_parsing);
}
