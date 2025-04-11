package com.strategy;

import com.gameplay.*;
import com.orders.*;

import java.util.ArrayList;

/**
 * The player strategy that requires user interaction to make decisions.
 */
public class HumanPlayerStrategy extends PlayerStrategy {
    /**
     * Instantiates a new Player strategy.
     *
     * @param p_gameEngine the game engine
     * @param p_player     the player
     */
    public HumanPlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        super(p_gameEngine, p_player);
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.HUMAN;
    }

    @Override
    public Order createOrder(Parsing p_parsing) {
        ArrayList<String> l_arguments = p_parsing.getArgArr();

        switch (p_parsing.d_commandType) {
            case "deploy" -> {
                String l_countryName = l_arguments.get(0);
                int l_num = Integer.parseInt(l_arguments.get(1));

                return new Deploy(this.d_player, l_countryName, l_num);

            }
            case "advance" -> {
                String l_countryFrom = l_arguments.get(0);
                String l_countryTo = l_arguments.get(1);
                int l_numArmies = Integer.parseInt(l_arguments.get(2));

                return new Advance(this.d_gameEngine, this.d_player, l_countryFrom, l_countryTo, l_numArmies);
            }
            case "bomb" -> {
                String l_countryName = l_arguments.getFirst();

                return new Bomb(this.d_player, l_countryName);
            }
            case "blockade" -> {
                String l_countryName = l_arguments.getFirst();

                return new Blockade(this.d_gameEngine, this.d_player, l_countryName);
            }
            case "airlift" -> {
                String l_sourceCountryName = l_arguments.get(0);
                String l_targetCountryName = l_arguments.get(1);
                int l_numArmy = Integer.parseInt(l_arguments.get(2));

                return new Airlift(this.d_player, l_sourceCountryName, l_targetCountryName, l_numArmy);
            }
            case "negotiate" -> {
                String l_playerName = l_arguments.getFirst();

                return new Diplomacy(this.d_gameEngine, this.d_player, l_playerName);
            }
        }

        return null;
    }
}
