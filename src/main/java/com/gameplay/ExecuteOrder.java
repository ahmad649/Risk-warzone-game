package com.gameplay;

import com.States.Phase;

public class ExecuteOrder implements Phase {
    GameEngine engine;
    public ExecuteOrder(GameEngine engine) {
        this.engine = engine;
    }
    @Override
    public boolean executeOrder() {
        for (Player l_player : engine.d_playersList) {
            Order l_pendingOrder = l_player.next_order();
            if (l_pendingOrder != null) {
                l_pendingOrder.execute();
                return true;
            }
        }
        return false;
    }
}
