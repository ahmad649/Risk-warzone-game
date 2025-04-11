package com.strategy;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import com.model.Country;
import com.orders.Advance;
import com.orders.Deploy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BenevolentPlayerStrategyTest {
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
    public void deployToWeakestCountry() {
        System.out.println("\nTEST : Benevolent player deploys armies to the weakest country.");

        int l_playerArmies = this.d_player1.getReinforcements();

        BenevolentPlayerStrategy l_benevolentPlayer1 = new BenevolentPlayerStrategy(this.d_gameEngine, this.d_player1);

        // get the weakest country
        Country l_weakestCountry = l_benevolentPlayer1.getWeakestCountry(this.d_player1.getOwnedCountries());

        // deploy armies
        Deploy l_deployOrder = l_benevolentPlayer1.deployToWeakestCountry(l_weakestCountry);

        // execute deploy order
        l_deployOrder.execute();

        assertEquals(l_playerArmies, l_weakestCountry.getArmies());
    }

    @Test
    public void moveArmiesFromStrongestToWeakestCountry() {
        System.out.println("\nTEST : Benevolent player move armies from the strongest country to the weakest country.");

        BenevolentPlayerStrategy l_benevolentPlayer1 = new BenevolentPlayerStrategy(this.d_gameEngine, this.d_player1);

        Country l_strongestCountry = this.d_player1.getOwnedCountries().getFirst();
        if (l_strongestCountry.getNeighbors().isEmpty()) {
            return;
        }

        // set the number of armies in the strongest country
        l_strongestCountry.setArmies(10);
        int strongestCountryArmies = l_strongestCountry.getArmies();

        Random l_random = new Random();
        int l_numOfArmies = l_random.nextInt(l_strongestCountry.getArmies() + 1);

        Advance l_advanceOrder = l_benevolentPlayer1.moveArmiesFromStrongestToWeakestCountry(l_strongestCountry, l_numOfArmies);
        if (l_advanceOrder == null) {
            return;
        }
        l_advanceOrder.execute();

        assertEquals(10 - l_numOfArmies, strongestCountryArmies - l_numOfArmies);
    }
}
