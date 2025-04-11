package com.strategy;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import com.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomPlayerStrategyTest {
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
    public void deployRandomly() {
        RandomPlayerStrategy l_randomPlayer1 = new RandomPlayerStrategy(this.d_gameEngine, this.d_player1);

        Random l_random = new Random();
        int l_numOfArmies = l_random.nextInt(this.d_player1.getReinforcements() + 1);

        Deploy l_deployOrder = l_randomPlayer1.deployArmiesToRandomCountry(l_numOfArmies);

        l_deployOrder.execute();

        Country l_deployedCountry = null;
        for (Country l_country : this.d_player1.getOwnedCountries()) {
            if (l_country.getArmies() == l_numOfArmies) {
                l_deployedCountry = l_country;
            }
        }
        if (l_deployedCountry == null) {
            return;
        }

        assertEquals(l_numOfArmies, l_deployedCountry.getArmies());
    }

    @Test
    public void advanceArmiesToRandomCountry() {
        RandomPlayerStrategy l_randomPlayer1 = new RandomPlayerStrategy(this.d_gameEngine, this.d_player1);

        Country l_countryFrom = l_randomPlayer1.getOwnedCountry();
        if (l_countryFrom == null) {
            return;
        }

        Country l_countryTo = l_countryFrom.getNeighbors().getFirst();
        if (l_countryTo == null) {
            return;
        }

        l_countryFrom.setArmies(10);
        l_countryTo.setArmies(5);

        int l_tempNum = l_countryFrom.getArmies() + 1;
        if (l_tempNum < 1) {
            l_tempNum = 1;
        }

        int l_countryToArmies = l_countryTo.getArmies();
        Random l_random = new Random();
        int l_numOfArmies = l_random.nextInt(l_tempNum);

        Advance l_advanceOrder = l_randomPlayer1.advanceArmiesToRandomCountry(l_numOfArmies, l_countryFrom, l_countryTo);

        l_advanceOrder.execute();

        if (!l_countryFrom.getName().equals(l_countryTo.getOwner().getName())) {
            return;
        }
        assertEquals(l_countryToArmies + l_numOfArmies, l_countryTo.getArmies());
    }
}
