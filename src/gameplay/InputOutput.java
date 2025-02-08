package gameplay;

import java.io.File;
import java.util.Scanner;

public class InputOutput {
    /*
    TODO:
        Take input Via scanner, validate and return
        Different possible inputs depending on the stage of the game with varying args
        Take stage as parameter and validate
        Currently only 3 possible inputs refer grading sheet
        2 for startup and 1 during issue_order
     */

    public static void get_user_command() {
        Scanner l_scanner = new Scanner(System.in);

        System.out.println("Enter command: ");
        String l_command = l_scanner.nextLine();

        if (is_editcontinent_command_valid(l_command)) {
            System.out.println("Executing editcontinent command");
            /**
             * TODO: User-driven creation/deletion of map elements: continent.
             */
        } else if (is_editcountry_command_valid(l_command)) {
            System.out.println("Executing editcountry command");
            /**
             * TODO: User-driven creation/deletion of map elements: country.
             */
        } else if (is_editneighbor_command_valid(l_command)) {
            System.out.println("Executing editneighbor command");
            /**
             * TODO: User-driven creation/deletion of map elements: connectivity between countries.
             */
        } else if (is_showmap_command_valid(l_command)) {
            System.out.println("Executing showmap command");
            /**
             * TODO:
             *  1) GAMEPLAY:
             *  show all countries and continents, armies on each country, ownership, and connectivity in a way that enables efficient gameplay.
             *  2) MAP EDITOR:
             *  show all continents and countries and their respective neighbors.
             */
        } else if (is_savemap_command_valid(l_command)) {
            System.out.println("Executing savemap command");
            /**
             * TODO: save a map to a text file exactly as edited (using the 'domination game map format').
             */
        } else if (is_editmap_command_valid(l_command)) {
            System.out.println("Executing editmap command");
            /**
             * TODO: load a map from an existing 'domination' map file, or create a new map from scratch if the file does not exist
             */
        } else if (is_validatemap_command_valid(l_command)) {
            System.out.println("Executing validatemap command");
            /**
             * TODO: Verification of map correctness. The map should be automatically validated upon loading and before saving (at least 3 types of incorrect maps). The 'validatemap' command can be triggered anytime during map editing.
             */
        } else if (is_loadmap_command_valid(l_command)) {
            System.out.println("Executing loadmap command");
            /**
             * TODO: Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph.
             */
        } else if (is_gameplayer_command_valid(l_command)) {
            System.out.println("Executing gameplayer command");
            /**
             * TODO: user creates the players
             */
        } else if (is_assigncountries_command_valid(l_command)) {
            System.out.println("Executing assigncountries command");
            /**
             * TODO: all countries are randomly assigned to players
             */
        } else if (is_deploy_command_valid(l_command)) {
            System.out.println("Executing deploy command");
            /**
             * TODO: The GameEngine class calls the issue _order) method of the Player. This method will wait for the following command, then create a deploy order object on the player's list of orders, then reduce the number of armies in the player's reinforcement pool. The game engine does this for all players in round-robin fashion until all the players have placed all their reinforcement armies on the map.
             */
        } else {
            System.out.println("Command does not exist. Please try again.");
        }
    }

    public static boolean is_editcontinent_command_valid(String p_command) {
        if (!p_command.startsWith("editcontinent ")) {
            return false;
        }

        // Split the command
        String[] l_parts = p_command.trim().split(" ");

        // Check duplicates in flag argument
        boolean l_addFlag = false, l_removeFlag = false;

        // Iterate over the arguments
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Ensure "-add" is followed by <continentID> and <continentvalue>
                    if (l_addFlag) {
                        System.out.println("Duplicate '-add' section found.");
                        return false;
                    }
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("Invalid syntax for -add. It must be followed by <continentID> and <continentvalue>.");
                        return false;
                    }
                    // Check that continentID is a number and continentvalue is a string
                    try {
                        Integer.parseInt(l_parts[l_i + 1]); // Validate continentID
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid <continentID>. It must be a number.");
                        return false;
                    }
                    // Assume continentvalue is any non-empty string
                    if (l_parts[l_i + 2].isEmpty()) {
                        System.out.println("Invalid <continentvalue>. It cannot be empty.");
                        return false;
                    }
                    l_addFlag = true;
                    l_i += 2; // Skip the validated arguments
                    break;
                case "-remove":
                    // Ensure "-remove" is followed by <continentID>
                    if (l_removeFlag) {
                        System.out.println("Duplicate '-remove' section found.");
                        return false;
                    }
                    if (l_i + 1 >= l_parts.length) {
                        System.out.println("Invalid syntax for -remove. It must be followed by <continentID>.");
                        return false;
                    }
                    // Check that continentID is a number
                    try {
                        Integer.parseInt(l_parts[l_i + 1]); // Validate continentID
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid <continentID>. It must be a number.");
                        return false;
                    }
                    l_removeFlag = true;
                    l_i += 1; // Skip the validated argument
                    break;
                default:
                    System.out.println("Unknown argument in 'editcontinent' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if any flag arguments provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("Invalid command. Missing '-add' or '-remove' section in the command.");
            return false;
        }
        return true;
    }

    public static boolean is_editcountry_command_valid(String p_command) {
        if (!p_command.startsWith("editcountry ")) {
            return false;
        }

        // Split the command
        String[] l_parts = p_command.trim().split(" ");

        // Check duplicates in flag arguments
        boolean l_addFlag = false, l_removeFlag = false;

        // Iterate over the arguments
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Ensure "-add" is followed by <countryID> and <continentID>
                    if (l_addFlag) {
                        System.out.println("Duplicate '-add' section found.");
                        return false;
                    }
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("Invalid syntax for -add. It must be followed by <countryID> and <continentID>.");
                        return false;
                    }
                    // Check that countryID and continentID are numbers
                    try {
                        Integer.parseInt(l_parts[l_i + 1]); // Validate countryID
                        Integer.parseInt(l_parts[l_i + 2]); // Validate continentID
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid <countryID> or <continentID>. Both must be numbers.");
                        return false;
                    }
                    l_addFlag = true;
                    l_i += 2; // Skip the validated arguments
                    break;
                case "-remove":
                    // Ensure "-remove" is followed by <countryID>
                    if (l_removeFlag) {
                        System.out.println("Duplicate '-remove' section found.");
                        return false;
                    }
                    if (l_i + 1 >= l_parts.length) {
                        System.out.println("Invalid syntax for -remove. It must be followed by <countryID>.");
                        return false;
                    }
                    // Check that countryID is a number
                    try {
                        Integer.parseInt(l_parts[l_i + 1]); // Validate countryID
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid <countryID>. It must be a number.");
                        return false;
                    }
                    l_removeFlag = true;
                    l_i += 1; // Skip the validated argument
                    break;
                default:
                    System.out.println("Unknown argument in 'editcountry' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if any flag arguments provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("Invalid command. Missing '-add' or '-remove' section in the command.");
            return false;
        }
        return true;
    }

    public static boolean is_editneighbor_command_valid(String p_command) {
        if (!p_command.startsWith("editneighbor ")) {
            return false;
        }

        // Split the command
        String[] l_parts = p_command.trim().split(" ");

        boolean l_addFlag = false, l_removeFlag = false;

        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    if (l_addFlag) {
                        System.out.println("Duplicate '-add' section found.");
                        return false;
                    }
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("Invalid syntax for -add. It must be followed by <countryID> and <neighborcountryID>.");
                        return false;
                    }
                    try {
                        Integer.parseInt(l_parts[l_i + 1]); // Validate countryID
                        Integer.parseInt(l_parts[l_i + 2]); // Validate neighborcountryID
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid <countryID> or <neighborcountryID>. Both must be numbers.");
                        return false;
                    }
                    l_addFlag = true;
                    l_i += 2; // Skip the validated arguments
                    break;
                case "-remove":
                    if (l_removeFlag) {
                        System.out.println("Duplicate '-remove' section found.");
                        return false;
                    }
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("Invalid syntax for -remove. It must be followed by <countryID> and <neighborcountryID>.");
                        return false;
                    }
                    try {
                        Integer.parseInt(l_parts[l_i + 1]); // Validate countryID
                        Integer.parseInt(l_parts[l_i + 2]); // Validate neighborcountryID
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid <countryID> or <neighborcountryID>. Both must be numbers.");
                        return false;
                    }
                    l_removeFlag = true;
                    l_i += 2; // Skip the validated arguments
                    break;
                default:
                    System.out.println("Unknown argument in 'editneighbor' command: " + l_parts[l_i]);
                    return false;
            }
        }

        if (!l_addFlag && !l_removeFlag) {
            System.out.println("Invalid command. Missing '-add' or '-remove' section in the command.");
            return false;
        }

        return true;
    }

    public static boolean is_showmap_command_valid(String p_command) {
        return p_command.trim().equals("showmap");
    }

    public static boolean is_savemap_command_valid(String p_command) {
        if (!p_command.startsWith("savemap ")) {
            return false;
        }

        // Split command to get filename
        String[] l_parts = p_command.trim().split(" ");

        // Check command length
        if (l_parts.length != 2) {
            System.out.println("Invalid command. 'savemap' command must have only 1 argument.");
            return false;
        }

        String l_filename = l_parts[1];

        // Validate filename
        if (!l_filename.endsWith(".map")) {
            System.out.println("Invalid filename. The file must have a .map extension.");
            return false;
        }

        return true;
    }

    public static boolean is_editmap_command_valid(String p_command) {
        if (!p_command.startsWith("editmap ")) {
            return false;
        }

        // Split command to get filename
        String[] l_parts = p_command.trim().split(" ");

        // Check command length
        if (l_parts.length != 2) {
            System.out.println("Invalid command. 'editmap' command must have only 1 argument.");
            return false;
        }

        String l_filename = l_parts[1];

        // Validate filename
        if (!l_filename.endsWith(".map")) {
            System.out.println("Invalid filename. The file must have a .map extension.");
            return false;
        }

        return true;
    }

    public static boolean is_validatemap_command_valid(String p_command) {
        return p_command.trim().equals("validatemap");
    }

    public static boolean is_loadmap_command_valid(String p_command) {
        if (!p_command.startsWith("loadmap ")) {
            return false;
        }
        // Split command to get filename
        String[] l_parts = p_command.trim().split(" ");

        // Check command length
        if (l_parts.length != 2) {
            System.out.println("Invalid command. 'loadmap' command must have only 1 argument.");
            return false;
        }

        String l_filename = l_parts[1];

        // Validate filename
        if (!l_filename.endsWith(".map")) {
            System.out.println("Invalid filename. The file must have a .map extension.");
            return false;
        }

        // Check whether file exists
        String l_filePath = "path/to/maps" + l_filename;
        File l_file = new File(l_filePath);
        if (!l_file.exists()) {
            System.out.println("File does not exist.");
            return false;
        }
        return true;
    }

    public static boolean is_gameplayer_command_valid(String p_command) {
        if (!p_command.startsWith("gameplayer ")) {
            return false;
        }

        // Split the command
        String[] l_parts = p_command.trim().split(" ");

        boolean l_addFlag = false, l_removeFlag = false;

        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    if (l_addFlag) {
                        System.out.println("Duplicate '-add' section found.");
                        return false;
                    }
                    if (l_i + 1 >= l_parts.length || l_parts[l_i + 1].isEmpty()) {
                        System.out.println("Invalid syntax for -add. It must be followed by a player name.");
                        return false;
                    }
                    l_addFlag = true;
                    l_i += 1; // Skip player name
                    break;
                case "-remove":
                    if (l_removeFlag) {
                        System.out.println("Duplicate '-remove' section found.");
                        return false;
                    }
                    if (l_i + 1 >= l_parts.length || l_parts[l_i + 1].isEmpty()) {
                        System.out.println("Invalid syntax for -remove. It must be followed by a player name.");
                        return false;
                    }
                    l_removeFlag = true;
                    l_i += 1; // Skip player name
                    break;
                default:
                    System.out.println("Unknown argument in 'gameplayer' command: " + l_parts[l_i]);
                    return false;
            }
        }

        if (!l_addFlag && !l_removeFlag) {
            System.out.println("Invalid command. Missing '-add' or '-remove' section in the command.");
            return false;
        }

        return true;
    }

    public static boolean is_assigncountries_command_valid(String p_command) {
        return p_command.trim().equals("assigncountries");
    }

    public static boolean is_deploy_command_valid(String p_command) {
        if (!p_command.startsWith("deploy ")) {
            return false;
        }

        // Split the command to extract arguments
        String[] l_parts = p_command.trim().split(" ");

        // Validate the structure of the command: it must have exactly 3 parts
        if (l_parts.length != 3) {
            System.out.println("Invalid command. 'deploy' command must follow the syntax: deploy <countryID> <num>.");
            return false;
        }

        // Validate countryID is a number
        try {
            Integer.parseInt(l_parts[1]); // Validate countryID
        } catch (NumberFormatException e) {
            System.out.println("Invalid <countryID>. It must be a number.");
            return false;
        }

        // Validate num (number of armies) is a positive number
        try {
            if (Integer.parseInt(l_parts[2]) <= 0) {
                System.out.println("Invalid <num>. It must be a positive number.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid <num>. It must be a number.");
            return false;
        }

        return true;
    }

}
