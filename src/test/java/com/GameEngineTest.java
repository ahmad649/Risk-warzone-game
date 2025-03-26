package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.States.ExecuteOrder;
import com.States.IssueOrder;
import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import com.model.Continent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.maps.MapReader;
import com.model.Country;

public class GameEngineTest {
    private Player d_player1, d_player2;
    private GameEngine d_gameEngine;
    private Phase d_gamePhase;

    /**
     * Initialize all test cases by loading Game Map, creating players, and assigning countries to all players
     */
    @BeforeEach
    public void initialize() {
        d_gameEngine = new GameEngine();

        // Load map and set countries
        System.out.println("\nLoading Map...");
        this.d_gamePhase = new Startup(this.d_gameEngine);

        this.d_gamePhase.loadMap(new Parsing("loadmap Classic_World_Map"));

        this.d_player1 = new Player("TestPlayer1");
        this.d_player2 = new Player("TestPlayer2");

        this.d_gameEngine.getPlayersList().add(this.d_player1);
        this.d_gameEngine.getPlayersList().add(this.d_player2);

        this.d_gamePhase.assignCountries();
    }

    /**
     * Calculate reinforcement armies.
     */
    @Test
    public void calculateReinforcementArmies() {
        System.out.println("\nTEST : Calculate the number of reinforcement armies\n");
        System.out.println(this.d_gameEngine.getPlayersList());

        // Change game phase to Issue Order
        this.d_gamePhase = new IssueOrder(this.d_gameEngine);
        this.d_gamePhase.assignReinforcements();

        // Calculating the number of reinforcement armies for each player
        HashMap<String, Integer> players = new HashMap<>();
        for (Player l_player: this.d_gameEngine.getPlayersList()) {
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

        System.out.println("\n-> Total number of reinforcement armies for " + this.d_player1.getName() + " : " + this.d_player1.getReinforcements());
        System.out.println("-> Total number of reinforcement armies for " + this.d_player2.getName() + " : " + this.d_player2.getReinforcements());

        assertEquals(players.get("TestPlayer1"), this.d_player1.getReinforcements());
        assertEquals(players.get("TestPlayer2"), this.d_player2.getReinforcements());
    }

    @Test
    public void verifyGamePhase() {
        System.out.println("\nTEST : Verifying game phases\n");

        System.out.println("Set game phase to 'Startup'");
        this.d_gamePhase = new Startup(this.d_gameEngine);

        System.out.println("Current game phase: " + this.d_gamePhase.currentPhase());
        assertEquals("Startup", this.d_gamePhase.currentPhase());

        System.out.println("Set game phase to 'IssueOrder'");
        this.d_gamePhase = new IssueOrder(this.d_gameEngine);

        System.out.println("Current game phase: " + this.d_gamePhase.currentPhase());
        assertEquals("IssueOrder", this.d_gamePhase.currentPhase());

        System.out.println("Set game phase to 'ExecuteOrder'");
        this.d_gamePhase = new ExecuteOrder(this.d_gameEngine);

        System.out.println("Current game phase: " + this.d_gamePhase.currentPhase());
        assertEquals("ExecuteOrder", this.d_gamePhase.currentPhase());
    }
}
