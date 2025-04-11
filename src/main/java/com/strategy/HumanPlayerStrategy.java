package com.strategy;

import com.gameplay.*;

import java.util.ArrayList;

public class HumanPlayerStrategy extends PlayerStrategy {

    Player d_currentPlayer;
    GameEngine d_gameEngine;

    public HumanPlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        super(p_gameEngine, p_player);
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.HUMAN;
    }

    public Order createOrder(Parsing p_parsing) {
        ArrayList<String> l_arguments = p_parsing.getArgArr();

        switch (p_parsing.d_commandType) {
            case "deploy" -> {
                String l_countryName = l_arguments.get(0);
                int l_num = Integer.parseInt(l_arguments.get(1));

                return new Deploy(this.d_currentPlayer, l_countryName, l_num);

            }
            case "advance" -> {
                String l_countryFrom = l_arguments.get(0);
                String l_countryTo = l_arguments.get(1);
                int l_numArmies = Integer.parseInt(l_arguments.get(2));

                return new Advance(this.d_gameEngine, this.d_currentPlayer, l_countryFrom, l_countryTo, l_numArmies);
            }
            case "bomb" -> {
                String l_countryName = l_arguments.getFirst();

                return new Bomb(this.d_currentPlayer, l_countryName);
            }
            case "blockade" -> {
                String l_countryName = l_arguments.getFirst();

                return new Blockade(this.d_gameEngine, this.d_currentPlayer, l_countryName);
            }
            case "airlift" -> {
                String l_sourceCountryName = l_arguments.get(0);
                String l_targetCountryName = l_arguments.get(1);
                int l_numArmy = Integer.parseInt(l_arguments.get(2));

                return new Airlift(this.d_currentPlayer, l_sourceCountryName, l_targetCountryName, l_numArmy);
            }
            case "negotiate" -> {
                String l_playerName = l_arguments.getFirst();

                return new Diplomacy(this.d_gameEngine, this.d_currentPlayer, l_playerName);
            }
        }

        return null;
    }
}
