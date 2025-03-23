package com.gameplay;

import com.model.Country;

import java.util.*;

/**
 * Player class containing the Player related methods and information
 * Queue of Order nature designated for the orders issued by a player
 * Integer d_numReinforcement designated for the number of reinforcements a player possesses
 * List of Country nature ownedCountries designated for countries a player possesses
 */
public class Player {

    private Queue<Order> d_playerOrders;
    private int d_numReinforcement;
    /**
     * A list of countries owned by the player.
     */
    public List<Country> d_ownedCountries;

    private String d_name;
    private List<Card> d_cards;
    private List<Player> d_diplomacyPlayers;

    /**
     * Player generic constructor
     *
     * @param p_name the p name
     */
    public Player(String p_name) {
        d_name = p_name;
        d_ownedCountries = new ArrayList<>();
        d_playerOrders = new LinkedList<>();
        d_numReinforcement = 0;
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
     * setReinforcements method
     *
     * @param p_reinforcements Integer for the number of reinforcements that need to be set
     */
    public void setReinforcements(int p_reinforcements) {
        d_numReinforcement = p_reinforcements;
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

    public List<Card> getD_cards() {
        return d_cards;
    }

    public List<Player> getD_diplomacyPlayers() {
        return d_diplomacyPlayers;
    }

    public void addDiplomacyPlayers(Player p_diplomacyPlayers) {
        for (Player l_player : d_diplomacyPlayers) {
            if (!l_player.getName().equals(p_diplomacyPlayers.getName())) {
                this.d_diplomacyPlayers.add(p_diplomacyPlayers);
            }
        }
    }

    /**
     * Takes input from user in this format "deploy countryID num" and adds a command to playerOrders
     * Decreases the appropriate number of reinforcements from the numReinforcement
     */
    public void issue_order(Parsing l_parsing) {
        int l_num = Integer.parseInt(l_parsing.d_argArr.get(1));
        String l_countryName = l_parsing.d_argArr.get(0).replace('_', ' ');
        if (l_num <= d_numReinforcement && ownsCountry(l_countryName)) {
            Order l_newOrder = new Order("deploy", l_countryName, l_num, this);
            d_playerOrders.add(l_newOrder);
            d_numReinforcement -= l_num;
            System.out.println("Order added: Deploy " + l_num + " armies to country " + l_countryName);
        } else {
            System.out.println("Not enough reinforcements available or you don't own this country.");
    public void issue_order() {
        System.out.println(d_name + ", enter your order (deploy <countryID> <num>):");
        Command l_command = InputOutput.get_user_command();
        if (l_command == null) { System.out.println("Invalid order. Please try again.");
            return;
        }
        ArrayList<String> l_arguments = l_command.getArgArr();

        if (l_command.d_commandType.equals("deploy")) {
            int l_num = Integer.parseInt(l_arguments.get(1));
            String l_countryName = l_arguments.get(0).replace('_', ' ');;
            if (l_num <= d_numReinforcement && ownsCountry(l_countryName)) {
                Order l_newOrder = new Deploy("deploy", l_countryName , l_num, this);
                d_playerOrders.add(l_newOrder);
                d_numReinforcement -= l_num;
                System.out.println("Order added: Deploy " + l_num + " armies to country " + l_countryName);
            } else {
                System.out.println("Not enough reinforcements available or you don't own this country.");
            }
        } else if (l_command.d_commandType.equals("advance")) {
            String l_countryFrom = l_arguments.get(0);
            String l_countryTo = l_arguments.get(1);
            int l_numArmies = Integer.parseInt(l_arguments.get(2));

            Order l_advanceOrder = new Advance("advance", l_countryFrom, l_countryTo, l_numArmies, this);
            this.d_playerOrders.add(l_advanceOrder);

        } else if (l_command.d_commandType.equals("bomb")) {
            String l_countryName = l_arguments.getFirst();

            Order l_bombOrder = new Bomb(this, l_countryName);
            this.d_playerOrders.add(l_bombOrder);

        } else if (l_command.d_commandType.equals("blockade")) {
            String l_countryName = l_arguments.getFirst();

            Order l_blockadeOrder = new Blockade(this, l_countryName);
            this.d_playerOrders.add(l_blockadeOrder);

        } else if (l_command.d_commandType.equals("airlift")) {
            String l_sourceCountryName = l_arguments.get(0);
            String l_targetCountryName = l_arguments.get(1);
            int l_numArmy = Integer.parseInt(l_arguments.get(2));

            Order l_airliftOrder = new Airlift(this, l_sourceCountryName, l_targetCountryName, l_numArmy);
            this.d_playerOrders.add(l_airliftOrder);

        } else if (l_command.d_commandType.equals("negotiate")) {
            String l_playerName = l_arguments.getFirst();

            Order l_diplomacyOrder = new Diplomacy(this, l_playerName);
            this.d_playerOrders.add(l_diplomacyOrder);

        } else {
            System.out.println("Invalid order. Please try again.");
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
     * getOwnedCountries method
     *
     * @return List of Country nature containing the countries owned by the player
     */
    public List<Country> getOwnedCountries() {
        return d_ownedCountries;
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

    public void removeCountry(String p_countryName) {
        for (Country l_country : d_ownedCountries) {
            if (l_country.getName().equals(p_countryName)) {
                d_ownedCountries.remove(l_country);
            }
        }
    }
}
