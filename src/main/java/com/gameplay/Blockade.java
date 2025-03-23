package com.gameplay;

import com.model.Country;

public class Blockade extends Order {

    public Blockade(Player p_player, String p_countryName) {
        super("blockade", p_countryName, 0, p_player);
    }

    @Override
    public void execute() {
        // Check if the player owns a blockade card
        if (!d_player.getD_cards().contains(Card.BLOCKADE)) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + Card.BLOCKADE + " card");
            return;
        }

        // Check if the specified country is owned by the current player
        Country l_countryToBlockade = d_player.getCountryByName(d_countryName);
        if (l_countryToBlockade == null) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + d_countryName + " country");
            return;
        }

        int l_numOfArmies = l_countryToBlockade.getArmies();
        // Check the number of armies in the specified country
        if (l_numOfArmies <= 0) {
            System.out.println("\nError: Country " + d_countryName + " has 0 armies");
            return;
        }

        // Triple the number of armies on the specified country
        l_countryToBlockade.setArmies(l_numOfArmies * 3);
        System.out.println("\nSuccess: Now country " + d_countryName + " has " + l_numOfArmies + " armies");

        // Remove the specified country from the current player's countries
        d_player.removeCountry(d_countryName);

        // TODO:Convert the specified country into neutral territory

        // Remove blockade card from the current player
        d_player.getD_cards().remove(Card.BLOCKADE);
    }
}
