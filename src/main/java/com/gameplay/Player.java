package com.gameplay;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.model.Country;

import java.util.*;

/**
 * Player class containing the Player related methods and information
 * Queue of Order nature designated for the orders issued by a player
 * Integer d_numReinforcement designated for the number of reinforcements a player possesses
 * List of Country nature ownedCountries designated for countries a player possesses
 */
public class Player{

    private Queue<Order> d_playerOrders;
    private int d_numReinforcement;
    //Mark as a reference to stop loop serialization
    @JsonManagedReference
    public List<Country> d_ownedCountries;

    private String d_name;
    private List<Card> d_cards;
    private List<Player> d_diplomacyPlayers;

    /**
     * Player no-params constructor made for serialization
     */
    public Player(){
        d_name = "";
        d_ownedCountries = new ArrayList<>();
        d_playerOrders = new LinkedList<>();
        d_numReinforcement = 0;
        d_cards = new ArrayList<>();
        d_diplomacyPlayers = new ArrayList<>();
    }

    /**
     * Player argument constructor
     *
     * @param p_name the players name
     */
    public Player(String p_name) {
        this();
        d_name = p_name;
    }

    /**
     * getName() method
     *
     * @return String assigned for the players name
     */
    public String getName() {
        return d_name;
    }

    /**
     * getReinforcements method
     *
     * @return Integer assigned for the total number of reinforcements that the player has
     */
    public int getReinforcements() {
        return d_numReinforcement;
    }

    /**
     * getOrders method
     * @return Queue containing the orders issued by the player
     */
    public Queue<Order> getOrders(){ return d_playerOrders; }

    /**
     * Gets a list of cards own by the current player
     *
     * @return a list of cards
     */
    public List<Card> getCards() {
        return d_cards;
    }

    /**
     * Gets a list of diplomacy players of the current player
     *
     * @return a list of diplomacy players
     */
    public List<Player> getDiplomacyPlayers() {
        return d_diplomacyPlayers;
    }

    /**
     * getOwnedCountries method
     *
     * @return List of Country nature containing the countries owned by the player
     */
    public List<Country> getOwnedCountries() {
        return d_ownedCountries;
    }

    /**
     * setReinforcements method
     *
     * @param p_reinforcements Integer for the number of reinforcements that need to be set
     */
    public void setReinforcements(int p_reinforcements) {
        d_numReinforcement = p_reinforcements;
    }

    /**
     * ownsCountry method
     *
     * @param p_countryName String containing the country's name being analyzed
     * @return Boolean type validating if the country is owned by the player
     */
    public boolean ownsCountry(String p_countryName) {
        for (Country l_country : d_ownedCountries) {
            if (l_country.getName().equals(p_countryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * getCountryByName method
     *
     * @param p_countryName String containing the country's name being searched
     * @return Country object owned by the player
     */
    public Country getCountryByName(String p_countryName){
        for (Country l_country : d_ownedCountries){
            if (l_country.getName().equals(p_countryName)){
                return l_country;
            }
        }
        return null;
    }

    /**
     * Add cards to the card list
     *
     * @param p_card a card to add
     */
    public void addCards(Card p_card) {
        this.d_cards.add(p_card);
    }

    /**
     * Remove card from the list
     *
     * @param p_card the card
     */
    public void removeCard(Card p_card) {
        this.d_cards.remove(p_card);
    }

    /**
     * Add player to the diplomacy list
     *
     * @param p_diplomacyPlayer the diplomacy player
     */
    public void addDiplomacyPlayers(Player p_diplomacyPlayer) {
        if (d_diplomacyPlayers.isEmpty()) {
            d_diplomacyPlayers.add(p_diplomacyPlayer);
            return;
        }

        for (Player l_player : d_diplomacyPlayers) {
            if (!l_player.getName().equals(p_diplomacyPlayer.getName())) {
                this.d_diplomacyPlayers.add(p_diplomacyPlayer);
            }
        }
    }

    /**
     * Remove all diplomacy players.
     */
    public void clearDiplomacyPlayers() {
        d_diplomacyPlayers.clear();
    }

    /**
     * Takes input from user in this format "deploy countryID num" and adds a command to playerOrders
     * Decreases the appropriate number of reinforcements from the numReinforcement
     *
     * @param p_gameEngine the game engine
     * @param l_parsing    the parsing object that returns parsed arguments
     */
    public void issue_order(GameEngine p_gameEngine,Parsing l_parsing) {
        ArrayList<String> l_arguments = l_parsing.getArgArr();

        switch (l_parsing.d_commandType) {
            case "deploy" -> {
                String l_countryName = l_arguments.get(0);
                int l_num = Integer.parseInt(l_arguments.get(1));

                Deploy l_deployOrder = new Deploy(this, l_countryName, l_num);
                p_gameEngine.d_logbuffer.addEntry(l_deployOrder);

                this.d_playerOrders.add(l_deployOrder);
            }
            case "advance" -> {
                String l_countryFrom = l_arguments.get(0);
                String l_countryTo = l_arguments.get(1);
                int l_numArmies = Integer.parseInt(l_arguments.get(2));

                Advance l_advanceOrder = new Advance(p_gameEngine, this, l_countryFrom, l_countryTo, l_numArmies);
                p_gameEngine.d_logbuffer.addEntry(l_advanceOrder);

                this.d_playerOrders.add(l_advanceOrder);
            }
            case "bomb" -> {
                String l_countryName = l_arguments.getFirst();

                Bomb l_bombOrder = new Bomb(this, l_countryName);
                p_gameEngine.d_logbuffer.addEntry(l_bombOrder);

                this.d_playerOrders.add(l_bombOrder);
            }
            case "blockade" -> {
                String l_countryName = l_arguments.getFirst();

                Blockade l_blockadeOrder = new Blockade(p_gameEngine, this, l_countryName);
                p_gameEngine.d_logbuffer.addEntry(l_blockadeOrder);

                this.d_playerOrders.add(l_blockadeOrder);
            }
            case "airlift" -> {
                String l_sourceCountryName = l_arguments.get(0);
                String l_targetCountryName = l_arguments.get(1);
                int l_numArmy = Integer.parseInt(l_arguments.get(2));

                Airlift l_airliftOrder = new Airlift(this, l_sourceCountryName, l_targetCountryName, l_numArmy);
                p_gameEngine.d_logbuffer.addEntry(l_airliftOrder);

                this.d_playerOrders.add(l_airliftOrder);
            }
            case "negotiate" -> {
                String l_playerName = l_arguments.getFirst();

                Diplomacy l_diplomacyOrder = new Diplomacy(p_gameEngine, this, l_playerName);
                p_gameEngine.d_logbuffer.addEntry(l_diplomacyOrder);

                this.d_playerOrders.add(l_diplomacyOrder);
            }
        }
    }

    /**
     * Removes the first command from the playerOrders and returns it
     *
     * @return The order at the front of the queue
     */
    public Order next_order() {
        if (!d_playerOrders.isEmpty()) {
            return d_playerOrders.poll();
        }
        return null;
    }

    /**
     * removeReinforcement method
     *
     * @param p_numArmy Integer representing the number of reinforcements being subtracted from the player
     */
    public void removeReinforcement(int p_numArmy) {
        d_numReinforcement -= p_numArmy;
    }

    /**
     * addArmiesToCountry method
     *
     * @param p_countryName String containing the target country's name
     * @param p_num         Integer containing the amount of reinforcements that will be sent to the country
     */
    public void addArmiesToCountry(String p_countryName, int p_num) {
        for (Country l_country : d_ownedCountries) {
            if (l_country.getName().equals(p_countryName)) {
                l_country.addReinforcements(p_num);
                return;
            }
        }
    }

    /**
     * Remove country from the list of owned countries.
     *
     * @param p_countryName the country name
     */
    public void removeCountry(String p_countryName) {
        d_ownedCountries.removeIf(l_country -> l_country.getName().equals(p_countryName));
    }

    /**
     * Add country to the list of owned countries
     *
     * @param p_country the country
     */
    public void addCountryToOwnedCountries(Country p_country) {
        d_ownedCountries.add(p_country);
    }

}
