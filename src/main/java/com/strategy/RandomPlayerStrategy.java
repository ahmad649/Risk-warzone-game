package com.strategy;

import com.gameplay.*;
import com.model.Country;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayerStrategy extends PlayerStrategy {

    public RandomPlayerStrategy(GameEngine p_gameEngine, Player p_player) {
        super(p_gameEngine, p_player);
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
        int l_orderNum = l_random.nextInt(2);

        int l_numOfArmies = 0;
        String l_countryName = "";
        Country l_countryFrom = null, l_countryTo = null, l_country = null;
        switch (l_orderNum) {
            case 0:
                // Create deploy order
                l_numOfArmies = l_random.nextInt(this.getplayer().getReinforcements() + 1);

                return this.deployArmiesToRandomCountry(l_numOfArmies);
            case 1:
                // Create advance order
                l_countryFrom = this.getOwnedCountry();
                l_countryTo = this.getNeighboringCountry();
                if (l_countryFrom == null || l_countryTo == null) {
                    return null;
                }

                int l_tempNum = l_countryFrom.getArmies() + 1;
                if (l_tempNum < 1) {
                    l_tempNum = 1;
                }

                l_numOfArmies = l_random.nextInt(l_tempNum);

                return this.advanceArmiesToRandomCountry(l_numOfArmies, l_countryFrom, l_countryTo);
            case 2:
                // Create bomb order
                l_country = this.getNonOwnedNeighboringCountry();
                if (l_country == null) {
                    return null;
                }
                l_countryName = l_country.getName();

                return new Bomb(this.getplayer(), l_countryName);
            case 3:
                // Create blockade order
                l_country = this.getOwnedCountry();
                if (l_country == null) {
                    return null;
                }
                l_countryName = l_country.getName();

                return new Blockade(this.getgameEngine(), this.getplayer(), l_countryName);
            case 4:
                // Create airlift order

                // Make sure source and target country are different
                do {
                    l_countryFrom = this.getOwnedCountry();
                    l_countryTo = this.getOwnedCountry();
                    if (l_countryFrom == null || l_countryTo == null) {
                        return null;
                    }
                } while (!l_countryFrom.getName().equals(l_countryTo.getName()));
                l_tempNum = l_countryFrom.getArmies() + 1;
                if (l_tempNum < 1) {
                    l_tempNum = 1;
                }
                l_numOfArmies = l_random.nextInt(l_tempNum);

                return new Airlift(this.getplayer(), l_countryFrom.getName(), l_countryFrom.getName(), l_numOfArmies);
        }

        return null;
    }

    public Deploy deployArmiesToRandomCountry(int p_numOfArmies) {
        Country l_country = this.getOwnedCountry();
        if (l_country == null) {
            return null;
        }

        String l_countryName = l_country.getName();
        return new Deploy(this.getplayer(), l_countryName, p_numOfArmies);
    }

    public Advance advanceArmiesToRandomCountry(int p_numOfArmies, Country p_countryFrom, Country p_countryTo) {
        return new Advance(this.getgameEngine(), this.getplayer(), p_countryFrom.getName(), p_countryTo.getName(), p_numOfArmies);
    }

    public Country getOwnedCountry() {
        Random l_random = new Random();
        if (this.getplayer().getOwnedCountries().isEmpty()) {
            return null;
        }
        int l_randomNum = l_random.nextInt(this.getplayer().getOwnedCountries().size());

        return this.getplayer().getOwnedCountries().get(l_randomNum);
    }

    public Country getNeighboringCountry() {
        ArrayList<String> l_neighboringCountryNames = new ArrayList<>();
        ArrayList<Country> l_neighboringCountries = new ArrayList<>();

        // Get all neighboring countries regardless of whether it is owned by the current player or not
        for (Country l_country : this.getplayer().getOwnedCountries()) {
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
        if (l_neighboringCountries.isEmpty()) {
            return null;
        }
        int l_randomNum = l_random.nextInt(l_neighboringCountries.size());
        return l_neighboringCountries.get(l_randomNum);
    }

    public Country getNonOwnedNeighboringCountry() {
        // Get neighboring countries that are not owned by the current player
        ArrayList<Country> l_neighboringCountries = new ArrayList<>();
        for (Country l_country : this.getplayer().getOwnedCountries()) {
            for (Country l_neighbor : l_country.getNeighbors()) {
                if (!this.getplayer().ownsCountry(l_neighbor.getName())) {
                    l_neighboringCountries.add(l_neighbor);
                }
            }
        }

        // Return one random neighboring country that is not owned by the current player
        Random l_random = new Random();
        if (l_neighboringCountries.isEmpty()) {
            return null;
        }
        int l_randomNum = l_random.nextInt(l_neighboringCountries.size());
        return l_neighboringCountries.get(l_randomNum);
    }
}
