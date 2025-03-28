package com.gameplay;

import com.model.Country;

/**
 * Bomb class is used to destroy half of the armies located on an opponent’s territory that is adjacent to
 * one of the current player’s territories.
 */
public class Bomb extends Order {

    private final Player d_player;
    private Country d_countryToBomb;
    private final String d_countryName;
    public String d_LogINFO;
    /**
     * Instantiates a new Bomb object.
     *
     * @param p_player      the current player
     * @param p_countryName the target country name
     */
    public Bomb(Player p_player, String p_countryName) {
        this.d_player = p_player;
        this.d_countryName = p_countryName;
        this.d_countryToBomb = this.d_player.getCountryByName(p_countryName);
        d_LogINFO = String.format(
                "-----------------------------------------------------------------------\n" +
                        "ISSUED: Bomb: Player: %s, Target: %s\n" +
                        "-----------------------------------------------------------------------\n",
                d_player.getName(), d_countryName
        );
    }
    @Override
    public String getLogInfo() {
        return d_LogINFO;
    }

    @Override
    public boolean isValid() {
        // Check if the player owns a bomb card
        if (!d_player.getCards().contains(Card.BOMB)) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + Card.BOMB + " card");
            return false;
        }

        // Check if the specified country is owned by the current player
        if (this.d_countryToBomb != null) {
            System.out.println("\nError: Player " + d_player.getName() + " cannot bomb your own country");
            return false;
        }

        // Get the specified country if it is adjacent to the current player's countries
        for (Country l_country : d_player.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (l_adjCountry.getName().equals(this.d_countryName)) {
                    this.d_countryToBomb = l_adjCountry;
                }
            }
        }

        // Check if specified country is found as adjacent countries
        if (this.d_countryToBomb == null) {
            System.out.println("\nError: " + this.d_countryName + " is not adjacent with " + d_player.getName() + "'s countries");
            return false;
        }

        // Check if the country has armies
        if (this.d_countryToBomb.getArmies() <= 0) {
            System.out.println("\nError: Country " + this.d_countryName + " has no armies");
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        System.out.println(d_LogINFO);
        if (this.isValid()) {
            d_LogINFO = "\n-----------------------------------------------------------------------------";
            d_countryToBomb.setArmies(Math.round((float) d_countryToBomb.getArmies() / 2));
            d_LogINFO += "\nSuccess: " + this.d_countryName + " has been bombed by " + d_player.getName();
            d_LogINFO += "\n" + d_countryToBomb.getName() + " now has " + d_countryToBomb.getArmies() + " armies";
            // Remove bomb card from the current player
            this.d_player.removeCard(Card.BOMB);
            d_LogINFO += "\n-----------------------------------------------------------------------------";
            System.out.println(d_LogINFO);
        }
    }
}