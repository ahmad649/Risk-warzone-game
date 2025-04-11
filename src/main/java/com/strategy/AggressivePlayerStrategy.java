package com.strategy;

import com.gameplay.*;
import com.model.Country;
import com.orders.Advance;
import com.orders.Deploy;
import com.orders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A player strategy that focuses on centralization of forces and then attack, i.e. It deploys on its strongest country, then always attack with its strongest country, then moves its armies in order to maximize aggregation of forces in one country.
 *
 */
public class AggressivePlayerStrategy extends PlayerStrategy {

    private enum Strategy {
        /**
         * Deploy strategy.
         */
        DEPLOY,
        /**
         * Attack strategy.
         */
        ATTACK,
        /**
         * Move armies strategy.
         */
        MOVE_ARMIES}

    private Strategy d_currentStrategy;

    /**
     * Instantiates a new Aggressive player strategy.
     *
     * @param p_gameEngine the game engine
     * @param p_player     the player
     */
    public AggressivePlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        super(p_gameEngine, p_player);
        this.d_currentStrategy = Strategy.DEPLOY;
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.AGGRESSIVE;
    }

    public Order createOrder(Parsing p_parsing) {
        Country l_strongestCountry = this.getStrongestCountry();
        if (l_strongestCountry == null) {
            return null;
        }

        // Deploy armies to the strongest country
        if (this.d_currentStrategy.equals(Strategy.DEPLOY)) {
            this.d_currentStrategy = Strategy.ATTACK;

            int l_numOfArmies = Math.round((float) this.getplayer().getReinforcements() / 2);
            return this.deployArmiesToStrongestCountry(l_strongestCountry, l_numOfArmies);
        }

        // Attack with the strongest country
        if (this.d_currentStrategy.equals(Strategy.ATTACK)) {
            this.d_currentStrategy = Strategy.MOVE_ARMIES;

            return this.attackWithStrongestCountry(l_strongestCountry);
        }

        // Move armies
        if (this.d_currentStrategy.equals(Strategy.MOVE_ARMIES)) {
            this.d_currentStrategy = Strategy.DEPLOY;

            List<Country> l_neighboringCountries = this.getOwnedNeighboringCountries(l_strongestCountry);

            if (!l_neighboringCountries.isEmpty()) {
                String l_countryFromName = l_strongestCountry.getName();
                String l_countryToName = l_neighboringCountries.getFirst().getName();

                int l_strongestCountryArmies = l_strongestCountry.getArmies() + 1;
                if (l_strongestCountryArmies < 1) {
                    l_strongestCountryArmies = 1;
                }
                Random l_random = new Random();
                int l_numOfArmies = l_random.nextInt(Math.round((float) (l_strongestCountryArmies) / 2));
                return new Advance(this.getgameEngine(), this.getplayer(), l_countryFromName, l_countryToName, l_numOfArmies);
            }

        }
        return null;
    }

    /**
     * Deploy armies to the strongest country.
     *
     * @param p_strongestCountry the strongest country object
     * @param p_numOfArmies      the number of armies
     * @return the Deploy order
     */
    public Deploy deployArmiesToStrongestCountry(Country p_strongestCountry, int p_numOfArmies) {

        return new Deploy(this.getplayer(), p_strongestCountry.getName(), p_numOfArmies);
    }

    /**
     * Attack with the strongest country.
     *
     * @param p_strongestCountry the strongest country
     * @return the Advance order
     */
    public Advance attackWithStrongestCountry(Country p_strongestCountry) {
        List<Country> l_neighboringCountries = this.getNonOwnedNeighboringCountries(p_strongestCountry);

        // Check if neighboring country is empty
        if (l_neighboringCountries.isEmpty()) {
            return null;
        }

        // Check if countryTo exists or not
        Country l_countryTo = l_neighboringCountries.getFirst();
        if (l_countryTo == null) {
            return null;
        }

        // Get the names of countryFrom and countryTo
        String l_countryFromName = p_strongestCountry.getName();
        String l_countryToName = l_countryTo.getName();

        int l_numOfArmies = Math.round((float) p_strongestCountry.getArmies() / 2);

        return new Advance(this.getgameEngine(), this.getplayer(), l_countryFromName, l_countryToName, l_numOfArmies);
    }

    /**
     * Get the strongest country of the current player.
     *
     * @return the strongest country
     */
    public Country getStrongestCountry() {
        // Get the country that has the most armies
        Country l_strongestCountry = null;
        for (Country l_country : this.getplayer().getOwnedCountries()) {
            if (l_strongestCountry == null) {
                l_strongestCountry = l_country;
            } else if (l_country.getArmies() > l_strongestCountry.getArmies()) {
                l_strongestCountry = l_country;
            }
        }
        return l_strongestCountry;
    }

    /**
     * Get neighboring countries that are not owned by the current player.
     *
     * @param p_strongestCountry the strongest country
     * @return List of non-owned neighboring countries
     */
    public List<Country> getNonOwnedNeighboringCountries(Country p_strongestCountry) {
        // Get all neighboring countries of the strongest country that are not owned by the current player
        List<Country> l_neighboringCountries = new ArrayList<>();
        if (p_strongestCountry == null) {
            return new ArrayList<>();
        }
        for (Country l_country : p_strongestCountry.getNeighbors()) {
            if (!this.getplayer().ownsCountry(l_country.getName())) {
                l_neighboringCountries.add(l_country);
            }
        }

        return l_neighboringCountries;
    }

    /**
     * Get neighboring countries of the strongest country.
     *
     * @param p_strongestCountry the strongest country
     * @return the list of neighboring countries
     */
    public List<Country> getOwnedNeighboringCountries(Country p_strongestCountry) {
        // Get all neighboring countries of the strongest country that are owned by the current player
        List<Country> l_neighboringCountries = new ArrayList<>();
        if (p_strongestCountry == null) {
            return new ArrayList<>();
        }
        for (Country l_country : p_strongestCountry.getNeighbors()) {
            if (!p_strongestCountry.getName().equals(l_country.getName()) && this.getplayer().ownsCountry(l_country.getName())) {
                l_neighboringCountries.add(l_country);
            }
        }

        return l_neighboringCountries;
    }
}
