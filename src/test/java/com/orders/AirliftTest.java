package com.orders;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import com.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AirliftTest {
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
     * Test a player does not have an airlift card.
     */
    @Test
    public void noAirliftCard() {
        Airlift l_airliftOrder = new Airlift(this.d_player1, "Peru", "Peru", 5);
        assertFalse(l_airliftOrder.isValid());
    }

    /**
     * Test a player performs airlift with the same source and target country.
     */
    @Test
    public void sameSourceAndTargetCountry() {
        System.out.println("\nTEST : Player performs airlift with the same source and target country");

        this.d_player1.getCards().add(Card.AIRLIFT);

        Airlift l_airliftOrder = new Airlift(this.d_player1, "Peru", "Peru", 5);

        assertFalse(l_airliftOrder.isValid());
    }

    /**
     * Test a player does not own source country
     */
    @Test
    public void playerDoesNotOwnSourceCountry() {
        System.out.println("\nTEST : Player performs airlift from the source country that is not owned by the player");

        this.d_player1.getCards().add(Card.AIRLIFT);

        Country l_sourceCountry = this.d_player2.getOwnedCountries().getFirst();
        Country l_targetCountry = this.d_player1.getOwnedCountries().getFirst();

        Airlift l_airliftOrder = new Airlift(this.d_player1, l_sourceCountry.getName(), l_targetCountry.getName(), 5);

        assertFalse(l_airliftOrder.isValid());
    }

    /**
     * Test a player does not own target country
     */
    @Test
    public void playerDoesNotOwnTargetCountry() {
        System.out.println("\nTEST : Player performs airlift to the target country that is not owned by the player");

        this.d_player1.getCards().add(Card.AIRLIFT);

        Country l_sourceCountry = this.d_player1.getOwnedCountries().getFirst();
        Country l_targetCountry = this.d_player2.getOwnedCountries().getFirst();

        Airlift l_airliftOrder = new Airlift(this.d_player1, l_sourceCountry.getName(), l_targetCountry.getName(), 5);

        assertFalse(l_airliftOrder.isValid());
    }

    /**
     * Test a player does not own target country
     */
    @Test
    public void insufficientArmies() {
        System.out.println("\nTEST : Player performs airlift where the source country does not have sufficient armies");

        this.d_player1.getCards().add(Card.AIRLIFT);

        Country l_sourceCountry = this.d_player1.getOwnedCountries().getFirst();
        Country l_targetCountry = this.d_player1.getOwnedCountries().getLast();

        Airlift l_airliftOrder = new Airlift(this.d_player1, l_sourceCountry.getName(), l_targetCountry.getName(), 10);

        assertFalse(l_airliftOrder.isValid());
    }

    /**
     * Test a player performs airlift successfully
     */
    @Test
    public void successfulAirlift() {
        System.out.println("\nTEST : Player performs airlift successfully");

        this.d_player1.getCards().add(Card.AIRLIFT);

        Country l_sourceCountry = this.d_player1.getOwnedCountries().getFirst();
        Country l_targetCountry = this.d_player1.getOwnedCountries().getLast();

        int l_sourceArmies = 10;
        int l_targetArmies = 3;
        int l_numArmies = 5;

        l_sourceCountry.setArmies(l_sourceArmies);
        l_targetCountry.setArmies(l_targetArmies);

        Airlift l_airliftOrder = new Airlift(this.d_player1, l_sourceCountry.getName(), l_targetCountry.getName(), l_numArmies);
        l_airliftOrder.execute();

        assertEquals(l_sourceArmies - l_numArmies, l_sourceCountry.getArmies());
        assertEquals(l_targetArmies + l_numArmies, l_targetCountry.getArmies());
    }
}
