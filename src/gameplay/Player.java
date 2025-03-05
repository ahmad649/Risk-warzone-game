package gameplay;

import model.Country;

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
    public List<Country> ownedCountries;

    private String d_name;

    /**
     * Player generic constructor
     * @param p_name
     */
    //Constructor
    public Player(String p_name) {
        d_name = p_name;
        ownedCountries = new ArrayList<>();
        d_playerOrders = new LinkedList<>();
        d_numReinforcement = 0;
    }

    /**
     * getName() method
     * @return String assigned for the players name
     */
    public String getName() {
        return d_name;
    }

    /**
     * setReinforcements method
     * @param p_reinforcements Integer for the number of reinforcements that need to be set
     */
    public void setReinforcements(int p_reinforcements) {
        d_numReinforcement = p_reinforcements;
    }

    /**
     * getReinforcements method
     * @return Integer assigned for the total number of reinforcements that the player has
     */
    public int getReinforcements() {
        return d_numReinforcement;
    }

    /**
     * ownsCountry method
     * @param countryName String containing the country's name being analyzed
     * @return Boolean type validating if the country is owned by the player
     */
    public boolean ownsCountry(String countryName) {
        for (Country c : ownedCountries) {
            if (c.getName().equals(countryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes input from user in this format "deploy countryID num" and adds a command to playerOrders
     * Decreases the appropriate number of reinforcements from the numReinforcement
     */
    public void issue_order() {
        // TODO: Use InputOutput class to take the input and continue further
        // TODO: Validcation of countries here cause the user may try to deploy to countries here and not go trhough later which will lead to unused armies
        System.out.println(d_name + ", enter your order (deploy <countryID> <num>):");
        Command command = InputOutput.get_user_command();
        if (command == null) { System.out.println("Invalid order. Please try again.");
            return;
        }
        if (command.commandType.equals("deploy")) {
            int num = Integer.parseInt(command.argArr.get(1));
            String countryName = command.argArr.get(0);
            if (num <= d_numReinforcement) {
                Order newOrder = new Order("deploy", countryName , num, this);
                d_playerOrders.add(newOrder);
                d_numReinforcement -= num;
                System.out.println("Order added: Deploy " + num + " armies to country " + countryName);
            } else {
                System.out.println("Not enough reinforcements available.");
            }
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
        // TODO: Implement this method
        if (!d_playerOrders.isEmpty()) {
            return d_playerOrders.poll();
        }
        return null;
    }

    /**
     * getOwnedCountries method
     * @return List of Country nature containing the countries owned by the player
     */
    public List<Country> getOwnedCountries() {
        return ownedCountries;
    }

    /**
     * removeReinforcement method
     * @param dNumArmy Integer representing the number of reinforcements being subtracted from the player
     */
    public void removeReinforcement(int dNumArmy) {
        d_numReinforcement -= dNumArmy;
    }

    /**
     * addArmiesToCountry method
     * @param dCountryName String containing the target country's name
     * @param dNumArmy Integer containing the amount of reinforcements that will be sent to the country
     */
    public void addArmiesToCountry(String dCountryName, int dNumArmy) {
        for (Country c : ownedCountries) {
            if (c.getName().equals(dCountryName)) {
                c.addReinforcements(dNumArmy);
                return;
            }
        }
    }
}
