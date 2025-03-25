package com;

import com.gameplay.Parsing;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@code CommandTest} class provides testing for the arguments passed in the user commands.
 */
public class ParsingTest {
    /**
     * Parse edit continent arguments.
     */
    @Test
    public void parseEditContinentArguments() {
        System.out.println("\nTEST : Parse arguments from 'editcontinent' command");

        Parsing l_parsing = new Parsing("editcontinent -add continentID continentValue -remove continentID");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("\nArguments parsed from '-add' flag: " + l_parsing.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("continentID", "continentValue")), l_parsing.getArgsLabeled().get("-add"));

        System.out.println("Arguments parsed from '-remove' flag: " + l_parsing.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Collections.singletonList("continentID")), l_parsing.getArgsLabeled().get("-remove"));
    }

    /**
     * Parse edit country arguments.
     */
    @Test
    public void parseEditCountryArguments() {
        System.out.println("\nTEST : Parse arguments from 'editcountry' command");

        Parsing l_parsing = new Parsing("editcountry -add countryID continentID -remove countryID");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_parsing.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "continentID")), l_parsing.getArgsLabeled().get("-add"));

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_parsing.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Collections.singletonList("countryID")), l_parsing.getArgsLabeled().get("-remove"));
    }

    /**
     * Parse edit neighbor arguments.
     */
    @Test
    public void parseEditNeighborArguments() {
        System.out.println("\nTEST : Parse arguments from 'editneighbor' command");

        Parsing l_parsing = new Parsing("editcountry -add countryID neighborCountryID -remove countryID neighborCountryID");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_parsing.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "neighborCountryID")), l_parsing.getArgsLabeled().get("-add"));

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_parsing.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "neighborCountryID")), l_parsing.getArgsLabeled().get("-remove"));
    }

    /**
     * Parse savemap arguments.
     */
    @Test
    public void parseSavemapArguments() {
        System.out.println("\nTEST : Parse argument from 'savemap' command");

        Parsing l_parsing = new Parsing("savemap testFile");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from 'savemap' command: " + l_parsing.getArgArr().getFirst());
        assertEquals("testFile", l_parsing.getArgArr().getFirst());
    }

    /**
     * Parse editmap arguments.
     */
    @Test
    public void parseEditmapArguments() {
        System.out.println("\nTEST : Parse argument from 'editmap' command");

        Parsing l_parsing = new Parsing("editmap testFile");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from 'editmap' command: " + l_parsing.getArgArr().getFirst());
        assertEquals("testFile", l_parsing.getArgArr().getFirst());
    }

    /**
     * Parse loadmap arguments.
     */
    @Test
    public void parseLoadmapArguments() {
        System.out.println("\nTEST : Parse argument from 'loadmap' command");

        Parsing l_parsing = new Parsing("loadmap testFile");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from 'loadmap' command: " + l_parsing.getArgArr().getFirst());
        assertEquals("testFile", l_parsing.getArgArr().getFirst());
    }

    /**
     * Parse gameplayer arguments.
     */
    @Test
    public void parseGameplayerArguments() {
        System.out.println("\nTEST : Parse arguments from 'gameplayer' command");

        // Add and remove players at the same time
        Parsing l_parsing = new Parsing("gameplayer -add player1 -remove player2");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_parsing.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Collections.singletonList("player1")), l_parsing.getArgsLabeled().get("-add"));

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_parsing.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Collections.singletonList("player2")), l_parsing.getArgsLabeled().get("-remove"));

        // Add players
        l_parsing = new Parsing("gameplayer -add player1 player2");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_parsing.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("player1", "player2")), l_parsing.getArgsLabeled().get("-add"));

        // Add players
        l_parsing = new Parsing("gameplayer -remove player3 player4");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_parsing.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Arrays.asList("player3", "player4")), l_parsing.getArgsLabeled().get("-remove"));
    }

    /**
     * Parsedeploy arguments.
     */
    @Test
    public void parsedeployArguments() {
        System.out.println("\nTEST : Parse argument from 'deploy' command");

        Parsing l_parsing = new Parsing("deploy countryID 4");
        System.out.println("\nRunning: " + l_parsing.getFullCommand());

        System.out.println("-> Arguments parsed from 'deploy' command: " + l_parsing.getArgArr());
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "4")), l_parsing.getArgArr());
    }
}
