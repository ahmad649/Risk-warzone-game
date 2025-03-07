package test;

import gameplay.Command;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@code CommandTest} class provides testing for the arguments passed in the user commands.
 */
public class CommandTest {
    /**
     * Parse edit continent arguments.
     */
    @Test
    public void parseEditContinentArguments() {
        System.out.println("\nTEST : Parse arguments from 'editcontinent' command");

        Command l_command = new Command("editcontinent -add continentID continentValue -remove continentID");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("\nArguments parsed from '-add' flag: " + l_command.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("continentID", "continentValue")), l_command.getArgsLabeled().get("-add"));

        System.out.println("Arguments parsed from '-remove' flag: " + l_command.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Collections.singletonList("continentID")), l_command.getArgsLabeled().get("-remove"));
    }

    /**
     * Parse edit country arguments.
     */
    @Test
    public void parseEditCountryArguments() {
        System.out.println("\nTEST : Parse arguments from 'editcountry' command");

        Command l_command = new Command("editcountry -add countryID continentID -remove countryID");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_command.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "continentID")), l_command.getArgsLabeled().get("-add"));

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_command.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Collections.singletonList("countryID")), l_command.getArgsLabeled().get("-remove"));
    }

    /**
     * Parse edit neighbor arguments.
     */
    @Test
    public void parseEditNeighborArguments() {
        System.out.println("\nTEST : Parse arguments from 'editneighbor' command");

        Command l_command = new Command("editcountry -add countryID neighborCountryID -remove countryID neighborCountryID");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_command.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "neighborCountryID")), l_command.getArgsLabeled().get("-add"));

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_command.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "neighborCountryID")), l_command.getArgsLabeled().get("-remove"));
    }

    /**
     * Parse savemap arguments.
     */
    @Test
    public void parseSavemapArguments() {
        System.out.println("\nTEST : Parse argument from 'savemap' command");

        Command l_command = new Command("savemap testFile");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from 'savemap' command: " + l_command.getArgArr().getFirst());
        assertEquals("testFile", l_command.getArgArr().getFirst());
    }

    /**
     * Parse editmap arguments.
     */
    @Test
    public void parseEditmapArguments() {
        System.out.println("\nTEST : Parse argument from 'editmap' command");

        Command l_command = new Command("editmap testFile");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from 'editmap' command: " + l_command.getArgArr().getFirst());
        assertEquals("testFile", l_command.getArgArr().getFirst());
    }

    /**
     * Parse loadmap arguments.
     */
    @Test
    public void parseLoadmapArguments() {
        System.out.println("\nTEST : Parse argument from 'loadmap' command");

        Command l_command = new Command("loadmap testFile");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from 'loadmap' command: " + l_command.getArgArr().getFirst());
        assertEquals("testFile", l_command.getArgArr().getFirst());
    }

    /**
     * Parse gameplayer arguments.
     */
    @Test
    public void parseGameplayerArguments() {
        System.out.println("\nTEST : Parse arguments from 'gameplayer' command");

        // Add and remove players at the same time
        Command l_command = new Command("gameplayer -add player1 -remove player2");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_command.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Collections.singletonList("player1")), l_command.getArgsLabeled().get("-add"));

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_command.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Collections.singletonList("player2")), l_command.getArgsLabeled().get("-remove"));

        // Add players
        l_command = new Command("gameplayer -add player1 player2");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from '-add' flag: " + l_command.getArgsLabeled().get("-add"));
        assertEquals(new ArrayList<>(Arrays.asList("player1", "player2")), l_command.getArgsLabeled().get("-add"));

        // Add players
        l_command = new Command("gameplayer -remove player3 player4");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from '-remove' flag: " + l_command.getArgsLabeled().get("-remove"));
        assertEquals(new ArrayList<>(Arrays.asList("player3", "player4")), l_command.getArgsLabeled().get("-remove"));
    }

    /**
     * Parsedeploy arguments.
     */
    @Test
    public void parsedeployArguments() {
        System.out.println("\nTEST : Parse argument from 'deploy' command");

        Command l_command = new Command("deploy countryID 4");
        System.out.println("\nRunning: " + l_command.getFullCommand());

        System.out.println("-> Arguments parsed from 'deploy' command: " + l_command.getArgArr());
        assertEquals(new ArrayList<>(Arrays.asList("countryID", "4")), l_command.getArgArr());
    }
}
