package com.orders;

import com.States.IssueOrder;
import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import com.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeployTest {
    private Player d_player1, d_player2;
    private GameEngine d_gameEngine;
    private Phase d_gamePhase;

    /**
     * Initialize all test cases by loading Game Map, creating players, and assigning countries to all players
     */
    @BeforeEach
    public void initialize() {
        this.d_gameEngine = new GameEngine();

        // Load map and set countries
        System.out.println("\nLoading Map...");
        this.d_gamePhase = new Startup(this.d_gameEngine);

        this.d_gamePhase.loadMap(new Parsing("loadmap Classic_World_Map.txt"));

        this.d_player1 = new Player("TestPlayer1");
        this.d_player2 = new Player("TestPlayer2");

        this.d_gameEngine.getPlayersList().add(this.d_player1);
        this.d_gameEngine.getPlayersList().add(this.d_player2);

        this.d_gamePhase.assignCountries();
    }

    /**
     * Test a player does not own the target country
     */
    @Test
    public void playerNotOwnCountry() {
        System.out.println("\nTEST : Player deploys armies where it is not their country");

        Country l_countryToDeploy = this.d_player2.getOwnedCountries().getFirst();
        Deploy l_deployOrder = new Deploy(this.d_player1, l_countryToDeploy.getName(), 5);
        assertFalse(l_deployOrder.isValid());
    }

    /**
     * Test deploying more armies than the available reinforcement pool.
     */
    @Test
    public void NotEnoughReinforcements() {
        System.out.println("\nTEST : Disallow deploying more armies than the reinforcement pool\n");

        // Change game phase to Issue Order
        this.d_gamePhase = new IssueOrder(this.d_gameEngine);
        this.d_gamePhase.assignReinforcements();

        int l_player1_deploy_armies = 15;
        int l_player2_deploy_armies = 20;

        String l_countryOwnedByPlayer1 = this.d_player1.getOwnedCountries().getFirst().getName();
        String l_countryOwnedByPlayer2 = this.d_player2.getOwnedCountries().getFirst().getName();

        // Deploy armies
        System.out.println();
        Order l_player1_order = new Deploy(this.d_player1, l_countryOwnedByPlayer1, l_player1_deploy_armies);
        l_player1_order.execute();

        System.out.println();
        Order l_player2_order = new Deploy(this.d_player2, l_countryOwnedByPlayer2, l_player2_deploy_armies);
        l_player2_order.execute();

        assertNotEquals(l_player1_deploy_armies, this.d_player1.getReinforcements());
        assertNotEquals(l_player2_deploy_armies, this.d_player2.getReinforcements());
    }
}
