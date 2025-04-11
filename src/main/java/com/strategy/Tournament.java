package com.strategy;

import com.States.ExecuteOrder;
import com.States.IssueOrder;
import com.States.Startup;
import com.gameplay.*;
import com.model.Country;
import com.orders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * A tournament starts with the user choosing M = 1 to 5 different maps, P = 2 to 4 different computer players strategies, G = 1 to 5
 * games to be played on each map, D = 10 to 50 maximum number of tums for each game. A toumament is then automatically played
 * by playing G games on each of the M different maps between the chosen computer player strategies. In order to minimize run completion time, each game should be declared a draw after D tums. Once started, the tournament plays all the games automatically without user interaction.
 */
public class Tournament {
    private final GameEngine d_gameEngine;
    private ArrayList<String> d_maps;
    private final ArrayList<ArrayList<String>> d_winners;
    private final ArrayList<Player> d_players;

    /**
     * Instantiates a new Tournament.
     *
     * @param p_gameEngine the game engine
     */
    public Tournament(GameEngine p_gameEngine) {
        this.d_gameEngine = p_gameEngine;
        this.d_gameEngine.d_phase = new Startup(this.d_gameEngine);
        this.d_players = new ArrayList<>();
        this.d_winners = new ArrayList<>();
    }

    /**
     * Get the list of players.
     *
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return d_players;
    }

    /**
     * Start tournament.
     *
     * @param p_parsing the parsed user command
     */
    public void start(Parsing p_parsing) {
        System.out.println("\nTOURNAMENT STARTED.");

        // Extract arguments
        List<String> l_maps = p_parsing.getArgsLabeled().get("-M");
        List<String> l_playerStrategies = p_parsing.getArgsLabeled().get("-P");
        int l_numOfGames = Integer.parseInt(p_parsing.getArgsLabeled().get("-G").getFirst());
        int l_maxNumOfTurns = Integer.parseInt(p_parsing.getArgsLabeled().get("-D").getFirst());

        for (int l_i = 0; l_i < l_maps.size(); l_i++) {
            this.d_winners.add(new ArrayList<>());
        }

        for (int l_k = 0; l_k < l_maps.size(); l_k++) {
            this.d_gameEngine.d_phase = new Startup(this.d_gameEngine);
            this.d_gameEngine.d_phase.loadMap(new Parsing("loadmap " + l_maps.get(l_k)));
            this.addPlayers((ArrayList<String>) l_playerStrategies);

            this.d_gameEngine.d_phase.assignCountries();

            for (int l_i = 0; l_i < l_numOfGames; l_i++) {
                for (int l_j = 0; l_j < l_maxNumOfTurns; l_j++) {
                    this.d_gameEngine.d_phase = new IssueOrder(this.d_gameEngine);
                    this.d_gameEngine.d_phase.assignReinforcements();

                    this.issueOrders();

                    this.d_gameEngine.d_phase = new ExecuteOrder(this.d_gameEngine);
                    this.executeOrders();
                }
                String l_winner = this.getWinner();
                this.d_winners.get(l_k).add(l_winner);
            }
        }
        this.displayResult(l_maps, l_playerStrategies, l_numOfGames, l_maxNumOfTurns);
    }

    /**
     * Add players to join the tournament mode.
     *
     * @param p_playerStrategies the list of player strategies
     */
    public void addPlayers(ArrayList<String> p_playerStrategies) {
        Player l_player;

        for (String playerStrategy : p_playerStrategies) {
            switch (playerStrategy.toLowerCase()) {
                case "aggressive":
                    l_player = new Player("Aggressive Player");
                    l_player.setPlayerStrategy(new AggressivePlayerStrategy(this.d_gameEngine, l_player));
                    this.d_gameEngine.getPlayersList().add(l_player);
                    this.d_players.add(l_player);
                    break;
                case "benevolent":
                    l_player = new Player("Benevolent Player");
                    l_player.setPlayerStrategy(new BenevolentPlayerStrategy(this.d_gameEngine, l_player));
                    this.d_gameEngine.getPlayersList().add(l_player);
                    this.d_players.add(l_player);
                    break;
                case "random":
                    l_player = new Player("Random Player");
                    l_player.setPlayerStrategy(new RandomPlayerStrategy(this.d_gameEngine, l_player));
                    this.d_gameEngine.getPlayersList().add(l_player);
                    this.d_players.add(l_player);
                    break;
                case "cheater":
                    l_player = new Player("Cheater Player");
                    l_player.setPlayerStrategy(new CheaterPlayerStrategy(this.d_gameEngine, l_player));
                    this.d_gameEngine.getPlayersList().add(l_player);
                    this.d_players.add(l_player);
                    break;
            }
        }

    }

    /**
     * Issue orders of each player strategy.
     */
    public void issueOrders() {
        System.out.println("\nISSUE ORDER PHASE");

        for (Player l_player : this.d_gameEngine.getPlayersList()) {
            l_player.issue_order(this.d_gameEngine, new Parsing(""));
        }

    }

    /**
     * Execute orders of each player strategy.
     */
    public void executeOrders() {
        System.out.println("\nEXECUTE ORDER PHASE");
        for (Player l_player : this.d_gameEngine.getPlayersList()) {
            Order l_order = l_player.next_order();
            if (l_order != null) {
                l_order.execute();
            }
        }
    }

    /**
     * Get winner at the end of the max number of turns.
     *
     * @return the winner name
     */
    public String getWinner() {
        HashMap<String, Integer> l_ownersHashmap = new HashMap<>();
        for (Country l_country : this.d_gameEngine.d_countryList) {
            String l_owner = l_country.getOwner().getName(); // or use appropriate toString if owner is not a String
            l_ownersHashmap.put(l_owner, l_ownersHashmap.getOrDefault(l_owner, 0) + 1);
        }

        String maxKey = null;
        int maxValue = Integer.MIN_VALUE;
        for (HashMap.Entry<String, Integer> entry : l_ownersHashmap.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        if (l_ownersHashmap.size() == 2) {
            return maxKey;
        } else if (l_ownersHashmap.size() == 3) {
            return maxKey;
        } else if (l_ownersHashmap.size() == 4) {
            return maxKey;
        }

        return "Draw";
    }

    /**
     * Display the end result of the tournament.
     *
     * @param p_maps             the maps
     * @param l_playerStrategies the player strategies
     * @param l_numOfGames       the number of games
     * @param l_maxNumOfTurns    the maximum number of turns
     */
    public void displayResult(List<String> p_maps, List<String> l_playerStrategies, int l_numOfGames, int l_maxNumOfTurns) {
        System.out.println("M:  " + String.join(" ", p_maps));
        System.out.println("P:  " + String.join(" ", l_playerStrategies));
        System.out.println("G:  " + l_numOfGames);
        System.out.println("D:  " + l_maxNumOfTurns + "\n");

        System.out.printf("%-15s", ""); // empty top-left cell
        for (int i = 1; i <= l_numOfGames; i++) {
            System.out.printf("%-25s", "Game " + i);
        }
        System.out.println();

        // Print table body
        for (int i = 0; i < this.d_winners.size(); i++) {
            System.out.printf("%-15s", "Map " + (i + 1));
            for (int j = 0; j < this.d_winners.get(i).size(); j++) {
                System.out.printf("%-25s", this.d_winners.get(i).get(j));
            }
            System.out.println();
        }
    }
}
