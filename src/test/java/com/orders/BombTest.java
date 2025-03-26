package com.orders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.model.Continent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.Bomb;
import com.gameplay.Card;
import com.gameplay.GameEngine;
import com.gameplay.Parsing;
import com.gameplay.Player;
import com.model.Country;

public class BombTest {
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
     * Test a player does not have a bomb card.
     */
    @Test
    public void noBombCard() {
        System.out.println("\nTEST : Player does not have bomb card");

        // Get player 1's adjacent countries
        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (!this.d_player1.ownsCountry(l_adjCountry.getName())) {
                    l_adjacentCountries.add(l_adjCountry);
                }
            }
        }

        // set a country to bomb and set the armies to be 0
        Country l_countryToBomb = l_adjacentCountries.getFirst();

        Bomb l_bombOrder = new Bomb(this.d_player1, l_countryToBomb.getName());
        assertFalse(l_bombOrder.isValid());
    }

    /**
     * Test a player cannot use Bomb card on adjacent countries that have no armies.
     */
    @Test
    public void zeroNumberOfArmies() {
        System.out.println("\nTEST : Player cannot use bomb card on adjacent countries that have no armies");

        this.d_player1.addCards(Card.BOMB);

        // Get player 1's adjacent countries
        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (!this.d_player1.ownsCountry(l_adjCountry.getName())) {
                    l_adjacentCountries.add(l_adjCountry);
                }
            }
        }

        // set a country to bomb and set the armies to be 0
        Country l_countryToBomb = l_adjacentCountries.getFirst();
        l_countryToBomb.setArmies(0);

        Bomb l_bombOrder = new Bomb(this.d_player1, l_countryToBomb.getName());
        assertFalse(l_bombOrder.isValid());
    }

    /**
     * Test a player cannot use Bomb card on their own countries.
     */
    @Test
    public void bombOurOwnCountry() {
        System.out.println("\nTEST : Player cannot use bomb card on their own countries");

        this.d_player1.addCards(Card.BOMB);

        // Get player 1's countries
        List<Country> l_countries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            if (this.d_player1.ownsCountry(l_country.getName())) {
                l_countries.add(l_country);
            }
        }

        Country l_countryToBomb = l_countries.getFirst();
        l_countryToBomb.setArmies(5);

        Bomb l_bombOrder = new Bomb(this.d_player1, l_countryToBomb.getName());
        assertFalse(l_bombOrder.isValid());
    }

    /**
     * Test a player cannot bomb nonadjacent countries.
     */
    @Test
    public void bombNonAdjacentCountry() {
        System.out.println("\nTEST : Player cannot bomb nonadjacent countries");

        this.d_player1.addCards(Card.BOMB);

        // Get player 1's adjacent countries
        List<String> l_adjacentCountryNames = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (!this.d_player1.ownsCountry(l_adjCountry.getName())) {
                    l_adjacentCountryNames.add(l_adjCountry.getName());
                }
            }
        }

        // Get player 1's non adjacent countries
        List<Country> l_nonAdjacentCountries = new ArrayList<>();
        for (Country l_country : this.d_player2.getOwnedCountries()) {
            if (!l_adjacentCountryNames.contains(l_country.getName())) {
                l_nonAdjacentCountries.add(l_country);
            }
        }
        if (l_nonAdjacentCountries.isEmpty()) {
            l_nonAdjacentCountries.add(new Country(1, "TestCountry", new Continent(1, "TestContinent", 1)));
        }

        Country l_countryToBomb = l_nonAdjacentCountries.getFirst();
        l_countryToBomb.setArmies(5);

        Bomb l_bombOrder = new Bomb(this.d_player1, l_countryToBomb.getName());
        assertFalse(l_bombOrder.isValid());
    }

    /**
     * Test a player successfully bomb an adjacent country
     */
    @Test
    public void successfulBombCountry() {
        System.out.println("\nTEST : Player successfully use bomb card on adjacent country");

        this.d_player1.addCards(Card.BOMB);

        // Get player 1's adjacent countries
        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (!this.d_player1.ownsCountry(l_adjCountry.getName())) {
                    l_adjacentCountries.add(l_adjCountry);
                }
            }
        }

        // set a country to bomb and set the armies to be 10
        Country l_countryToBomb = l_adjacentCountries.getFirst();
        l_countryToBomb.setArmies(10);

        Bomb l_bombOrder = new Bomb(this.d_player1, l_countryToBomb.getName());
        assertTrue(l_bombOrder.isValid());

        System.out.println("\nSuccess: " + l_countryToBomb.getName() + " has been bombed by " + this.d_player1.getName());
    }
}
