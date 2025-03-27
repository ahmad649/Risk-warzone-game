package com.gameplay;

import com.model.Country;

import java.util.Objects;

/**
 * Airlift class is used to advance some armies from one of the
 * current player’s territories to any another territory.
 */
public class Airlift extends Order {

    private final Player d_player;
    private final String d_sourceCountryName, d_targetCountryName;
    private Country d_sourceCountry, d_targetCountry;
    private final int d_numArmy;
    private String d_LogINFO;

    /**
     * Instantiates a new Airlift object
     *
     * @param p_player            the current player
     * @param p_sourceCountryName the source country name
     * @param p_targetCountryName the target country name
     * @param p_numArmy           the number of armies
     */
    public Airlift(Player p_player, String p_sourceCountryName, String p_targetCountryName, int p_numArmy) {
        this.d_player = p_player;
        this.d_sourceCountryName = p_sourceCountryName;
        this.d_targetCountryName = p_targetCountryName;
        this.d_numArmy = p_numArmy;
        d_LogINFO = String.format(
                "-----------------------------------------------------------------------\n" +
                        "ISSUED: Airlift: Player: %s, Source: %s, Destination: %s, Armies: %d\n" +
                        "-----------------------------------------------------------------------\n",
                d_player.getName(), d_sourceCountryName, d_targetCountryName, d_numArmy
        );
    }
    @Override
    public String getLogInfo() {
        return d_LogINFO;
    }

    @Override
    public boolean isValid() {
        // Check if the player owns a airlift card
        if (!this.d_player.getCards().contains(Card.AIRLIFT)) {
            System.out.println("\nError: Player " + this.d_player.getName() + " does not own " + Card.AIRLIFT + " card");
            return false;
        }

        // Check if the specified source and target country are equal
        if (Objects.equals(this.d_sourceCountryName, this.d_targetCountryName)) {
            System.out.println("\nError: The source and target country cannot be the same");
            return false;
        }

        // Check if the specified source country is owned by the current player
        this.d_sourceCountry = this.d_player.getCountryByName(this.d_sourceCountryName);
        if (this.d_sourceCountry == null) {
            System.out.println("\nError: Player " + this.d_player.getName() + " does not own source country: " + this.d_sourceCountryName);
            return false;
        }

        // Check if the specified target country is owned by the current player
        this.d_targetCountry = this.d_player.getCountryByName(this.d_targetCountryName);
        if (this.d_targetCountry == null) {
            System.out.println("\nError: Player " + this.d_player.getName() + " does not own target country: " + this.d_targetCountryName);
            return false;
        }

        // Check if the specified source country has sufficient armies
        if (this.d_numArmy > this.d_sourceCountry.getArmies()) {
            System.out.println("\nError: " + this.d_sourceCountry.getName() + " does not have sufficient armies");
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        if (this.isValid()) {
            d_LogINFO = "\n-----------------------------------------------------------------------------";
            // Perform airlift
            this.d_sourceCountry.setArmies(this.d_sourceCountry.getArmies() - this.d_numArmy);
            this.d_targetCountry.setArmies(this.d_targetCountry.getArmies() + this.d_numArmy);
            d_LogINFO += "\nAirlift successfully executed";
            d_LogINFO += "Now " + this.d_sourceCountry.getName() + " has " + this.d_sourceCountry.getArmies() + " armies";
            d_LogINFO += "Now " + this.d_targetCountry.getName() + " has " + this.d_targetCountry.getArmies() + " armies";
            d_LogINFO += "\n-----------------------------------------------------------------------------";
            System.out.println(d_LogINFO);

            // Remove airlift card from the current player
            this.d_player.getCards().remove(Card.AIRLIFT);
        }
    }
}
