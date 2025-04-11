package com.orders;

import com.gameplay.GameEngine;
import com.gameplay.Player;
import com.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Advance class is used to move some armies from one of the current player's territories (source)
 * to an adjacent territory (target). if the target territory belongs to the current player,
 * the armies are moved to the target territory. If the target territory belongs to another player,
 * an attack happens between the two territories.
 */
public class Advance extends Order {
    private final GameEngine d_gameEngine;
    private final Player d_player;
    private final Country d_countryFrom;
    private Country d_countryTo;
    private final String d_countryNameFrom, d_countryNameTo;
    private final int d_numArmies;
    private String d_LogINFO;

    /**
     * Instantiates a new Advance object.
     *
     * @param p_gameEngine  the game engine that controls the game flow
     * @param p_player      the current player
     * @param p_countryFrom the source country
     * @param p_countryTo   the target country
     * @param p_numArmies   the number of armies
     */
    public Advance(GameEngine p_gameEngine, Player p_player, String p_countryFrom, String p_countryTo, int p_numArmies) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
        this.d_countryFrom = p_player.getCountryByName(p_countryFrom);
        this.d_countryNameFrom = p_countryFrom;
        this.d_countryNameTo = p_countryTo;
        this.d_numArmies = p_numArmies;
        d_LogINFO = String.format(
                "-----------------------------------------------------------------------\n" +
                        "ISSUED: Advance: Player: %s, Source: %s, Destination: %s, Armies: %d\n" +
                        "-----------------------------------------------------------------------\n",
                d_player.getName(), d_countryNameFrom, d_countryNameTo, d_numArmies
        );
        for (Player l_player : p_gameEngine.getPlayersList()) {
            if (l_player.getCountryByName(p_countryTo) != null) {
                this.d_countryTo = l_player.getCountryByName(this.d_countryNameTo);
            }
        }
    }
    /**
     * Gets the log info.
     */
    @Override
    public String getLogInfo() {
        return d_LogINFO;
    }

    /**
     * Checks if the advance order is valid.
     *
     * @return true if the advance order is valid, false otherwise
     */
    @Override
    public boolean isValid() {
        Country l_countryTo = this.d_countryTo;
        if (l_countryTo == null) {
            return false;
        }

        // Ensure the player owns the source country before advancing
        if (!this.d_player.ownsCountry(this.d_countryNameFrom)) {
            System.out.println("\nError: Player " + this.d_player.getName() + " does not own country " + this.d_countryNameFrom);
            return false;
        }

        // If the target country is owned by player in the diplomacy list
        ArrayList<String> diplomacyPlayerNames = new ArrayList<>();
        for (Player l_player : this.d_player.getDiplomacyPlayers()) {
            diplomacyPlayerNames.add(l_player.getName());
        }

        if (diplomacyPlayerNames.contains(l_countryTo.getOwner().getName())) {
            System.out.println("\nError: " + this.d_countryNameTo + " is owned by a player that's in the diplomacy list");
            return false;
        }

        // Source and target country are the same
        if (this.d_countryFrom.getName().equals(l_countryTo.getName())) {
            System.out.println("\nError: Source and target country cannot be the same");
            return false;
        }

        // Ensure the country has enough reinforcements
        if (this.d_numArmies > this.d_countryFrom.getArmies()) {
            System.out.println("\nError: Not enough armies are available in " + this.d_countryNameFrom);
            return false;
        }

        //Ensure both countries are adjacent
        if (!this.d_countryFrom.isNeighbor(l_countryTo.getName())){
            System.out.println("\nError: " + this.d_countryNameFrom + " and " + this.d_countryNameTo + " are not adjacent");
            return false;
        }

        return true;
    }

    /**
     * execute() method inherited from the abstract Order class where the Advance logic is placed.
     * Validations about the player issuing the order owns the source country are made, as well as the number of armies in the country to check if the operation is possible.
     */
    @Override
    public void execute(){
        System.out.println(d_LogINFO);
        if (isValid()) {
            d_LogINFO = "\n-----------------------------------------------------------------------------";
            ArrayList<String> l_countryNamesOwned = new ArrayList<>();
            for (Country l_country : this.d_player.getOwnedCountries()) {
                l_countryNamesOwned.add(l_country.getName());
            }
            if (l_countryNamesOwned.contains(this.d_countryTo.getName())) {
                // Move armies

                d_LogINFO += "\nSuccess: Moving " + this.d_numArmies + " armies to " + this.d_countryTo.getName();

                this.d_countryFrom.setArmies(this.d_countryFrom.getArmies() - this.d_numArmies);
                this.d_countryTo.setArmies(this.d_countryTo.getArmies() + this.d_numArmies);
            } else {
                // Battle

                d_LogINFO += "\nStarting battle between " + this.d_countryFrom.getName() + " and " + this.d_countryTo.getName();

                int l_attackingWinningChance = (int) Math.round(this.d_numArmies * 0.6);
                int l_defendingWinningChance = (int) Math.round(this.d_countryTo.getArmies() * 0.7);

                int l_attackingArmies = this.d_numArmies - l_defendingWinningChance;
                int l_defendingArmies = this.d_countryTo.getArmies() - l_attackingWinningChance;

                if (l_defendingArmies <= 0) {
                    // Attacker wins
                    d_LogINFO += "\nAttacker wins";

                    // Give random card to player
                    Card l_card = this.getRandomCard();

                    d_LogINFO += "\nPlayer " + this.d_player.getName() + " has received " + l_card + " card";
                    this.d_player.addCards(l_card);

                    // Set number of armies

                    this.d_countryFrom.setArmies(this.d_countryFrom.getArmies() - this.d_numArmies);
                    d_LogINFO += "\nNow " + this.d_countryFrom.getName() + " has " + this.d_countryFrom.getArmies() + " armies";

                    this.d_countryTo.setArmies(l_attackingArmies);
                    d_LogINFO += "\nNow " + this.d_countryTo.getName() + " has " + this.d_countryTo.getArmies() + " armies";

                    // Add and remove country
                    this.d_player.addCountryToOwnedCountries(this.d_countryTo);
                    this.d_countryTo.getOwner().removeCountry(this.d_countryNameTo);

                    // Change owner
                    this.d_countryTo.setOwner(this.d_player);
                } else {
                    // Defender wins

                    d_LogINFO += "\nDefender wins";
                    // Set number of armies
                    this.d_countryFrom.setArmies(l_attackingArmies + (this.d_countryFrom.getArmies() - this.d_numArmies));
                    d_LogINFO += "\nNow " + this.d_countryFrom.getName() + " has " + this.d_countryFrom.getArmies() + " armies";

                    this.d_countryTo.setArmies(l_defendingArmies);
                    d_LogINFO += "\nNow " + this.d_countryTo.getName() + " has " + this.d_countryTo.getArmies() + " armies";

                }
            }
            d_LogINFO += "\n-----------------------------------------------------------------------------";
            System.out.println(d_LogINFO);
        }
    }

    /**
     * After conquering the target country (owned by other players),
     * a random card will be given to the current player.
     *
     * @return a random {@link Card} value
     */
    public Card getRandomCard() {
        ArrayList<Card> cards = new ArrayList<>(List.of(Card.BOMB, Card.BLOCKADE, Card.AIRLIFT, Card.DIPLOMACY));

        Random random = new Random();
        return cards.get(random.nextInt(cards.size()));
    }
}
