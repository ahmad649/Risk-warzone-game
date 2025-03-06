package gameplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import maps.MapReader;
import model.Continent;
import model.Country;

/**
 * GameEngine class that works as the controller for the game flow
 * Manages the game stages and has the list for Player and Country objects:
 * d_playerList: list of player objects
 * d_countryList: list of country objects
 */
public class GameEngine {
    List<Player> d_playersList = new ArrayList<>();

    List<Country> d_countryList = new ArrayList<>();

    /**
     * startup() method in charge of initiating the game taking no parameters
     * Manages player creation and controls the first stage of the game managing the users input through the InputOutput class methods
     * If the command given by the user is valid, Command class methods are called to determine either if the command adds or removes players
     * On the other hand, if the command corresponds to assigning countries, the process is called as well as the loop method running the main stage of the game
     */
    // Game starter
    public void startup(){
        System.out.println("Game setup started. Add players using 'gameplayer -add <playername>'.");
        while (true) {
            Command l_command = null;
            while (l_command == null){
                l_command = InputOutput.get_user_command();
            }
            if (l_command.d_commandType.equals("gameplayer")){
                if (l_command.d_argsLabeled.containsKey("-add")) {
                    String l_playername = l_command.d_argsLabeled.get("-add").getFirst();
                    d_playersList.add(new Player(l_playername));
                    System.out.println("Player added: " + l_playername);
                } else if (l_command.d_argsLabeled.containsKey("-remove")) {
                    String l_playerName = l_command.d_argsLabeled.get("-remove").getFirst();
                    d_playersList.removeIf(p -> p.getName().equals(l_playerName));
                    System.out.println("Player removed: " + l_playerName);
                }
            } else if (l_command.d_commandType.equals("loadmap")) {
                MapReader l_mapreader = new MapReader();
                l_mapreader.loadMap(l_command.d_argArr.getFirst());
                d_countryList = l_mapreader.getCountriesMap().values().stream().toList();
                if (d_countryList.isEmpty()){
                    System.out.println("Empty map loaded. Please try again.");
                    continue;
                }
                l_mapreader.showMap();
            } else if (l_command.d_commandType.equals("assigncountries")) {
                assignCountries();
                looper();
                break;
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }
    }

    /**
     * assignCountries() method with no parameters in charge of taking both players and countries list and assign the countries randomly
     */
    // Assigning countries created randomly to the players
    private void assignCountries() {
        if (d_playersList.isEmpty() || d_countryList.isEmpty()) {
            System.out.println("Cannot assign countries. Ensure players and countries are available.");
            return;
        }

        int l_index = 0;
        for (Country l_country : d_countryList) {
            Player l_player = d_playersList.get(l_index);
            l_player.d_ownedCountries.add(l_country);
            l_country.setOwner(l_player);
            l_index = (l_index + 1) % d_playersList.size();
        }

        System.out.println("All countries have been assigned to players.");
    }

    /**
     * looper() method with no parameters in charge of managing game stages after initialization
     * Once both players and countries are created and assigned, this method is called to implement the rest of the game
     * Manages basic verification of players existence, takes the list of player objects and iterates through it assigning reinforcements to each one of them
     * After this process is done, Issuing Orders phase starts, filling an Oder type array from the Player class with the orders issued by each player
     * Finally, Order Execution phase starts, executing each order issued by the players
     */
    //Looping method controlling the rest of the game stages after initialization
    public void looper(){

        // Verification for the existence of players
        if (d_playersList.isEmpty()) {
            System.out.println("No players available. Exiting game loop.");
            return;
        }

        // Assigning reinforcements to each player
        for(Player l_player : d_playersList){
            int l_reinforcements = 5;

            HashSet<Country> l_processedCountries = new HashSet<>();
            for (Country l_country : l_player.d_ownedCountries) {
                if (l_processedCountries.contains(l_country)) {continue;}
                Continent l_checkingContinent = l_country.getContinent();
                boolean l_givebonus = true;
                for (Country otherCountry : l_checkingContinent.getCountries()) {
                    l_processedCountries.add(otherCountry);
                    if (otherCountry.getOwner()!=l_player) {
                        l_givebonus = false;
                        break;
                    }
                }
                if (l_givebonus) {
                    l_reinforcements+= l_checkingContinent.getBonus();
                }
            }

            l_player.setReinforcements(l_reinforcements);
            System.out.println(l_player.getName() + " receives " + l_reinforcements + " reinforcements.");
        }

        // Issuing Orders Phase
        boolean l_ordersRemaining = true;
        while (l_ordersRemaining) {
            l_ordersRemaining = false;
            for (Player l_player : d_playersList) {
                if (l_player.getReinforcements() > 0) {
                    l_player.issue_order();
                    l_ordersRemaining = true;
                }
            }
        }

        // Order Execution Phase
        boolean l_executingOrders = true;
        while (l_executingOrders) {
            l_executingOrders = false;
            for (Player l_player : d_playersList) {
                Order l_pendingOrder = l_player.next_order();
                if (l_pendingOrder != null) {
                    l_pendingOrder.execute();
                    l_executingOrders = true;
                }
            }
        }

    }

}
