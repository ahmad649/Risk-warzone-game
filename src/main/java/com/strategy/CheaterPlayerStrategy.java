package com.strategy;

import com.gameplay.GameEngine;
import com.orders.Order;
import com.gameplay.Parsing;
import com.gameplay.Player;
import com.model.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * A player strategy that conquers all the immediate neighboring enemy countries, and then doubles the number of armies on its countries that have enemy neighbors. The implementation of the above-stated behavior is performed by directly affecting the map during the order creation phase.
 */
public class CheaterPlayerStrategy extends PlayerStrategy {

    /**
     * Instantiates a new Cheater player strategy.
     *
     * @param p_gameEngine the game engine
     * @param p_player     the player
     */
    public CheaterPlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        super(p_gameEngine, p_player);
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.CHEATER;
    }

    @Override
    public Order createOrder(Parsing p_parsing) {
        int l_playerReinforcements = this.getplayer().getReinforcements();

        // Conquers all immediate neighboring enemy countries
        ArrayList<Country> l_tempCountryList = this.conquerAllEnemyNeighbors(l_playerReinforcements);

        for (Country l_newCountry : l_tempCountryList) {
            if (!this.getplayer().getOwnedCountries().contains(l_newCountry))
                this.getplayer().addCountryToOwnedCountries(l_newCountry);
        }

        // Get all cheater player's countries that the armies need to be doubled
        ArrayList<Country> l_countriesWithEnemyNeighbors = this.getAllCountriesWithEnemyNeighbors(this.getplayer().getOwnedCountries());

        // Doubles the number of armies on countries that have enemy neighbors
        for (Country l_country : l_countriesWithEnemyNeighbors) {
            int l_currentArmies = l_country.getArmies();

            l_country.setArmies(l_currentArmies * 2);
        }

        return null;
    }

    /**
     * Conquer all enemy neighbors.
     *
     * @param p_playerReinforcements the player reinforcements
     * @return the list of enemy neighboring countries
     */
    public ArrayList<Country> conquerAllEnemyNeighbors(int p_playerReinforcements) {
        ArrayList<Country> l_tempCountryList = new ArrayList<Country>();
        // Conquers all immediate neighboring enemy countries
        for (Country l_country : this.getplayer().getOwnedCountries()) {
            for (Country l_neighboringCountry : l_country.getNeighbors()) {
                Player l_neighboringCountryOwner = l_neighboringCountry.getOwner();
                // Check if the current neighboring country is owned by the current player
                if (p_playerReinforcements > 0 && !l_neighboringCountryOwner.getName().equals(this.getplayer().getName())) {
                    // Add country to cheater player's list of countries
                    l_neighboringCountry.setOwner(this.getplayer());

                    // Set armies
                    l_neighboringCountry.setArmies(p_playerReinforcements);
                    p_playerReinforcements = 0;

                    l_tempCountryList.add(l_neighboringCountry);

                    // Remove country from the other player's list of countries
                    l_neighboringCountryOwner.removeCountry(l_neighboringCountry.getName());

                    System.out.println("\nCheater player has conquered " + l_neighboringCountry.getName());
                }
            }
        }

        return l_tempCountryList;
    }

    /**
     * Get all countries with enemy neighbors.
     *
     * @param p_countries the list of countries to search
     * @return the list of countries that have enemy neighbors
     */
    public ArrayList<Country> getAllCountriesWithEnemyNeighbors(List<Country> p_countries) {
        ArrayList<Country> l_countriesWithEnemyNeighbors = new ArrayList<>();
        ArrayList<String> l_countryNamesWithEnemyNeighbors = new ArrayList<>();
        // Get all cheater player's countries that the armies need to be doubled
        for (Country l_country : p_countries) {
            for (Country l_neighboringCountry : l_country.getNeighbors()) {
                Player l_neighboringCountryOwner = l_neighboringCountry.getOwner();

                boolean l_isOwnerEqual = l_neighboringCountryOwner.getName().equals(this.getplayer().getName());
                boolean l_isCountryInList = l_countryNamesWithEnemyNeighbors.contains(l_country.getName());
                // Check if the current neighboring country is owned by the current player
                if (!l_isOwnerEqual && !l_isCountryInList) {
                    // Add current country to the list
                    l_countriesWithEnemyNeighbors.add(l_country);
                    l_countryNamesWithEnemyNeighbors.add(l_country.getName());
                }
            }
        }

        return l_countriesWithEnemyNeighbors;
    }
}
