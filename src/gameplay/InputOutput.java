package gameplay;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InputOutput {
    /*
    TODO:
        Take input Via scanner, validate and return
        Different possible inputs depending on the stage of the game with varying argsLabled
        Take stage as parameter and validate
        Currently only 3 possible inputs refer grading sheet
        2 for startup and 1 during issue_order
     */

    public static void run_map_editor() {
        String l_command;

        do {
            System.out.println("\n***********************");
            System.out.println("*     Map Editor      *");
            System.out.println("***********************");

            Scanner l_scanner = new Scanner(System.in);

            System.out.println("\nEnter your command (enter 'return' to go back to main menu): ");
            l_command = l_scanner.nextLine();

            // Validate map commands given by the user, then perform actions
            if (is_editcontinent_command_valid(l_command)) {
                Command command= new Command(l_command);
                System.out.println("Executing editcontinent command");

            } else if (is_editcountry_command_valid(l_command)) {
                Command command= new Command(l_command);
                System.out.println("Executing editcountry command");

            } else if (is_editneighbor_command_valid(l_command)) {
                Command command= new Command(l_command);
                System.out.println("Executing editneighbor command");

            } else if (is_showmap_command_valid(l_command)) {
                System.out.println("Executing showmap command");

            } else if (is_savemap_command_valid(l_command)) {
                Command command= new Command(l_command);
                System.out.println("Executing savemap command");

            } else if (is_editmap_command_valid(l_command)) {
                Command command= new Command(l_command);
                System.out.println("Executing editmap command");

            } else if (is_validatemap_command_valid(l_command)) {
                System.out.println("Executing validatemap command");

            } else if (l_command.equals("return")) {
                System.out.println("\nReturn to main menu.");

            } else {
                System.out.println("\nInvalid command. Please try again.");
            }
        } while(!l_command.equals("return"));
    }

    public static Command get_user_command() {
        Scanner l_scanner = new Scanner(System.in);

        System.out.println("Enter command: ");
        String l_command = l_scanner.nextLine();

        // Validate all commands, then perform actions
        if (is_editcontinent_command_valid(l_command)) {
            System.out.println("Executing editcontinent command");
            return new Command(l_command);

        } else if (is_editcountry_command_valid(l_command)) {
            System.out.println("Executing editcountry command");
            return new Command(l_command);

        } else if (is_editneighbor_command_valid(l_command)) {
            System.out.println("Executing editneighbor command");
            return new Command(l_command);

        } else if (is_showmap_command_valid(l_command)) {
            System.out.println("Executing showmap command");
            return new Command(l_command);

        } else if (is_savemap_command_valid(l_command)) {
            System.out.println("Executing savemap command");
            return new Command(l_command);

        } else if (is_editmap_command_valid(l_command)) {
            System.out.println("Executing editmap command");
            return new Command(l_command);

        } else if (is_validatemap_command_valid(l_command)) {
            System.out.println("Executing validatemap command");
            return new Command(l_command);

        } else if (is_loadmap_command_valid(l_command)) {
            System.out.println("Executing loadmap command");
            return new Command(l_command);

        } else if (is_gameplayer_command_valid(l_command)) {
            System.out.println("Executing gameplayer command");
            return new Command(l_command);

        } else if (is_assigncountries_command_valid(l_command)) {
            System.out.println("Executing assigncountries command");
            return new Command(l_command);

        } else if (is_deploy_command_valid(l_command)) {
            System.out.println("Executing deploy command");
            return new Command(l_command);

        } else {
            System.out.println("Command does not exist. Please try again.");
        }
        return null;
    }

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

    public static boolean is_showmap_command_valid(String p_command) {
        // Check if command equals to 'showmap'
        return p_command.trim().equals("showmap");
    }

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

        // Get file name
        String l_filename = l_parts[1];

        // Check whether file name ends with .map extension
        if (!l_filename.endsWith(".map")) {
            System.out.println("\nFilename must have .map extension.");
            return false;
        }

        return true;
    }

    public static boolean is_editmap_command_valid(String p_command) {
        // Check if command starts with 'editmap '
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

        // Get file name
        String l_filename = l_parts[1];

        // Check whether file name ends with .map extension
        if (!l_filename.endsWith(".map")) {
            System.out.println("\nFilename must have .map extension.");
            return false;
        }

        return true;
    }

    public static boolean is_validatemap_command_valid(String p_command) {
        // Check if command equals to 'showmap'
        return p_command.trim().equals("validatemap");
    }

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

        // Get file name
        String l_filename = l_parts[1];

        // Check whether file name ends with .map extension
        if (!l_filename.endsWith(".map")) {
            System.out.println("\nFilename must have .map extension.");
            return false;
        }

        return true;
    }

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

    public static boolean is_assigncountries_command_valid(String p_command) {
        // Check if command equals to 'assigncountries'
        return p_command.trim().equals("assigncountries");
    }

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
}
