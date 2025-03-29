package com.gameplay;

import java.util.*;

/**
 * {@code Parsing} class manages the parsing of user commands.
 */
public class Parsing {
    /**
     * The number of arguments.
     */
    Integer d_numArgs;
    /**
     * The command type.
     */
    public String d_commandType;
    /**
     * The arguments in a form of hashmap.
     */
    public HashMap<String, List<String>> d_argsLabeled;
    /**
     * The arguments in a form of array.
     */
    public ArrayList<String> d_argArr;
    /**
     * The full command.
     */
    String d_fullCommand;

    /**
     * A constructor to initialize {@code Parsing} object which is used to parse user commands.
     *
     * @param p_input The user command string that has been parsed, either in a form of {@code HashMap<String, List<String>>} or {@code ArrayList<String>}
     */
    public Parsing(String p_input) {
        this.d_fullCommand = p_input;
        // Split the string
        String[] l_parts = p_input.split(" ");

        d_commandType = l_parts[0];
        d_argsLabeled = new HashMap<>();
        d_argArr = new ArrayList<>();
        // Iterate through the split commands
        for (int l_i = 1; l_i < l_parts.length; l_i++) {
            // Check for flag
            if (l_parts[l_i].startsWith("-")) {
                String l_flag = l_parts[l_i];
                List<String> l_values = new ArrayList<>();

                // Skip flag
                int l_j = l_i + 1;

                // Ensure the current index doesn't exceed the length of split commands and it doesn't start with '-'
                while (l_j < l_parts.length && !l_parts[l_j].startsWith("-")) {
                    // Add arguments to a list
                    l_values.add(l_parts[l_j]);
                    l_j++;
                }

                // Add to key-value pairs to hashmap
                l_values.addAll(d_argsLabeled.getOrDefault(l_flag,new ArrayList<>()));
                d_argsLabeled.put(l_flag, l_values);
                l_i = l_j - 1; // Move i to the end of the processed arguments
            } else {
                d_argArr.add(l_parts[l_i]);
            }
        }
        d_numArgs = d_argArr.size();
    }

    /**
     * Get labeled arguments from user commands in a form of {@code HashMap<String, List<String>>}
     *
     * @return a {@code HashMap<String, List<String>>} containing the labeled arguments.
     */
    public HashMap<String, List<String>> getArgsLabeled() {
        return d_argsLabeled;
    }

    /**
     * Get arguments from user commands in a form of {@code ArrayList<String>}
     *
     * @return an {@code ArrayList<String>} containing the command arguments.
     */
    public ArrayList<String> getArgArr() {
        return d_argArr;
    }

    /**
     * Get full command.
     *
     * @return the full command
     */
    public String getFullCommand() {
        return d_fullCommand;
    }
}
