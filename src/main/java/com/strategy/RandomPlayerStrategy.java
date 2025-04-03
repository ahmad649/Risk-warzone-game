package com.strategy;

import com.gameplay.*;
import com.model.Country;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayerStrategy implements PlayerStrategy {
    private final GameEngine d_gameEngine;
    private final Player d_player;

    public RandomPlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
    }

    @Override
    public PlayerBehavior getPlayerBehavior() {
        return PlayerBehavior.RANDOM;
    }

    @Override
    public Order createOrder(Parsing p_parsing) {
        System.out.println("Random player create order");

        // Get random order
        Random l_random = new Random();
        int l_orderNum = l_random.nextInt(5);

        int l_numOfArmies = 0;
        String l_countryName = "";
        Country l_countryFrom = null, l_countryTo = null;
        switch (l_orderNum) {
            case 0:
                // Create deploy order
                l_numOfArmies = l_random.nextInt(this.d_player.getReinforcements() + 1);
                l_countryName = this.getOwnedCountry().getName();

                return new Deploy(this.d_player, l_countryName, l_numOfArmies);
            case 1:
                // Create advance order
                l_countryFrom = this.getOwnedCountry();
                l_countryTo = this.getNeighboringCountry();
                l_numOfArmies = l_random.nextInt(l_countryFrom.getArmies() + 1);

                return new Advance(this.d_gameEngine, this.d_player, l_countryFrom.getName(), l_countryTo.getName(), l_numOfArmies);
            case 2:
                // Create bomb order
                l_countryName = this.getNonOwnedNeighboringCountry().getName();

                return new Bomb(this.d_player, l_countryName);
            case 3:
                // Create blockade order
                l_countryName = this.getOwnedCountry().getName();

                return new Blockade(this.d_gameEngine, this.d_player, l_countryName);
            case 4:
                // Create airlift order

                // Make sure source and target country are different
                do {
                    l_countryFrom = this.getOwnedCountry();
                    l_countryTo = this.getOwnedCountry();
                } while (!l_countryFrom.getName().equals(l_countryTo.getName()));
                l_numOfArmies = l_random.nextInt(l_countryFrom.getArmies() + 1);

                return new Airlift(this.d_player, l_countryFrom.getName(), l_countryFrom.getName(), l_numOfArmies);
        }

        return null;
    }

    public Country getOwnedCountry() {
        Random l_random = new Random();
        int l_randomNum = l_random.nextInt(this.d_player.getOwnedCountries().size());

        return this.d_player.getOwnedCountries().get(l_randomNum);
    }

    public Country getNeighboringCountry() {
        ArrayList<String> l_neighboringCountryNames = new ArrayList<>();
        ArrayList<Country> l_neighboringCountries = new ArrayList<>();

        // Get all neighboring countries regardless of whether it is owned by the current player or not
        for (Country l_country : this.d_player.getOwnedCountries()) {
            for (Country l_neighbor : l_country.getNeighbors()) {
                if (!l_neighboringCountryNames.contains(l_country.getName())) {
                    l_neighboringCountryNames.add(l_country.getName());
                    l_neighboringCountries.add(l_country);
                } else if (!l_neighboringCountryNames.contains(l_neighbor.getName())) {
                    l_neighboringCountryNames.add(l_neighbor.getName());
                    l_neighboringCountries.add(l_neighbor);
                }
            }
        }

        // Return one random neighboring country
        Random l_random = new Random();
        int l_randomNum = l_random.nextInt(l_neighboringCountries.size());
        return l_neighboringCountries.get(l_randomNum);
    }

    public Country getNonOwnedNeighboringCountry() {
        // Get neighboring countries that are not owned by the current player
        ArrayList<Country> l_neighboringCountries = new ArrayList<>();
        for (Country l_country : this.d_player.getOwnedCountries()) {
            for (Country l_neighbor : l_country.getNeighbors()) {
                if (!this.d_player.ownsCountry(l_neighbor.getName())) {
                    l_neighboringCountries.add(l_neighbor);
                }
            }
        }

        // Return one random neighboring country that is not owned by the current player
        Random l_random = new Random();
        int l_randomNum = l_random.nextInt(l_neighboringCountries.size());
        return l_neighboringCountries.get(l_randomNum);
    }
}
