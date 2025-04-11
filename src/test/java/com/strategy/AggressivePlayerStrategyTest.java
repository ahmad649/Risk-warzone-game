package com.strategy;

import com.States.Phase;
import com.States.Startup;
import com.gameplay.*;
import com.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggressivePlayerStrategyTest {
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
    public void deployToStrongestCountry() {
        System.out.println("\nTEST : Aggressive Player deploys armies to the strongest country.");

        AggressivePlayerStrategy l_aggressivePlayer1 = new AggressivePlayerStrategy(this.d_gameEngine, this.d_player1);

        Country l_country = this.d_player1.getOwnedCountries().getFirst();
        l_country.setArmies(10);

        // get the strongest country
        Country l_strongestCountry = l_aggressivePlayer1.getStrongestCountry();

        int l_numOfArmies = Math.round((float) (this.d_player1.getReinforcements()) / 2);
        // deploy armies
        Deploy l_deployOrder = l_aggressivePlayer1.deployArmiesToStrongestCountry(l_strongestCountry, l_numOfArmies);
        if (l_deployOrder == null) {
            return;
        }

        // execute deploy order
        l_deployOrder.execute();


        assertEquals(10 + l_numOfArmies, l_strongestCountry.getArmies());
    }

    @Test
    public void attackWithStrongestCountry() {
        System.out.println("\nTEST : Aggressive Player attacks with its strongest country.");
        AggressivePlayerStrategy l_aggressivePlayer1 = new AggressivePlayerStrategy(this.d_gameEngine, this.d_player1);

        Country l_country = this.d_player1.getOwnedCountries().getFirst();
        l_country.setArmies(10);

        // get the strongest country
        Country l_strongestCountry = l_aggressivePlayer1.getStrongestCountry();
        System.out.println(l_strongestCountry.getArmies());

        // create advance order to attack neighboring country
        Advance l_advanceOrder = l_aggressivePlayer1.attackWithStrongestCountry(l_strongestCountry);

        if (l_advanceOrder != null) {
            // execute advance order
            l_advanceOrder.execute();

            assertEquals(5, l_strongestCountry.getArmies());
        }

    }
}
