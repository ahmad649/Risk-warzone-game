package com.gameplay;

import com.model.Country;

public class Bomb extends Order {

    public Bomb(Player p_player, String p_countryName) {
        super("bomb", p_countryName, 0, p_player);
    }

    @Override
    public void execute() {
        // Check if the player owns a bomb card
        if (!d_player.getD_cards().contains(Card.BOMB)) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + Card.BOMB + " card");
            return;
        }

        // Check if the specified country is owned by the current player
        Country l_currentPlayerCountry = d_player.getCountryByName(d_countryName);
        if (l_currentPlayerCountry != null) {
            System.out.println("\nError: Player " + d_player.getName() + " cannot bomb your own country");
            return;
        }

        // Get the specified country if it is adjacent to the current player's countries
        Country l_countryToBomb = null;
        for (Country l_country : d_player.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (l_adjCountry.getName().equals(this.d_countryName)) {
                    l_countryToBomb = l_adjCountry;
                }
            }
        }

        // Check if specified country is found as adjacent countries
        if (l_countryToBomb == null) {
            System.out.println("\nError: " + d_countryName + " is not adjacent with " + d_player.getName() + "'s countries");
            return;
        }

        // Check if the country has armies
        if (l_countryToBomb.getArmies() <= 0) {
            System.out.println("\nError: Country " + d_countryName + " has no armies");
            return;
        }

        l_countryToBomb.setArmies(Math.round((float) l_countryToBomb.getArmies() / 2));
        System.out.println("\nSuccess: " + d_countryName + " has been bombed by " + d_player.getName());

        // Remove bomb card from the current player
        d_player.getD_cards().remove(Card.BOMB);
    }
}