package com.gameplay;

import com.States.Startup;
import com.States.TournamentMode;
import com.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TournamentTest {
    private GameEngine d_gameEngine;
    private Tournament d_tournament;
    private TournamentMode d_mode;

    @BeforeEach
    public void initialize() {
        this.d_gameEngine = new GameEngine();

        this.d_tournament = new Tournament(this.d_gameEngine);
    }

    @Test
    public void validateTournamentCommand() {
        System.out.println("\nTEST: Parse arguments from 'tournament' command");

        Parsing l_parsing = new Parsing("tournament -M Classic_World_Map Game_of_Thrones_Map Witcher_Map -P Aggressive Benevolent Random Cheater -G 3 -D 15");

        // Extract arguments
        List<String> l_maps = l_parsing.getArgsLabeled().get("-M");
        List<String> l_playerStrategies = l_parsing.getArgsLabeled().get("-P");
        int l_numOfGames = Integer.parseInt(l_parsing.getArgsLabeled().get("-G").getFirst());
        int l_maxNumOfTurns = Integer.parseInt(l_parsing.getArgsLabeled().get("-D").getFirst());

        System.out.println("\nM:  " + String.join(" ", l_maps));
        System.out.println("P:  " + String.join(" ", l_playerStrategies));
        System.out.println("G:  " + l_numOfGames);
        System.out.println("D:  " + l_maxNumOfTurns + "\n");

        assertArrayEquals(new String[]{"Classic_World_Map", "Game_of_Thrones_Map", "Witcher_Map"}, l_maps.toArray());
        assertArrayEquals(new String[]{"Aggressive", "Benevolent", "Random", "Cheater"}, l_playerStrategies.toArray());
        assertEquals(3, l_numOfGames);
        assertEquals(15, l_maxNumOfTurns);
    }

    @Test
    public void allowedNumOfMaps() {
        System.out.println("\nTEST: Validate number of maps allowed");

        boolean l_validCommand = InputOutput.is_tournament_command_valid("tournament -M Classic_World_Map Game_of_Thrones_Map Witcher_Map Test1_Map Test2_Map Test3_Map -P Aggressive Benevolent Random Cheater -G 3 -D 15");

        assertFalse(l_validCommand);
    }

    @Test
    public void allowedNumOfPlayerStrategies() {
        System.out.println("\nTEST: Validate number of player strategies allowed");

        boolean l_validCommand1 = InputOutput.is_tournament_command_valid("tournament -M Classic_World_Map Game_of_Thrones_Map Witcher_Map -P Aggressive -G 3 -D 15");
        boolean l_validCommand2 = InputOutput.is_tournament_command_valid("tournament -M Classic_World_Map Game_of_Thrones_Map Witcher_Map -P Aggressive Benevolent Random Cheater Aggressive -G 3 -D 15");

        assertFalse(l_validCommand1);
        assertFalse(l_validCommand2);
    }

    @Test
    public void allowedNumberOfTurns() {
        System.out.println("\nTEST: Validate number of turns allowed");

        boolean l_validCommand1 = InputOutput.is_tournament_command_valid("tournament -M Classic_World_Map Game_of_Thrones_Map Witcher_Map -P Aggressive Benevolent Random Cheater -G 3 -D 51");
        boolean l_validCommand2 = InputOutput.is_tournament_command_valid("tournament -M Classic_World_Map Game_of_Thrones_Map Witcher_Map -P Aggressive Benevolent Random Cheater -G 3 -D 7");

        assertFalse(l_validCommand1);
        assertFalse(l_validCommand2);
    }

    @Test
    public void addPlayers() {
        System.out.println("\nTEST : Verifying game phases\n");
        this.d_gameEngine.d_phase = new Startup(this.d_gameEngine);
        this.d_gameEngine.d_phase.loadMap(new Parsing("loadmap Classic_World_Map"));

        ArrayList<String> l_players = new ArrayList<>(
                Arrays.asList("Aggressive", "Benevolent", "Random", "Cheater")
        );
        this.d_tournament.addPlayers(l_players);
        System.out.println("\nAdding players: " + l_players);

        assertEquals(4, this.d_gameEngine.getPlayersList().size());
    }

}
