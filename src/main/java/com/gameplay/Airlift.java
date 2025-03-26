package com.gameplay;

import com.model.Country;

import java.util.Objects;

public class Airlift extends Order {

    private final String d_sourceCountryName, d_targetCountryName;
    private Country l_sourceCountry, l_targetCountry;
    private final int d_numArmy;

    public Airlift(Player p_player, String p_sourceCountryName, String p_targetCountryName, int p_numArmy) {
        this.d_sourceCountryName = p_sourceCountryName;
        this.d_targetCountryName = p_targetCountryName;
        this.d_numArmy = p_numArmy;
    }

    public boolean isValid() {
        // Check if the player owns a airlift card
        if (!d_player.getD_cards().contains(Card.AIRLIFT)) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + Card.AIRLIFT + " card");
            return false;
        }

        // Check if the specified source and target country are equal
        if (Objects.equals(this.d_sourceCountryName, this.d_targetCountryName)) {
            System.out.println("\nError: The source and target country cannot be the same");
            return false;
        }

        // Check if the specified source country is owned by the current player
        this.l_sourceCountry = d_player.getCountryByName(d_countryName);
        if (this.l_sourceCountry == null) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own source country: " + this.d_sourceCountryName);
            return false;
        }

        // Check if the specified target country is owned by the current player
        this.l_targetCountry = d_player.getCountryByName(d_targetCountryName);
        if (this.l_targetCountry == null) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own target country: " + this.d_targetCountryName);
            return false;
        }

        // Check if the specified source country has sufficient armies
        if (this.d_numArmy > this.l_sourceCountry.getArmies()) {
            System.out.println("\nError: " + this.l_sourceCountry.getName() + " does not have sufficient armies");
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        if (this.isValid()) {
            // Perform airlift
            this.l_sourceCountry.setArmies(this.l_sourceCountry.getArmies() - this.d_numArmy);
            this.l_targetCountry.setArmies(this.l_targetCountry.getArmies() + this.d_numArmy);
            System.out.println("\nAirlift successfully executed");
            System.out.println("Now " + this.l_sourceCountry.getName() + " has " + this.l_sourceCountry.getArmies() + " armies");
            System.out.println("Now " + this.l_targetCountry.getName() + " has " + this.l_targetCountry.getArmies() + " armies");

            // Remove airlift card from the current player
            d_player.getD_cards().remove(Card.AIRLIFT);
        }
    }
}
