package com.gameplay;

import com.model.Country;

import java.util.Objects;

public class Airlift extends Order {

    private final String d_sourceCountryName;
    private final String d_targetCountryName;
    private final int d_numArmy;

    public Airlift(Player p_player, String p_sourceCountryName, String p_targetCountryName, int p_numArmy) {
        super("airlift", p_sourceCountryName, p_numArmy, p_player);
        this.d_sourceCountryName = p_sourceCountryName;
        this.d_targetCountryName = p_targetCountryName;
        this.d_numArmy = p_numArmy;
    }

    @Override
    public void execute() {
        // Check if the player owns a airlift card
        if (!d_player.getD_cards().contains(Card.AIRLIFT)) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + Card.AIRLIFT + " card");
            return;
        }

        // Check if the specified source and target country are equal
        if (Objects.equals(this.d_sourceCountryName, this.d_targetCountryName)) {
            System.out.println("\nError: The source and target country cannot be the same");
            return;
        }

        // Check if the specified source country is owned by the current player
        Country l_sourceCountry = d_player.getCountryByName(d_countryName);
        if (l_sourceCountry == null) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + this.d_sourceCountryName + " country");
            return;
        }

        // Check if the specified target country is owned by the current player
        Country l_targetCountry = d_player.getCountryByName(d_targetCountryName);
        if (l_targetCountry == null) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + this.d_targetCountryName+ " country");
            return;
        }

        // Check if the specified source country has sufficient armies
        if (this.d_numArmy > l_sourceCountry.getArmies()) {
            System.out.println("\nError: " + l_sourceCountry.getName() + " does not have sufficient armies");
            return;
        }

        // Perform airlift
        l_sourceCountry.setArmies(l_sourceCountry.getArmies() - this.d_numArmy);
        l_targetCountry.setArmies(l_targetCountry.getArmies() + this.d_numArmy);
        System.out.println("\nAirlift successfully executed");
        System.out.println("Now " + l_sourceCountry.getName() + " has " + l_sourceCountry.getArmies() + " armies");
        System.out.println("Now " + l_targetCountry.getName() + " has " + l_targetCountry.getArmies() + " armies");

        // Remove blockade card from the current player
        d_player.getD_cards().remove(Card.AIRLIFT);
    }
}
