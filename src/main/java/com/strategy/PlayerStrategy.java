package com.strategy;

import com.gameplay.Order;
import com.gameplay.Parsing;
import com.gameplay.Player;

public interface PlayerStrategy {

    PlayerBehavior getPlayerBehavior();

    Order createOrder(Parsing p_parsing);
}
