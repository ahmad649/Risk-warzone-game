package com.gameplay;

import com.model.Country;

public class Blockade extends Order {
    private final Player d_player;
    private final String d_countryName;
    private final Country d_countryToBlockade;
    private int d_numOfArmies;

    public Blockade(Player p_player, String p_countryName) {
        this.d_player = p_player;
        this.d_countryName = p_countryName;
        this.d_countryToBlockade = this.d_player.getCountryByName(p_countryName);
    }

    public boolean isValid() {
        // Check if the player owns a blockade card
        if (!d_player.getD_cards().contains(Card.BLOCKADE)) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + Card.BLOCKADE + " card");
            return false;
        }

        // Check if the specified country is owned by the current player
        if (this.d_countryToBlockade == null) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + this.d_countryName + " country");
            return false;
        }

        this.d_numOfArmies = this.d_countryToBlockade.getArmies();
        // Check the number of armies in the specified country
        if (this.d_numOfArmies <= 0) {
            System.out.println("\nError: Country " + this.d_countryName + " has 0 armies");
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        if (this.isValid()) {
            // Triple the number of armies on the specified country
            this.d_countryToBlockade.setArmies(d_numOfArmies * 3);
            System.out.println("\nSuccess: Now country " + this.d_countryName + " has " + this.d_numOfArmies + " armies");

            // Remove the specified country from the current player's countries
            d_player.removeCountry(this.d_countryName);

            // TODO:Convert the specified country into neutral territory

            // Remove blockade card from the current player
            d_player.getD_cards().remove(Card.BLOCKADE);
        }
    }
}
