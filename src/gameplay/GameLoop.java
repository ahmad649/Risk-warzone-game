package gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameLoop {
    List<Player> d_playersList = new ArrayList<>();

    List<Country> d_countryList = new ArrayList<>();

    public void startup(){
        /*
        TODO:
         Take input from player
         2 types:
         "gameplayer" - adds player (has argsLabled refer the grading sheet)
         "assigncountries" - adds countries randomly to all players and ends startup
          Use InputOutput class to take the input and continue further
         */
        System.out.println("Game setup started. Add players using 'gameplayer -add <playername>'.");
        boolean gameSetup = true;

        while (gameSetup) {
            String command = null;
            while (command == null){
                command = InputOutput.get_user_command();
            }
            if (command.startsWith("gameplayer")){
                // parse arguments from gameplayer command
                HashMap<String, List<String>> l_arguments = Command.parse_gameplayer_command(command);
                for (String name : l_arguments.get("add")) {
                    d_playersList.add(new Player(name));
                }

                for (String name : l_arguments.get("remove")) {
                    // remove player from player list
                }

//                if (command.argsLabled.containsKey("-add")) {
//                    String playerName = command.argsLabled.get("-add");
//                    d_playersList.add(new Player(playerName));
//                    System.out.println("Player added: " + playerName);
//                } else if (command.argsLabled.containsKey("-remove")) {
//                    String playerName = command.argsLabled.get("-remove");
//                    d_playersList.removeIf(p -> p.getName().equals(playerName));
//                    System.out.println("Player removed: " + playerName);
//                }
            } else if (command.startsWith("assigncountries")) {
                assignCountries();
                gameSetup = false;
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }
    }

    private void assignCountries() {
        if (d_playersList.isEmpty() || d_countryList.isEmpty()) {
            System.out.println("Cannot assign countries. Ensure players and countries are available.");
            return;
        }

        Random random = new Random();
        int playerIndex = 0;

        for (Country country : d_countryList) {
            Player assignedPlayer = d_playersList.get(playerIndex);
            // TODO: Counry pending
            country.setOwner(assignedPlayer);
            playerIndex = (playerIndex + 1) % d_playersList.size();
        }

        System.out.println("All countries have been assigned to players.");
    }

    public void looper(){

        if (d_playersList.isEmpty()) {
            System.out.println("No players available. Exiting game loop.");
            return;
        }

        for(Player player : d_playersList){
            //TODO: Calculate and assign each player their appropriate reinforcements according to the rules
            int reinforcements = 5; // Simplified reinforcement rule
            player.setReinforcements(reinforcements);
            System.out.println(player.getName() + " receives " + reinforcements + " reinforcements.");
        }

        boolean ordersRemaining = true;
        while (ordersRemaining) {
            ordersRemaining = false;
            for (Player player : d_playersList) {
                if (player.getReinforcements() > 0) {
                    player.issue_order();
                    ordersRemaining = true;
                }
            }
        }

        // **Order Execution Phase**
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
