package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.model.Continent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.gameplay.GameEngine;
import com.gameplay.Order;
import com.gameplay.Player;
import com.maps.MapReader;
import com.model.Country;

public class GameEngineTest {
    private Player d_player1, d_player2;
    private List<Player> d_playersList = new ArrayList<>();
    private final GameEngine d_gameEngine = new GameEngine();
    private final MapReader d_mapReader = new MapReader();

    /**
     * Initialize all test cases by loading Game Map, creating players, and assigning countries to all players
     */
    @BeforeEach
    public void initialize() {
        // Load map and set countries
        System.out.println("\nLoading Map...");
        d_mapReader.loadMap("Classic_World_Map");
        List<Country> l_countryList = d_mapReader.getCountriesMap().values().stream().toList();
        d_gameEngine.setCountryList(l_countryList);

        d_player1 = new Player("TestPlayer1");
        d_player2 = new Player("TestPlayer2");

        d_gameEngine.getPlayersList().add(d_player1);
        d_gameEngine.getPlayersList().add(d_player2);

        d_gameEngine.assignCountries();
    }

    /**
     * Calculate reinforcement armies.
     */
    @Test
    public void calculateReinforcementArmies() {
        System.out.println("\nTEST : Calculate the number of reinforcement armies\n");

        d_gameEngine.assignReinforcements();

        // Calculating the number of reinforcement armies for each player
        HashMap<String, Integer> players = new HashMap<>();
        for (Player l_player: d_gameEngine.getPlayersList()) {
            int l_armies = 5;
            HashSet<Country> l_processedCountries = new HashSet<>();
            for (Country l_country: l_player.d_ownedCountries) {
                if (l_processedCountries.contains(l_country)) {
                    continue;
                }
                Continent l_checkingContinent = l_country.getContinent();
                boolean l_givebonus = true;
                for (Country otherCountry : l_checkingContinent.getCountries()) {
                    l_processedCountries.add(otherCountry);
                    if (otherCountry.getOwner() != (l_player)) {
                        l_givebonus = false;
                        break;
                    }
                }
                if (l_givebonus) {
                    players.put(l_player.getName(), l_armies + l_checkingContinent.getBonus());
                }
            }
            if (!players.containsKey(l_player.getName())) {
                players.put(l_player.getName(), l_armies);
            }
        }

        System.out.println("\n-> Total number of reinforcement armies for " + d_player1.getName() + " : " + d_player1.getReinforcements());
        System.out.println("-> Total number of reinforcement armies for " + d_player2.getName() + " : " + d_player2.getReinforcements());

        assertEquals(players.get("TestPlayer1"), d_player1.getReinforcements());
        assertEquals(players.get("TestPlayer2"), d_player2.getReinforcements());
    }

    /**
     * Deploy more armies than the available reinforcement pool.
     */
//    @Test
//    public void deployMoreArmiesThanReinforcementPool() {
//        System.out.println("\nTEST : Disallow deploying more armies than the reinforcement pool\n");
//
//        d_gameEngine.assignReinforcements();
//
//        int l_player1_deploy_armies = 15;
//        int l_player2_deploy_armies = 20;
//
//        String l_countryOwnedByPlayer1 = d_player1.getOwnedCountries().getFirst().getName();
//        String l_countryOwnedByPlayer2 = d_player2.getOwnedCountries().getFirst().getName();
//
//        // Deploy armies
//        System.out.println();
//        Order l_player1_order = new Order("deploy", l_countryOwnedByPlayer1, l_player1_deploy_armies, d_player1);
//        l_player1_order.execute();
//
//        System.out.println();
//        Order l_player2_order = new Order("deploy", l_countryOwnedByPlayer2, l_player2_deploy_armies, d_player2);
//        l_player2_order.execute();
//
//        assertNotEquals(l_player1_deploy_armies, d_player1.getReinforcements());
//        assertNotEquals(l_player2_deploy_armies, d_player2.getReinforcements());
//    }
}
