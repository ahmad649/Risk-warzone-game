package com.gameplay;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.model.Country;

@JsonTypeName("blockade")
/**
 * Blockade class is used to triple the number of armies on one of the current player’s territories and make
 * it a neutral territory.
 */
public class Blockade extends Order {
    //Marked as a backreference to stop loop serialization
    @JsonBackReference
    private GameEngine d_gameEngine;
    private Player d_player;
    private String d_countryName;
    private Country d_countryToBlockade;
    private int d_numOfArmies;
    private String d_LogINFO;

    /**
     * Blockade no-args constructor for serialization
     */
    public Blockade(){}

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

    /**
     * Gets the log info.
     */
    @Override
    public String getLogInfo() {
        return d_LogINFO;
    }

    /**
     * Checks if the blockade order is valid.
     *
     * @return true if the blockade order is valid, false otherwise
     */
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
            System.out.println("\nError: Country " + this.d_countryName + " has no armies");
            return false;
        }

        return true;
    }

    /**
     * Executes the blockade order.
     */
    @Override
    public void execute() {
        System.out.println(d_LogINFO);
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
            this.d_player.removeCard(Card.BLOCKADE);
            d_LogINFO += "\n-----------------------------------------------------------------------------";
            System.out.println(d_LogINFO);
        }
    }
}
