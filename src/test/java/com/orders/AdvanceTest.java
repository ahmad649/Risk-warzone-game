package com.orders;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.GameEngine;
import com.gameplay.Parsing;
import com.gameplay.Player;
import com.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdvanceTest {
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
     * Test a player does not own the source country
     */
    @Test
    public void playerDoesNotOwnSourceCountry() {
        System.out.println("\nTEST : Player doesn't own source country");

        Country l_countryFrom = this.d_player2.getOwnedCountries().getFirst();
        Country l_countryTo = this.d_player2.getOwnedCountries().getLast();

        Advance l_advanceOrder = new Advance(this.d_gameEngine, this.d_player1, l_countryFrom.getName(), l_countryTo.getName(), 2);
        assertFalse(l_advanceOrder.isValid());
    }

    /**
     * Test the source and target country are the same
     */
    @Test
    public void sourceAndTargetCountryTheSame() {
        System.out.println("\nTEST : Source an target country are the same");

        Country l_countryFrom = this.d_player1.getOwnedCountries().getFirst();
        Country l_countryTo = this.d_player1.getOwnedCountries().getFirst();

        Advance l_advanceOrder = new Advance(this.d_gameEngine, this.d_player1, l_countryFrom.getName(), l_countryTo.getName(), 2);
        assertFalse(l_advanceOrder.isValid());
    }

    /**
     * Test source country does not have enough armies
     */
    @Test
    public void notEnoughArmies() {
        System.out.println("\nTEST : Source country does not have enough armies");

        Country l_countryFrom = this.d_player1.getOwnedCountries().getFirst();

        // Get player 1's adjacent countries
        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            for (Country l_adjCountry : l_country.getNeighbors()) {
                if (!this.d_player1.ownsCountry(l_adjCountry.getName())) {
                    l_adjacentCountries.add(l_adjCountry);
                }
            }
        }
        Country l_countryTo = l_adjacentCountries.getFirst();

        Advance l_advanceOrder = new Advance(this.d_gameEngine, this.d_player1, l_countryFrom.getName(), l_countryTo.getName(), 15);
        assertFalse(l_advanceOrder.isValid());
    }

    /**
     * Test source and target countries are not adjacent
     */
    @Test
    public void SourceAndTargetCountryAdjacentOrNot() {
        System.out.println("\nTEST : Source and target country are not adjacent to each other");

        Country l_countryFrom = this.d_player1.getOwnedCountries().getFirst();
        l_countryFrom.setArmies(10);

        Country l_countryTo = this.d_player2.getOwnedCountries().getFirst();
        l_countryTo.setArmies(10);

        Advance l_advanceOrder = new Advance(this.d_gameEngine, this.d_player1, l_countryFrom.getName(), l_countryTo.getName(), 2);

        if (l_countryFrom.isNeighbor(l_countryTo.getName())) {
            System.out.println("\nSource and target country are adjacent");
            assertTrue(l_advanceOrder.isValid());
        } else {
            assertFalse(l_advanceOrder.isValid());
        }
    }

    /**
     * Test attacker winning and successfully occupies the target country
     */
    @Test
    public void AttackerWinning() {
        System.out.println("\nTEST : Attacker successfully capture the defender country");

        Country l_countryFrom = this.d_player1.getOwnedCountries().getFirst();
        l_countryFrom.setArmies(10);

        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : l_countryFrom.getNeighbors()) {
            if (!l_country.getOwner().getName().equals(this.d_player1.getName())) {
                l_adjacentCountries.add(l_country);
            }
        }

        if (l_adjacentCountries.isEmpty()) {
            System.out.println("\n" + l_countryFrom.getName() + " has no adjacent countries");
            return;
        }

        Country l_countryTo = l_adjacentCountries.getFirst();
        l_countryTo.setArmies(5);

        Advance l_advanceOrder = new Advance(this.d_gameEngine, this.d_player1, l_countryFrom.getName(), l_countryTo.getName(), 10);
        l_advanceOrder.execute();

        assertEquals(0, l_countryFrom.getArmies());
        assertEquals(6, l_countryTo.getArmies());
    }

    /**
     * Test defender successfully defends the country
     */
    @Test
    public void DefenderWinning() {
        System.out.println("\nTEST : Defender successfully defends the country");

        Country l_countryFrom = this.d_player1.getOwnedCountries().getFirst();
        l_countryFrom.setArmies(26);

        List<Country> l_adjacentCountries = new ArrayList<>();
        for (Country l_country : l_countryFrom.getNeighbors()) {
            if (!l_country.getOwner().getName().equals(this.d_player1.getName())) {
                l_adjacentCountries.add(l_country);
            }
        }

        if (l_adjacentCountries.isEmpty()) {
            System.out.println("\n" + l_countryFrom.getName() + " has no adjacent countries");
            return;
        }

        Country l_countryTo = l_adjacentCountries.getFirst();
        l_countryTo.setArmies(20);

        Advance l_advanceOrder = new Advance(this.d_gameEngine, this.d_player1, l_countryFrom.getName(), l_countryTo.getName(), 25);
        l_advanceOrder.execute();

        assertEquals(12, l_countryFrom.getArmies());
        assertEquals(5, l_countryTo.getArmies());
    }
}
