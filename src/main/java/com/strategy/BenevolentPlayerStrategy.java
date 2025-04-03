package com.strategy;

import com.gameplay.*;
import com.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenevolentPlayerStrategy implements PlayerStrategy {

    private final GameEngine d_gameEngine;
    private final Player d_player;

    public BenevolentPlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.BENEVOLENT;
    }

    @Override
    public Order createOrder(Parsing p_parsing) {
        Country l_weakestCountry = this.getWeakestCountry(this.d_player.getOwnedCountries());

        // Deploy armies to the weakest country
        if (l_weakestCountry.getArmies() == 0) {
            int l_numOfArmies = this.d_player.getReinforcements();

            return new Deploy(this.d_player, l_weakestCountry.getName(), l_numOfArmies);
        }

        Random l_random = new Random();
        int l_randomNum = l_random.nextInt(3);

        int l_numOfArmies = 0;
        Country l_strongestCountry = null;
        switch (l_randomNum) {
            case 0:
                // create advance order
                l_strongestCountry = this.getStrongestCountry();
                List<Country> l_neighboringCountries = this.getOwnedNeighboringCountries(l_strongestCountry);

                // Check if neighboring countries exist or not
                if (!l_neighboringCountries.isEmpty()) {
                    l_weakestCountry = this.getWeakestCountry(l_neighboringCountries);
                    l_numOfArmies = l_random.nextInt(l_strongestCountry.getArmies() + 1);

                    return new Advance(this.d_gameEngine, this.d_player, l_strongestCountry.getName(), l_weakestCountry.getName(), l_numOfArmies);
                }
            case 1:
                // create airlift order
                l_strongestCountry = this.getStrongestCountry();
                String l_weakestCountryName = l_weakestCountry.getName();
                l_numOfArmies = l_random.nextInt(l_strongestCountry.getArmies() + 1);

                return new Airlift(this.d_player, l_strongestCountry.getName(), l_weakestCountryName, l_numOfArmies);
            case 2:
                // create blockade order
                return new Blockade(this.d_gameEngine, this.d_player, l_weakestCountry.getName());

        }

        return null;
    }

    public Country getWeakestCountry(List<Country> p_countries) {
        // Get the country that has the lowest armies
        Country l_weakestCountry = null;
        for (Country l_country : p_countries) {
            if (l_weakestCountry == null) {
                l_weakestCountry = l_country;
            } else if (l_country.getArmies() < l_weakestCountry.getArmies()) {
                l_weakestCountry = l_country;
            }
        }
        return l_weakestCountry;
    }

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

    public ArrayList<Country> getOwnedCountries(Country p_weakestCountry) {
        // Get all owned countries except the weakest country
        ArrayList<Country> l_ownedCountries = new ArrayList<>();
        for (Country l_country : this.d_player.getOwnedCountries()) {
            if (!l_country.getName().equals(p_weakestCountry.getName())) {
                l_ownedCountries.add(l_country);
            }
        }

        return l_ownedCountries;
    }

    public List<Country> getOwnedNeighboringCountries(Country p_country) {
        // Get all neighboring countries of the weakest country that are owned by the current player
        List<Country> l_neighboringCountries = new ArrayList<>();
        for (Country l_country : p_country.getNeighbors()) {
            if (!p_country.getName().equals(l_country.getName()) && this.d_player.ownsCountry(l_country.getName())) {
                l_neighboringCountries.add(l_country);
            }
        }

        return l_neighboringCountries;
    }
}
