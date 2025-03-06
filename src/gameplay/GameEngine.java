package gameplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

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
            Command command = null;
            while (command == null){
                command = InputOutput.get_user_command();
            }
            if (command.d_commandType.equals("gameplayer")){
                if (command.d_argsLabeled.containsKey("-add")) {
                    String playerName = command.d_argsLabeled.get("-add").getFirst();
                    d_playersList.add(new Player(playerName));
                    System.out.println("Player added: " + playerName);
                } else if (command.d_argsLabeled.containsKey("-remove")) {
                    String playerName = command.d_argsLabeled.get("-remove").getFirst();
                    d_playersList.removeIf(p -> p.getName().equals(playerName));
                    System.out.println("Player removed: " + playerName);
                }
            } else if (command.d_commandType.equals("loadmap")) {
                MapReader mapReader = new MapReader();
                mapReader.loadMap(command.d_argArr.getFirst());
                d_countryList = mapReader.getCountriesMap().values().stream().toList();
                if (d_countryList.isEmpty()){
                    System.out.println("Empty map loaded. Please try again.");
                    continue;
                }
                mapReader.showMap();
            } else if (command.d_commandType.equals("assigncountries")) {
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

        int playerIndex = 0;

        for (Country country : d_countryList) {
            Player assignedPlayer = d_playersList.get(playerIndex);
            assignedPlayer.ownedCountries.add(country);
            country.setOwner(assignedPlayer);
            playerIndex = (playerIndex + 1) % d_playersList.size();
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
        for(Player player : d_playersList){
            int reinforcements = 5;

            HashSet<Country> processedCountries = new HashSet<>();
            for (Country country : player.ownedCountries) {
                if (processedCountries.contains(country)) {continue;}
                Continent checkingContinent = country.getContinent();
                boolean givebonus = true;
                for (Country otherCountry : checkingContinent.getCountries()) {
                    processedCountries.add(otherCountry);
                    if (otherCountry.getOwner()!=player) {
                        givebonus = false;
                        break;
                    }
                }
                if (givebonus) {
                    reinforcements+= checkingContinent.getBonus();
                }
            }

            player.setReinforcements(reinforcements);
            System.out.println(player.getName() + " receives " + reinforcements + " reinforcements.");
        }

        // Issuing Orders Phase
        boolean ordersRemaining = true;
        while (ordersRemaining) {
            ordersRemaining = false;
            for (Player player : d_playersList) {
                if (player.getReinforcements() > 0) {                    player.issue_order();
                    ordersRemaining = true;
                }
            }
        }

        // Order Execution Phase
        boolean executingOrders = true;
        while (executingOrders) {
            executingOrders = false;
            for (Player player : d_playersList) {
                Order pendingOrder = player.next_order();
                if (pendingOrder != null) {
                    pendingOrder.execute();
                    executingOrders = true;
                }
            }
        }

    }

}
