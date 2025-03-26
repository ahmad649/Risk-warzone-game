package com.orders;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.Advance;
import com.gameplay.GameEngine;
import com.gameplay.Parsing;
import com.gameplay.Player;
import com.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

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

        this.d_gamePhase.loadMap(new Parsing("loadmap Classic_World_Map"));

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
        Country l_countryFrom = this.d_player1.getOwnedCountries().getFirst();
        Country l_countryTo = this.d_player1.getOwnedCountries().getFirst();

        Advance l_advanceOrder = new Advance(this.d_gameEngine, this.d_player1, l_countryFrom.getName(), l_countryTo.getName(), 2);
        assertFalse(l_advanceOrder.isValid());
    }

    /**
     * Test not enough armies
     */
    @Test
    public void notEnoughArmies() {
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
}
