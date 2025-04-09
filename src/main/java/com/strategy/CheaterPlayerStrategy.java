package com.strategy;

import com.gameplay.GameEngine;
import com.gameplay.Order;
import com.gameplay.Parsing;
import com.gameplay.Player;
import com.model.Country;

import java.util.ArrayList;

public class CheaterPlayerStrategy implements PlayerStrategy {
    private final GameEngine d_gameEngine;
    private final Player d_player;

    public CheaterPlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.CHEATER;
    }

    @Override
    public Order createOrder(Parsing p_parsing) {
        int l_playerReinforcements = this.d_player.getReinforcements();

        ArrayList<Country> l_tempCountryList = new ArrayList<Country>();
        // Conquers all immediate neighboring enemy countries
        for (Country l_country : this.d_player.getOwnedCountries()) {
            for (Country l_neighboringCountry : l_country.getNeighbors()) {
                Player l_neighboringCountryOwner = l_neighboringCountry.getOwner();
                // Check if the current neighboring country is owned by the current player
                if (l_playerReinforcements > 0 && !l_neighboringCountryOwner.getName().equals(d_player.getName())) {
                    // Add country to cheater player's list of countries
                    l_neighboringCountry.setOwner(d_player);

                    // Set armies
                    l_neighboringCountry.setArmies(l_playerReinforcements);
                    l_playerReinforcements = 0;

                    l_tempCountryList.add(l_neighboringCountry);

                    // Remove country from the other player's list of countries
                    l_neighboringCountryOwner.removeCountry(l_neighboringCountry.getName());

                    System.out.println("\nCheater player has conquered " + l_neighboringCountry.getName());
                }
            }
        }

        for (Country l_newCountry : l_tempCountryList) {
            if (!d_player.getOwnedCountries().contains(l_newCountry))
                d_player.addCountryToOwnedCountries(l_newCountry);
        }

        ArrayList<Country> l_countriesWithEnemyNeighbors = new ArrayList<>();
        ArrayList<String> l_countryNamesWithEnemyNeighbors = new ArrayList<>();
        // Get all cheater player's countries that the armies need to be doubled
        for (Country l_country : this.d_player.getOwnedCountries()) {
            for (Country l_neighboringCountry : l_country.getNeighbors()) {
                Player l_neighboringCountryOwner = l_neighboringCountry.getOwner();

                boolean l_isOwnerEqual = l_neighboringCountryOwner.getName().equals(d_player.getName());
                boolean l_isCountryInList = l_countryNamesWithEnemyNeighbors.contains(l_neighboringCountry.getName());
                // Check if the current neighboring country is owned by the current player
                if (!l_isOwnerEqual && !l_isCountryInList) {
                    // Add current country to the list
                    l_countriesWithEnemyNeighbors.add(l_country);
                    l_countryNamesWithEnemyNeighbors.add(l_country.getName());
                }
            }
        }

        // Doubles the number of armies on countries that have enemy neighbors
        for (Country l_country : l_countriesWithEnemyNeighbors) {
            int l_currentArmies = l_country.getArmies();

            l_country.setArmies(l_currentArmies * 2);
        }

        return null;
    }
}
