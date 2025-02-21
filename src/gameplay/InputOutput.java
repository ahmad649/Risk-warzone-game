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

    public static void start_app() {
        int l_input = 0;

        do {
            System.out.println("\n***********************");
            System.out.println("* Welcome to Warzone *");
            System.out.println("***********************");

            Scanner l_scanner = new Scanner(System.in);

            System.out.println("\nMain Menu:");
            System.out.println("1. Map Editor");
            System.out.println("2. Play Warzone Game");
            System.out.println("3. Exit\n");
            System.out.println("Enter your choice: ");

            // Check whether user input is an integer or not
            if (l_scanner.hasNextInt()) {
                l_input = l_scanner.nextInt();

                // If user provides available options
                switch (l_input) {
                    case 1:
                        InputOutput.run_map_editor();
                        break;
                    case 2:
                        GameLoop gameLoop = new GameLoop();
                        gameLoop.startup();
                        break;
                    case 3:
                        System.out.println("\nSuccessfully exit the game.");
                        break;
                }
            } else {
                System.out.println("\nInvalid input. Please try again.\n");
            }
        } while(l_input != 3);
    }

    public static void run_map_editor() {
        String l_command;

        do {
            System.out.println("\n***********************");
            System.out.println("*     Map Editor      *");
            System.out.println("***********************");

            Scanner l_scanner = new Scanner(System.in);

            System.out.println("\nEnter your command (enter 'return' to go back to main menu): ");
            l_command = l_scanner.nextLine();

            if (is_editcontinent_command_valid(l_command)) {
                HashMap<String, List<String>> l_arguments = Command.parse_editcontinent_command(l_command);
                System.out.println(l_arguments);
                System.out.println("Executing editcontinent command");

            } else if (is_editcountry_command_valid(l_command)) {
                HashMap<String, List<String>> l_arguments = Command.parse_editcountry_command(l_command);
                System.out.println(l_arguments);
                System.out.println("Executing editcountry command");

            } else if (is_editneighbor_command_valid(l_command)) {
                HashMap<String, List<String>> l_arguments = Command.parse_editneighbor_command(l_command);
                System.out.println(l_arguments);
                System.out.println("Executing editneighbor command");

            } else if (is_showmap_command_valid(l_command)) {
                System.out.println("Executing showmap command");

            } else if (is_savemap_command_valid(l_command)) {
                String filename = Command.parse_savemap_command(l_command);
                System.out.println("Executing savemap command");

            } else if (is_editmap_command_valid(l_command)) {
                String filename = Command.parse_editmap_command(l_command);
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
        if (!p_command.startsWith("editcontinent ")) {
            return false;
        }

        String[] l_parts = p_command.trim().split(" ");

        // Check duplicates in flag argument
        boolean l_addFlag = false, l_removeFlag = false;

        // Iterate over the arguments
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    if (l_addFlag) {
                        System.out.println("\n'editcontinent' command cannot have multiple '-add' flags.");
                        return false;
                    }

                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-add' flag must be followed by 'continentID' and 'continentValue'.");
                        return false;
                    }

                    if (l_parts[l_i + 2].isEmpty()) {
                        System.out.println("\n'continentValue' cannot be empty.");
                        return false;
                    }
                    l_addFlag = true;
                    l_i += 2;
                    break;
                case "-remove":
                    if (l_removeFlag) {
                        System.out.println("\n'editcontinent' command cannot have multiple -remove flags.");
                        return false;
                    }
                    if (l_i + 1 >= l_parts.length) {
                        System.out.println("\n'-remove' flag must be followed by 'continentID'.");
                        return false;
                    }
                    l_removeFlag = true;
                    l_i += 1;
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'editcontinent' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if any flag arguments provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'editcontinent' command must have '-add' or '-remove' flags.");
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
                    if (l_addFlag) {
                        System.out.println("\n'editcountry' command cannot have multiple '-add' flags.");
                        return false;
                    }
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-add' flag must be followed by 'countryID' and 'continentID'.");
                        return false;
                    }
                    l_addFlag = true;
                    l_i += 2;
                    break;
                case "-remove":
                    if (l_removeFlag) {
                        System.out.println("\n'editcountry' command cannot have multiple '-remove' flags.");
                        return false;
                    }
                    if (l_i + 1 >= l_parts.length) {
                        System.out.println("\n'-remove' flag must be followed by 'countryID'.");
                        return false;
                    }
                    l_removeFlag = true;
                    l_i += 1;
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'editcountry' command: " + l_parts[l_i]);
                    return false;
            }
        }

        // Check if any flag arguments provided
        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'editcountry' command must have '-add' or '-remove' flags.");
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
                        System.out.println("\n'editneighbor' command cannot have multiple '-add' flags.");
                        return false;
                    }
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-add' flag must be followed by 'countryID' and 'neighborCountryID'.");
                        return false;
                    }

                    l_addFlag = true;
                    l_i += 2;
                    break;
                case "-remove":
                    if (l_removeFlag) {
                        System.out.println("\n'editneighbor' command cannot have multiple '-remove' flags.");
                        return false;
                    }
                    if (l_i + 2 >= l_parts.length) {
                        System.out.println("\n'-remove' flag must be followed by 'countryID' and neighborCountryID.");
                        return false;
                    }

                    l_removeFlag = true;
                    l_i += 2;
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'editneighbor' command: " + l_parts[l_i]);
                    return false;
            }
        }

        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'editneighbor' command must have '-add' or '-remove' flags.");
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

        String[] l_parts = p_command.trim().split(" ");
        if (l_parts.length != 2) {
            System.out.println("\n'savemap' command must have 1 argument.");
            return false;
        }

        String l_filename = l_parts[1];
        // Check filename
        if (!l_filename.endsWith(".map")) {
            System.out.println("\nFilename must have .map extension.");
            return false;
        }

        return true;
    }

    public static boolean is_editmap_command_valid(String p_command) {
        if (!p_command.startsWith("editmap ")) {
            return false;
        }

        String[] l_parts = p_command.trim().split(" ");

        if (l_parts.length != 2) {
            System.out.println("\n'editmap' command must have 1 argument.");
            return false;
        }

        String l_filename = l_parts[1];
        // Check filename
        if (!l_filename.endsWith(".map")) {
            System.out.println("\nFilename must have .map extension.");
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

        String[] l_parts = p_command.trim().split(" ");
        if (l_parts.length != 2) {
            System.out.println("\n'loadmap' command must have 1 argument.");
            return false;
        }

        String l_filename = l_parts[1];
        // Check filename
        if (!l_filename.endsWith(".map")) {
            System.out.println("\nFilename must have .map extension.");
            return false;
        }

        return true;
    }

    public static boolean is_gameplayer_command_valid(String p_command) {
        if (!p_command.startsWith("gameplayer ")) {
            return false;
        }

        String[] l_parts = p_command.trim().split(" ");

        boolean l_addFlag = false, l_removeFlag = false;
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    if (l_i + 1 >= l_parts.length || l_parts[l_i + 1].isEmpty()) {
                        System.out.println("\n'-add' flag must be followed by playerName");
                        return false;
                    }
                    l_addFlag = true;
                    l_i += 1;
                    break;
                case "-remove":
                    if (l_i + 1 >= l_parts.length || l_parts[l_i + 1].isEmpty()) {
                        System.out.println("\n'-remove' flag must be followed by playerName");
                        return false;
                    }
                    l_removeFlag = true;
                    l_i += 1;
                    break;
                default:
                    System.out.println("\nInvalid arguments in 'gameplayer' command: " + l_parts[l_i]);
                    return false;
            }
        }

        if (!l_addFlag && !l_removeFlag) {
            System.out.println("\n'gameplayer' command must have '-add' or '-remove' flags.");
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

        String[] l_parts = p_command.trim().split(" ");

        if (l_parts.length != 3) {
            System.out.println("\n'deploy' command must be followed by 'countryID' and 'num'.");
            return false;
        }

        // Check if 'num' are positive number
        try {
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
