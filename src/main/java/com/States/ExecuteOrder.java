package com.States;

import com.gameplay.GameEngine;
import com.gameplay.Order;
import com.gameplay.Player;

public class ExecuteOrder implements Phase {
    GameEngine engine;
    public ExecuteOrder(GameEngine engine) {
        this.engine = engine;
    }

    public String currentPhase() {
        return "ExecuteOrder";
    }

    @Override
    public boolean executeOrder() {
        for (Player l_player : engine.d_playersList) {
            Order l_pendingOrder = l_player.next_order();
            if (l_pendingOrder != null) {
                l_pendingOrder.execute();
                return false;
            }
        }
        return true;
    }
}
