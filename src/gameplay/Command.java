package gameplay;

import java.util.*;

/**
 * Command class in charge of managing the different commands composing the game that the user prompts to the program
 * Integer numArgs as the number of arguments contained in a command
 * String commandType as an identifier for the nature of the command
 * HashMap argsLabled as a data structure to keep track of the command types and their iterations through the program
 */
public class Command{
    Integer numArgs;
    String commandType;
    //Used when args are in -argname actual_arg format
    HashMap<String,String> argsLabled;
    ArrayList<String> argArr;

    /**
     * Command constructor
     * @param input String designated for the input made by the user
     */
    //Constructor
    public Command(String input){
        String[] parts = input.split(" ");
        commandType = parts[0];
        argsLabled = new HashMap<>();
        argArr = new ArrayList<>();
        for (int i = 1;i< parts.length;i+=1){
            if (parts[i].charAt(0)=='-'){
                argsLabled.put(parts[i],parts[i+1]);
                i+=1;
            } else {
                argArr.add(parts[i]);
            }
        }
        numArgs = argArr.size();
    }

    /**
     * HashMap return type parse_editcontinent_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return A HashMap with String keys and List<String> values containing assignations for commands (add or remove continents) for identifying
     * Makes the continent control smoother
     */
    public static HashMap<String, List<String>> parse_editcontinent_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Use hashmap to group arguments
        HashMap<String , List<String>> l_arguments = new HashMap<>();
        for (int l_i = 0; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Add 'continentID' and 'continentValue' to hashmap
                    l_arguments.put("add", new ArrayList<>(Arrays.asList(l_parts[l_i + 1], l_parts[l_i + 2])));
                    break;
                case "-remove":
                    // Add 'continentID' to hashmap
                    l_arguments.put("remove", new ArrayList<>(Collections.singletonList(l_parts[l_i + 1])));
            }
        }

        return l_arguments;
    }

    /**
     * HashMap return type parse_editcountry_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return A HashMap with String keys and List<String> values containing assignations for commands (add or remove countries) for identifying
     * Makes the country control smoother
     */
    public static HashMap<String, List<String>> parse_editcountry_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Use hashmap to group arguments
        HashMap<String , List<String>> l_arguments = new HashMap<>();
        for (int l_i = 0; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Add 'countryID' and 'continentID' to hashmap
                    l_arguments.put("add", new ArrayList<>(Arrays.asList(l_parts[l_i + 1], l_parts[l_i + 2])));
                    break;
                case "-remove":
                    // Add 'countryID' to hashmap
                    l_arguments.put("remove", new ArrayList<>(Collections.singletonList(l_parts[l_i + 1])));
            }
        }

        return l_arguments;
    }

    /**
     * HashMap return type parse_editneighbor_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return A HashMap with String keys and List<String> values containing assignations for commands (add or remove adjacent countries) for identifying
     * Makes the adjacent country control smoother
     */
    public static HashMap<String, List<String>> parse_editneighbor_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Use hashmap to group arguments
        HashMap<String , List<String>> l_arguments = new HashMap<>();
        for (int l_i = 0; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Add 'countryID' and 'neighborCountryID' to hashmap
                    l_arguments.put("add", new ArrayList<>(Arrays.asList(l_parts[l_i + 1], l_parts[l_i + 2])));
                    break;
                case "-remove":
                    // Add 'countryID' and 'neighborCountryID' to hashmap
                    l_arguments.put("remove", new ArrayList<>(Arrays.asList(l_parts[l_i + 1], l_parts[l_i + 2])));
            }
        }

        return l_arguments;
    }

    /**
     * String type parse_savemap_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return String value for identifying the savemap command and its objective
     */
    public static String parse_savemap_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Return file name
        return l_parts[1];
    }

    /**
     * String type parse_editmap_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return String value for identifying the editmap command and its objective
     */
    public static String parse_editmap_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Return file name
        return l_parts[1];
    }

    /**
     * String type parse_loadmap_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return String value for identifying the loadmap command and its objective
     */
    public static String parse_loadmap_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Return file name
        return l_parts[1];
    }

    /**
     * HashMap return type parse_gameplayer_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return A HashMap with String keys and List<String> values containing assignations for commands (add or remove players) for identifying
     * Makes the player control smoother
     */
    public static HashMap<String, List<String>> parse_gameplayer_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Use hashmap to group arguments
        HashMap<String , List<String>> l_arguments = new HashMap<>();
        for (int l_i = 0; l_i < l_parts.length; l_i++) {
            switch (l_parts[l_i]) {
                case "-add":
                    // Create 'add' as key in hashmap if it doesn't exist yet
                    if (!l_arguments.containsKey("add")){
                        l_arguments.put("add", new ArrayList<>());
                    }
                    // Add 'playerName' to list with the key 'add'
                    l_arguments.get("add").add(l_parts[l_i + 1]);
                    break;
                case "-remove":
                    // Create 'remove' as key in hashmap if it doesn't exist yet
                    if (!l_arguments.containsKey("remove")){
                        l_arguments.put("remove", new ArrayList<>());
                    }
                    // add 'playerName' to list with the key 'remove'
                    l_arguments.get("remove").add(l_parts[l_i + 1]);
                    break;
            }
        }

        return l_arguments;
    }

    /**
     * HashMap return type parse_deploy_command
     * @param p_command String designated for the command generated after being prompted by the user and verified in InputOuput class
     * @return A HashMap with String keys and List<String> values containing the countries information to perform the game commands
     */
    public static HashMap<String, String> parse_deploy_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Create hashmap to store 'countryID' and 'num'
        HashMap<String, String> l_arguments = new HashMap<>();
        l_arguments.put("countryID", l_parts[1]);
        l_arguments.put("num", l_parts[2]);

        return l_arguments;
    }
}
