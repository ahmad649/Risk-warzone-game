package com.strategy;

import com.gameplay.*;
import com.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AggressivePlayerStrategy extends PlayerStrategy {

    private enum Strategy {DEPLOY, ATTACK, MOVE_ARMIES}

    private Strategy d_currentStrategy;

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
                return new Advance(this.getgameEngine(), this.getplayer(), l_countryFromName, l_countryToName, l_numOfArmies);
            }

        }
        return null;
    }

    public Deploy deployArmiesToStrongestCountry(Country p_strongestCountry, int p_numOfArmies) {

        return new Deploy(this.getplayer(), p_strongestCountry.getName(), p_numOfArmies);
    }

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
