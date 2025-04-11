package com.orders;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import com.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlockadeTest {
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
     * Test a player does not have a blockade card.
     */
    @Test
    public void noBlockadeCard() {
        System.out.println("\nTEST : Player does not have blockade card");

        // Get player 1's adjacent countries
        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (!this.d_player1.ownsCountry(l_adjCountry.getName())) {
                    l_adjacentCountries.add(l_adjCountry);
                }
            }
        }

        // set a country to blockade and set the armies to be 0
        Country l_countryToBlockade = l_adjacentCountries.getFirst();

        Blockade l_blockadeOrder = new Blockade(this.d_gameEngine, this.d_player1, l_countryToBlockade.getName());
        assertFalse(l_blockadeOrder.isValid());
    }

    /**
     * Test a player cannot use Blockade card on other player's countries
     */
    @Test
    public void PlayerDoesNotOwnCountry() {
        System.out.println("\nTEST : Player cannot use blockade card on other player's countries");

        this.d_player1.addCards(Card.BLOCKADE);

        // Get player 1's adjacent countries
        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (!this.d_player1.ownsCountry(l_adjCountry.getName())) {
                    l_adjacentCountries.add(l_adjCountry);
                }
            }
        }

        // set a country to blockade and set the armies to be 0
        Country l_countryToBlockade = l_adjacentCountries.getFirst();

        Blockade l_blockadeOrder = new Blockade(this.d_gameEngine, this.d_player1, l_countryToBlockade.getName());
        assertFalse(l_blockadeOrder.isValid());
    }

    /**
     * Test a player cannot use Blockade card on adjacent countries that have no armies.
     */
    @Test
    public void zeroNumberOfArmies() {
        System.out.println("\nTEST : Player cannot use blockade card on other player's countries");

        this.d_player1.addCards(Card.BLOCKADE);

        // Get one of the Player 1's countries
        Country l_countryToBlockade = this.d_player1.getOwnedCountries().getFirst();
        l_countryToBlockade.setArmies(0);

        Blockade l_blockadeOrder = new Blockade(this.d_gameEngine, this.d_player1, l_countryToBlockade.getName());
        assertFalse(l_blockadeOrder.isValid());

    }

    /**
     * Test a player successfully performs a blockade on their country
     */
    @Test
    public void successfulBlockade() {
        System.out.println("\nTEST : Player use blockade card successfully");

        this.d_player1.addCards(Card.BLOCKADE);

        // Get player 1's countries
        List<Country> l_countries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            if (this.d_player1.ownsCountry(l_country.getName())) {
                l_countries.add(l_country);
            }
        }

        // Set armies on the country to blockade
        Country l_countryToBlockade = l_countries.getFirst();
        l_countryToBlockade.setArmies(5);

        l_countryToBlockade.setArmies(l_countryToBlockade.getArmies() * 3);

        System.out.println("\nSuccess: Now country " + l_countryToBlockade.getName() + " has " + l_countryToBlockade.getArmies() + " armies");

        assertEquals(15, l_countryToBlockade.getArmies());
    }
}
