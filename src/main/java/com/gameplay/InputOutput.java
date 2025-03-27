package com.gameplay;

import java.util.Scanner;

import com.maps.MapReader;

/**
 * {@code InputOutput} class manages all Input and Output operations that are used in Map Editor and Gameplay.
 */
public class InputOutput {

    /**
     * Instantiates a new InputOutput object.
     */
    public InputOutput() {}

    /**
     * Prompts user to enter a command, perform command validation, and returns Command object where the user command has been parsed.
     * <p>
     * The method accepts the following user commands:
     * </p>
     * <ul>
     *   <li>{@code editcontinent}</li>
     *   <li>{@code editcountry}</li>
     *   <li>{@code editneighbor}</li>
     *   <li>{@code showmap}</li>
     *   <li>{@code savemap}</li>
     *   <li>{@code editmap}</li>
     *   <li>{@code validatemap}</li>
     *   <li>{@code loadmap}</li>
     *   <li>{@code gameplayer}</li>
     *   <li>{@code assigncountries}</li>
     *   <li>{@code deploy}</li>
     * </ul>
     *
     * @return A new {@link Parsing} object where the user command has been parsed, otherwise {@code null} if the provided user command does not match any commands
     */
    public static Parsing get_user_command() {
        Scanner l_scanner = new Scanner(System.in);

        System.out.println("Enter command: ");
        String l_command = l_scanner.nextLine();

        // Validate all commands, then perform actions
        if (is_editcontinent_command_valid(l_command)) {
            System.out.println("Executing editcontinent command");
            return new Parsing(l_command);

        } else if (is_editcountry_command_valid(l_command)) {
            System.out.println("Executing editcountry command");
            return new Parsing(l_command);

        } else if (is_editneighbor_command_valid(l_command)) {
            System.out.println("Executing editneighbor command");
            return new Parsing(l_command);

        } else if (is_showmap_command_valid(l_command)) {
            System.out.println("Executing showmap command");
            return new Parsing(l_command);

        } else if (is_savemap_command_valid(l_command)) {
            System.out.println("Executing savemap command");
            return new Parsing(l_command);

        } else if (is_editmap_command_valid(l_command)) {
            System.out.println("Executing editmap command");
            return new Parsing(l_command);

        } else if (is_validatemap_command_valid(l_command)) {
            System.out.println("Executing validatemap command");
            return new Parsing(l_command);

        } else if (is_loadmap_command_valid(l_command)) {
            System.out.println("Executing loadmap command");
            return new Parsing(l_command);

        } else if (is_gameplayer_command_valid(l_command)) {
            System.out.println("Executing gameplayer command");
            return new Parsing(l_command);

        } else if (is_assigncountries_command_valid(l_command)) {
            System.out.println("Executing assigncountries command");
            return new Parsing(l_command);

        } else if (is_deploy_command_valid(l_command)) {
            System.out.println("Executing deploy command");
            return new Parsing(l_command);

        } else if (is_advance_command_valid(l_command)) {
            System.out.println("Executing advance command");
            return new Parsing(l_command);

        } else if (is_bomb_command_valid(l_command)) {
            System.out.println("Executing bomb command");
            return new Parsing(l_command);

        } else if (is_blockade_command_valid(l_command)) {
            System.out.println("Executing blockade command");
            return new Parsing(l_command);

        } else if (is_airlift_command_valid(l_command)) {
            System.out.println("Executing airlift command");
            return new Parsing(l_command);

        } else if (is_negotiate_command_valid(l_command)) {
            System.out.println("Executing negotiate command");
            return new Parsing(l_command);

        } else if (is_quit_command_valid(l_command)) {
            System.out.println("Executing quit command");
            return new Parsing(l_command);

        } else if (is_continue_command_valid(l_command)) {
            System.out.println("Executing continue command");
            return new Parsing(l_command);
        } else if (is_menu_command_valid(l_command)) {
            System.out.println("Executing menu command");
            return new Parsing(l_command);
        }else if (is_editor_command_valid(l_command)) {
            System.out.println("Executing editor command");
            return new Parsing(l_command);
        }else if (is_startgame_command_valid(l_command)) {
            System.out.println("Executing startgame command");
            return new Parsing(l_command);
        }
         else {
            System.out.println("Command does not exist. Please try again.");
        }
        return null;
    }

    /**
     * Validates whether the given 'editcontinent' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_editcontinent_command_valid(String p_command) {
        // Check if command starts with 'editcontinent '
        if (!p_command.startsWith("editcontinent ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check duplicates for '-add' and '-remove'
        boolean l_addFlag = false, l_removeFlag = false;

        // Iterate over the split commands
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Check if there are multiple '-add' flags
                    if (l_addFlag) {
                        System.out.println("\n'editcontinent' command cannot have multiple '-add' flags.");
                        return false;
                    }

                    // Check if 'continentID' and 'continentValue' are given in the '-add' flag
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-add' flag must be followed by 'continentID' and 'continentValue'.");
                        return false;
                    }

                    // Check if 'continentValue' is empty
                    if (l_parts[l_i + 2].isEmpty()) {
                        System.out.println("\n'continentValue' cannot be empty.");
                        return false;
                    }

                    l_addFlag = true; // set l_addFlag to true to prevent duplicate '-add' flag
                    l_i += 2; // skip checking on 'continentID' and 'continentValue'
                    break;
                case "-remove":
                    // Check if there are multiple '-remove' flags
                    if (l_removeFlag) {
                        System.out.println("\n'editcontinent' command cannot have multiple -remove flags.");
                        return false;
                    }

                    // Check if 'continentID' is given in the '-remove' flag
                    if (l_i + 1 >= l_parts.length) {
                        System.out.println("\n'-remove' flag must be followed by 'continentID'.");
                        return false;
                    }

                    l_removeFlag = true; // set l_removeFlag to true to prevent duplicate '-remove' flag
                    l_i += 1; // skip checking on 'continentID'
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'editcontinent' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if '-add' or '-remove' flags provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'editcontinent' command must have '-add' or '-remove' flags.");
            return false;
        }
        return true;
    }

    /**
     * Validates whether the given 'editcountry' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_editcountry_command_valid(String p_command) {
        // Check if command starts with 'editcountry '
        if (!p_command.startsWith("editcountry ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check duplicates for '-add' and '-remove'
        boolean l_addFlag = false, l_removeFlag = false;

        // Iterate over the split commands
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Check if there are multiple '-add' flags
                    if (l_addFlag) {
                        System.out.println("\n'editcountry' command cannot have multiple '-add' flags.");
                        return false;
                    }

                    // Check if 'countryID' and 'continentID' are given in the '-add' flag
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-add' flag must be followed by 'countryID' and 'continentID'.");
                        return false;
                    }

                    l_addFlag = true; // set l_addFlag to true to prevent duplicate '-add' flag
                    l_i += 2; // skip checking on 'countryID' and 'continentID'
                    break;
                case "-remove":
                    // Check if there are multiple '-remove' flags
                    if (l_removeFlag) {
                        System.out.println("\n'editcountry' command cannot have multiple '-remove' flags.");
                        return false;
                    }

                    // Check if 'countryID' is given in the '-remove' flag
                    if (l_i + 1 >= l_parts.length) {
                        System.out.println("\n'-remove' flag must be followed by 'countryID'.");
                        return false;
                    }

                    l_removeFlag = true; // set l_removeFlag to true to prevent duplicate '-remove' flag
                    l_i += 1; // skip checking on 'countryID'
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'editcountry' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if '-add' or '-remove' flags provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'editcountry' command must have '-add' or '-remove' flags.");
            return false;
        }
        return true;
    }

    /**
     * Validates whether the given 'editneighbor' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_editneighbor_command_valid(String p_command) {
        // Check if command starts with 'editneighbor '
        if (!p_command.startsWith("editneighbor ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check duplicates for '-add' and '-remove'
        boolean l_addFlag = false, l_removeFlag = false;

        // Iterate over the split commands
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Check if there are multiple '-add' flags
                    if (l_addFlag) {
                        System.out.println("\n'editneighbor' command cannot have multiple '-add' flags.");
                        return false;
                    }
                    // Check if 'countryID' and 'neighborCountryID' are given in the '-add' flag
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-add' flag must be followed by 'countryID' and 'neighborCountryID'.");
                        return false;
                    }

                    l_addFlag = true; // set l_addFlag to true to prevent duplicate '-add' flag
                    l_i += 2; // skip checking on 'continentID' and 'continentValue'
                    break;
                case "-remove":
                    // Check if there are multiple '-remove' flags
                    if (l_removeFlag) {
                        System.out.println("\n'editneighbor' command cannot have multiple '-remove' flags.");
                        return false;
                    }

                    // Check if 'countryID' and 'neighborCountryID' are given in the '-remove' flag
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-remove' flag must be followed by 'countryID' and 'neighborCountryID'.");
                        return false;
                    }

                    l_removeFlag = true; // set l_remove to true to prevent duplicate '-remove' flag
                    l_i += 2; // skip checking on 'countryID' and neighborCountryID
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'editneighbor' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if '-add' or '-remove' flags provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'editneighbor' command must have '-add' or '-remove' flags.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'showmap' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_showmap_command_valid(String p_command) {
        // Check if command equals to 'showmap'
        return p_command.trim().equals("showmap");
    }

    /**
     * Validates whether the given 'savemap' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_savemap_command_valid(String p_command) {
        // Check if command starts with 'savemap '
        if (!p_command.startsWith("savemap ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains more than 1 argument.
        if (l_parts.length != 2) {
            System.out.println("\n'savemap' command must have 1 argument.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'menu' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_menu_command_valid(String p_command) {
        // Check if command equals to 'menu'
        return p_command.trim().equals("menu");
    }

    /**
     * Validates whether the given 'editor' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_editor_command_valid(String p_command) {
        // Check if command equals to 'editor'
        return p_command.trim().equals("editor");
    }

    /**
     * Validates whether the given 'startgame' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_startgame_command_valid(String p_command) {
        // Check if command equals to 'startgame'
        return p_command.trim().equals("startgame");
    }

    /**
     * Validates whether the given 'editmap' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_editmap_command_valid(String p_command) {
        // Check if command starts with 'editmap'
        if (!p_command.startsWith("editmap ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains more than 1 argument.
        if (l_parts.length != 2) {
            System.out.println("\n'editmap' command must have 1 argument.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'validatemap' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_validatemap_command_valid(String p_command) {
        // Check if command equals to 'validatemap'
        return p_command.trim().equals("validatemap");
    }

    /**
     * Validates whether the given 'loadmap' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_loadmap_command_valid(String p_command) {
        // Check if command starts with 'loadmap '
        if (!p_command.startsWith("loadmap ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains more than 1 argument.
        if (l_parts.length != 2) {
            System.out.println("\n'loadmap' command must have 1 argument.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'gameplayer' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_gameplayer_command_valid(String p_command) {
        // Check if command starts with 'gameplayer '
        if (!p_command.startsWith("gameplayer ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if '-add' or '-remove' flags are provided
        boolean l_addFlag = false, l_removeFlag = false;

        // Iterate over the split commands
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Check if 'playerName' is given in the '-add' flag
                    if (l_i + 1 >= l_parts.length || l_parts[l_i + 1].isEmpty()) {
                        System.out.println("\n'-add' flag must be followed by 'playerName'");
                        return false;
                    }

                    l_addFlag = true; // set l_addFlag to true to indicate '-add' flag is provided at least once
                    l_i += 1; // skip checking on 'playerName'
                    break;
                case "-remove":
                    // Check if 'playerName' is given in the '-remove' flag
                    if (l_i + 1 >= l_parts.length || l_parts[l_i + 1].isEmpty()) {
                        System.out.println("\n'-remove' flag must be followed by 'playerName'");
                        return false;
                    }

                    l_removeFlag = true; // set l_removeFlag to true to indicate '-remove' flag is provided at least once
                    l_i += 1; // skip checking on 'playerName'
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'gameplayer' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if '-add' or '-remove' flags provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'gameplayer' command must have '-add' or '-remove' flags.");
            return false;
        }
        return true;
    }

    /**
     * Validates whether the given 'assigncountries' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_assigncountries_command_valid(String p_command) {
        // Check if command equals to 'assigncountries'
        return p_command.trim().equals("assigncountries");
    }

    /**
     * Validates whether the given 'deploy' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_deploy_command_valid(String p_command) {
        // Check if command starts with 'deploy '
        if (!p_command.startsWith("deploy ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains more than 2 argument.
        if (l_parts.length != 3) {
            System.out.println("\n'deploy' command must be followed by 'countryID' and 'num'.");
            return false;
        }


        try {
            // Check if 'num' is a positive number
            if (Integer.parseInt(l_parts[2]) <= 0) {
                System.out.println("\n'num' value must be a positive number.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("\n'num' value must be a number.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'continue' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_continue_command_valid(String p_command) {
        // Check if command starts with 'deploy '
        if (!p_command.startsWith("continue")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains more than 2 argument.
        if (l_parts.length != 1) {
            return false;
        }
        return true;
    }

    /**
     * Validates whether the given 'quit' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_quit_command_valid(String p_command) {
        return p_command.trim().equals("quit");
    }

    /**
     * Validates whether the given 'advance' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_advance_command_valid(String p_command) {
        // Check if command starts with 'advance '
        if (!p_command.startsWith("advance ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains exactly 4 arguments
        if (l_parts.length != 4) {
            System.out.println("\nInvalid number of arguments for 'advance' command.");
            return false;
        }
        // Check if countryNameFrom and countryNameTo are strings
        if (!l_parts[1].matches("[a-zA-Z]+") || !l_parts[2].matches("[a-zA-Z]+")){
            System.out.println("\nSource and target country names must be string.");
            return false;
        }

        // Try to parse the number of armies as an integer
        try {
            Integer.valueOf(l_parts[3]);
        } catch (NumberFormatException e) {
            System.out.println("\n'numArmies' value must be a number.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'bomb' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_bomb_command_valid(String p_command) {
        // Check if command starts with 'bomb '
        if (!p_command.startsWith("bomb ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains exactly 2 arguments
        if (l_parts.length != 2) {
            System.out.println("\n'bomb' command must have 2 arguments.");
            return false;
        }

        // Check if countryID is a string containing only letters
        if (!l_parts[1].matches("[a-zA-Z]+")) {
            System.out.println("\n'CountryID' must be a string.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'blockade' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_blockade_command_valid(String p_command) {
        // Check if command starts with 'blockade '
        if (!p_command.startsWith("blockade ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains exactly 2 arguments
        if (l_parts.length != 2) {
            System.out.println("\n'blockade' command must have 2 arguments.");
            return false;
        }

        // Check if countryID is a string containing only letters
        if (!l_parts[1].matches("[a-zA-Z]+")) {
            System.out.println("\n'CountryID' must be a string.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'airlift' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_airlift_command_valid(String p_command) {
        // Check if command starts with 'airlift '
        if (!p_command.startsWith("airlift ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains exactly 4 arguments
        if (l_parts.length != 4) {
            System.out.println("\n'airlift' command must have 4 arguments.");
            return false;
        }

        // Check if sourceCountryID is a string containing only letters
        if (!l_parts[1].matches("[a-zA-Z]+")) {
            System.out.println("\n'sourceCountryID' must be a string.");
            return false;
        }

        // Check if targetCountryID is a string containing only letters
        if (!l_parts[2].matches("[a-zA-Z]+")) {
            System.out.println("\n'targetCountryID' must be a string.");
            return false;
        }

        // Check if numArmies is a number
        try {
            Integer.valueOf(l_parts[3]);
        } catch (NumberFormatException e) {
            System.out.println("\n'numArmies' must be a number.");
            return false;
        }

        return true;
    }

    /**
     * Validates whether the given 'negotiate' command is valid.
     *
     * @param p_command The user command string to validate
     * @return {@code true} if the command is valid, otherwise {@code false}
     */
    public static boolean is_negotiate_command_valid(String p_command) {
        // Check if command starts with 'negotiate '
        if (!p_command.startsWith("negotiate ")) {
            return false;
        }

        // Split command into an array of strings
        String[] l_parts = p_command.trim().split(" ");

        // Check if the command contains exactly 2 arguments
        if (l_parts.length != 2) {
            System.out.println("\n'negotiate' command must have 2 arguments.");
            return false;
        }

        // Check if playerID is a string containing only letters
        if (!l_parts[1].matches("[a-zA-Z]+")) {
            System.out.println("\n'playerID' must be a string.");
            return false;
        }
        return true;
    }
}