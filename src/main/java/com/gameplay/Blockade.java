package com.gameplay;

import com.model.Country;

/**
 * Blockade class is used to triple the number of armies on one of the current player’s territories and make
 * it a neutral territory.
 */
public class Blockade extends Order {
    private final GameEngine d_gameEngine;
    private final Player d_player;
    private final String d_countryName;
    private final Country d_countryToBlockade;
    private int d_numOfArmies;
    private String d_LogINFO;
    /**
     * Instantiates a new Blockade object.
     *
     * @param p_gameEngine  the game engine that controls the game flow
     * @param p_player      the current player
     * @param p_countryName the target country name
     */
    public Blockade(GameEngine p_gameEngine, Player p_player, String p_countryName) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
        this.d_countryName = p_countryName;
        this.d_countryToBlockade = this.d_player.getCountryByName(p_countryName);
        d_LogINFO = String.format(
                "-----------------------------------------------------------------------\n" +
                        "ISSUED: Blockade: Player: %s, Source: %s\n" +
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
        // Check if the player owns a blockade card
        if (!d_player.getCards().contains(Card.BLOCKADE)) {
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
            d_LogINFO = "\n-----------------------------------------------------------------------------";
            this.d_countryToBlockade.setArmies(d_numOfArmies * 3);
            d_LogINFO += "\nSuccess: Now country " + this.d_countryName + " has " + this.d_numOfArmies + " armies";

            // Remove the specified country from the current player's countries
            d_player.removeCountry(this.d_countryName);

            Player l_neutralPlayer = this.d_gameEngine.getneutralPlayer();

            // Convert the specified country into neutral territory
            this.d_countryToBlockade.setOwner(l_neutralPlayer);
            l_neutralPlayer.addCountryToOwnedCountries(d_countryToBlockade);

            // Remove blockade card from the current player
            d_player.getCards().remove(Card.BLOCKADE);
            d_LogINFO += "\n-----------------------------------------------------------------------------";
            System.out.println(d_LogINFO);
        }
    }
}
