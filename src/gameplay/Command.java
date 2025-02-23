package gameplay;

import java.util.*;

public class Command{
    Integer numArgs;
    String commandType;
    //Used when args are in -argname actual_arg format
    HashMap<String,String> argsLabled;
    ArrayList<String> argArr;

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

    public static String parse_savemap_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Return file name
        return l_parts[1];
    }

    public static String parse_editmap_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Return file name
        return l_parts[1];
    }

    public static String parse_loadmap_command(String p_command){
        // Split command into an array of strings
        String[] l_parts = p_command.split(" ");

        // Return file name
        return l_parts[1];
    }

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
