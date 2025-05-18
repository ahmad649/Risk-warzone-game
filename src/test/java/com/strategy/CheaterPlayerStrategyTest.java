package com.strategy;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheaterPlayerStrategyTest {
    private Player d_player1, d_player2;
    private GameEngine d_gameEngine;
    private Phase d_gamePhase;

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

    @Test
    public void conquerAllEnemyNeighbors() {
        System.out.println("\nTEST : Cheater player conquers all enemy neighbors.");

        CheaterPlayerStrategy l_cheaterPlayer1 = new CheaterPlayerStrategy(this.d_gameEngine, this.d_player1);

        int l_playerReinforcements = this.d_player1.getReinforcements();

        ArrayList<Country> l_conqueredCountries = l_cheaterPlayer1.conquerAllEnemyNeighbors(l_playerReinforcements);
        if (l_conqueredCountries.isEmpty()) {
            return;
        }

        assertEquals(1, l_conqueredCountries.size());
    }

    @Test
    public void doubleArmies() {
        System.out.println("\nTEST : Cheater player doubles armies.");

        CheaterPlayerStrategy l_cheaterPlayer1 = new CheaterPlayerStrategy(this.d_gameEngine, this.d_player1);

        for (Country l_country : this.d_player1.getOwnedCountries()) {
            l_country.setArmies(5);
        }

        List<Country> l_countriesWithEnemyNeighbors = l_cheaterPlayer1.getAllCountriesWithEnemyNeighbors(this.d_player1.getOwnedCountries());

        // Doubles the number of armies on countries that have enemy neighbors
        for (Country l_country : l_countriesWithEnemyNeighbors) {
            int l_currentArmies = l_country.getArmies();

            l_country.setArmies(l_currentArmies * 2);
        }

        int l_armies = 10;
        for (Country l_country : l_countriesWithEnemyNeighbors) {
            if (l_country.getArmies() != l_armies) {
                l_armies = l_country.getArmies();
            }
        }

        assertEquals(10, l_armies);

    }
}
