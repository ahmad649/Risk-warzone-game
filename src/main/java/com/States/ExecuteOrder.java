package com.States;

import com.gameplay.GameEngine;
import com.gameplay.Order;
import com.gameplay.Player;

public class ExecuteOrder implements Phase {
    GameEngine engine;
    /**
     * Instantiates a new Execute order.
     *
     * @param engine the engine
     */
    public ExecuteOrder(GameEngine engine) {
        this.engine = engine;
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
     * @return the boolean
     */
    @Override
    public boolean executeOrder() {
        for (Player l_player : engine.d_playersList) {
            Order l_pendingOrder = l_player.next_order();
            if (l_pendingOrder != null) {
                l_pendingOrder.execute();
                engine.d_logbuffer.addEntry(l_pendingOrder);
                return false;
            }
        }

        // Clear diplomacy players for all players
        for (Player l_player : engine.getPlayersList()) {
            l_player.clearDiplomacyPlayers();
        }
        return true;
    }
}
