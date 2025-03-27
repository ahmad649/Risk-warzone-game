package com.orders;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiplomacyTest {
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

        this.d_gamePhase.loadMap(new Parsing("loadmap Classic_World_Map"));

        this.d_player1 = new Player("TestPlayer1");
        this.d_player2 = new Player("TestPlayer2");

        this.d_gameEngine.getPlayersList().add(this.d_player1);
        this.d_gameEngine.getPlayersList().add(this.d_player2);

        this.d_gamePhase.assignCountries();
    }

    /**
     * Test a player does not have a diplomacy card.
     */
    @Test
    public void noDiplomacyCard() {
        System.out.println("\nTEST : Player does not have diplomacy card");

        Diplomacy l_diplomacyOrder = new Diplomacy(this.d_gameEngine, this.d_player1, this.d_player2.getName());
        assertFalse(l_diplomacyOrder.isValid());
    }

    /**
     * Test diplomacy order where the target player does not exist in the player list
     */
    @Test
    public void targetPlayerDoesNotExist() {
        System.out.println("\nTEST : Player performs diplomacy where the target player does not exist");

        this.d_player1.addCards(Card.DIPLOMACY);

        Diplomacy l_diplomacyOrder = new Diplomacy(this.d_gameEngine, this.d_player1, "TestPlayer3");
        assertFalse(l_diplomacyOrder.isValid());
    }

    /**
     * Test diplomacy order where the target player is the same as the current player.
     */
    @Test
    public void targetPlayerSameAsCurrentPlayer() {
        System.out.println("\nTEST : Player performs diplomacy where the target player is the same as the current player");

        this.d_player1.addCards(Card.DIPLOMACY);

        Diplomacy l_diplomacyOrder = new Diplomacy(this.d_gameEngine, this.d_player1, this.d_player1.getName());
        assertFalse(l_diplomacyOrder.isValid());
    }

    /**
     * Test diplomacy runs successfully.
     */
    @Test
    public void successfulDiplomacy() {
        System.out.println("\nTEST : Player performs diplomacy successfully");

        this.d_player1.addCards(Card.DIPLOMACY);

        Diplomacy l_diplomacyOrder = new Diplomacy(this.d_gameEngine, this.d_player1, this.d_player2.getName());
        l_diplomacyOrder.execute();

        String l_playerInDiplomacyList = this.d_player1.getDiplomacyPlayers().getFirst().getName();

        assertEquals(l_playerInDiplomacyList, this.d_player2.getName());
    }
}
