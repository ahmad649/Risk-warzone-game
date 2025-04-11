package com.States;

import com.gameplay.GameEngine;
import com.orders.Order;
import com.gameplay.Player;

public class ExecuteOrder implements Phase {
    GameEngine d_engine;
    /**
     * Instantiates a new Execute order.
     *
     * @param p_engine the engine
     */
    public ExecuteOrder(GameEngine p_engine) {
        this.d_engine = p_engine;
        System.out.println("""
                -----------------------------------------------------------------------
                                             EXECUTING ORDERS
                -----------------------------------------------------------------------
                """
        );
    }

    /**
     * Current phase string.
     *
     * @return the string
     */
    public String currentPhase() {
        return "ExecuteOrder";
    }

    /**
     * Execute order boolean.
     *
     * @return true if all orders are executed, false if still processing orders
     */
    @Override
    public boolean executeOrder() {
        for (Player l_player : d_engine.d_playersList) {
            Order l_pendingOrder = l_player.next_order();
            if (l_pendingOrder != null) {
                l_pendingOrder.execute();
                d_engine.d_logbuffer.addEntry(l_pendingOrder);
                return false;
            }
        }

        // Clear diplomacy players for all players
        for (Player l_player : d_engine.getPlayersList()) {
            l_player.clearDiplomacyPlayers();
        }
        return true;
    }
}
