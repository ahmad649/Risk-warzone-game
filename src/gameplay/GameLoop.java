package gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameLoop {
    List<Player> d_playersList = new ArrayList<>();

    List<Country> d_countryList = new ArrayList<>();

    public void startup(){
        /*
        TODO:
         Take input from player
         2 types:
         "gameplayer" - adds player (has args refer the grading sheet)
         "assigncountries" - adds countries randomly to all players and ends startup
          Use InputOutput class to take the input and continue further
         */
        System.out.println("Game setup started. Add players using 'gameplayer -add <playername>'.");
        boolean gameSetup = true;

        while (gameSetup) {
            String command = InputOutput.get_user_command();

            if (InputOutput.is_gameplayer_command_valid(command)) {
                String[] parts = command.split(" ");
                if (parts[1].equals("-add")) {
                    String playerName = parts[2];
                    d_playersList.add(new Player(playerName));
                    System.out.println("Player added: " + playerName);
                } else if (parts[1].equals("-remove")) {
                    String playerName = parts[2];
                    d_playersList.removeIf(p -> p.getName().equals(playerName));
                    System.out.println("Player removed: " + playerName);
                }
            } else if (InputOutput.is_assigncountries_command_valid(command)) {
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
