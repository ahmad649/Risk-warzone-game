package com.strategy;

import com.gameplay.*;
import com.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AggressivePlayerStrategy implements PlayerStrategy {

    private enum Strategy {DEPLOY, ATTACK, MOVE_ARMIES}

    private final GameEngine d_gameEngine;
    private final Player d_player;
    private Strategy d_currentStrategy;

    public AggressivePlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
        this.d_currentStrategy = Strategy.DEPLOY;
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.AGGRESSIVE;
    }

    @Override
    public Order createOrder(Parsing p_parsing) {
        Country l_strongestCountry = this.getStrongestCountry();
        if (l_strongestCountry == null) {
            return null;
        }

        // Deploy armies to the strongest country
        if (this.d_currentStrategy.equals(Strategy.DEPLOY)) {
            return this.deployArmiesToStrongestCountry(l_strongestCountry);
        }

//        if (this.d_currentStrategy.equals(Strategy.DEPLOY)) {
//            this.d_currentStrategy = Strategy.ATTACK;
//
//            Random l_random = new Random();
//            int l_numOfArmies = l_random.nextInt(Math.round((float) (this.d_player.getReinforcements() + 1) / 2));
//            System.out.println("num of armies: " + l_numOfArmies);
//            return new Deploy(this.d_player, l_strongestCountry.getName(), l_numOfArmies);
//        }

        // Attack with the strongest country
        if (this.d_currentStrategy.equals(Strategy.ATTACK)) {
            this.d_currentStrategy = Strategy.MOVE_ARMIES;

            List<Country> l_neighboringCountries = this.getNonOwnedNeighboringCountries(l_strongestCountry);
            if (!l_neighboringCountries.isEmpty()) {
                Country l_countryTo = l_neighboringCountries.getFirst();
                if (l_countryTo == null) {
                    return null;
                }

                String l_countryFromName = l_strongestCountry.getName();
                String l_countryToName = l_countryTo.getName();

                int l_strongestCountryArmies = l_strongestCountry.getArmies() + 1;
                if (l_strongestCountryArmies < 1) {
                    l_strongestCountryArmies = 1;
                }

                Random l_random = new Random();
                int l_numOfArmies = l_random.nextInt(Math.round((float) (l_strongestCountryArmies) / 2));
                System.out.println("num of armies: " + l_numOfArmies);
                // Check if neighboring countries of the strongest country are empty or not
                return new Advance(this.d_gameEngine, this.d_player, l_countryFromName, l_countryToName, l_numOfArmies);
            }

        }

        // Move armies
        if (this.d_currentStrategy.equals(Strategy.MOVE_ARMIES)) {
            this.d_currentStrategy = Strategy.ATTACK;

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
                System.out.println("num of armies: " + l_numOfArmies);
                return new Advance(this.d_gameEngine, this.d_player, l_countryFromName, l_countryToName, l_numOfArmies);
            }

        }
        return null;
    }

    public Deploy deployArmiesToStrongestCountry(Country p_strongestCountry) {
        this.d_currentStrategy = Strategy.ATTACK;

        int l_numOfArmies = Math.round((float) (this.d_player.getReinforcements() + 1) / 2);
        return new Deploy(this.d_player, p_strongestCountry.getName(), l_numOfArmies);
    }

//    public Advance attackWithStrongestCountry(Country p_strongestCountry) {
//
//    }

    public Country getStrongestCountry() {
        // Get the country that has the most armies
        Country l_strongestCountry = null;
        for (Country l_country : this.d_player.getOwnedCountries()) {
            if (l_strongestCountry == null) {
                l_strongestCountry = l_country;
            } else if (l_country.getArmies() > l_strongestCountry.getArmies()) {
                l_strongestCountry = l_country;
            }
        }
        return l_strongestCountry;
    }

    public List<Country> getNonOwnedNeighboringCountries(Country p_strongestCountry) {
        // Get all neighboring countries of the strongest country that are not owned by the current player
        List<Country> l_neighboringCountries = new ArrayList<>();
        if (p_strongestCountry == null) {
            return new ArrayList<>();
        }
        for (Country l_country : p_strongestCountry.getNeighbors()) {
            if (!this.d_player.ownsCountry(l_country.getName())) {
                l_neighboringCountries.add(l_country);
            }
        }

        return l_neighboringCountries;
    }

    public List<Country> getOwnedNeighboringCountries(Country p_strongestCountry) {
        // Get all neighboring countries of the strongest country that are owned by the current player
        List<Country> l_neighboringCountries = new ArrayList<>();
        if (p_strongestCountry == null) {
            return new ArrayList<>();
        }
        for (Country l_country : p_strongestCountry.getNeighbors()) {
            if (!p_strongestCountry.getName().equals(l_country.getName()) && this.d_player.ownsCountry(l_country.getName())) {
                l_neighboringCountries.add(l_country);
            }
        }

        return l_neighboringCountries;
    }
}
