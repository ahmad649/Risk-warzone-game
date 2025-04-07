package com.adapter;

import com.gameplay.Parsing;

/**
 * This interface defines the contract for loading and saving maps in different file formats.
 * It is used to adapt the map loading and saving functionality for various map formats like
 * "domination" and "conquest" during the PreloadPhase and PostloadPhase of the game.
 */
public interface MapAdapter {

    /**
     * Loads a map file into memory.
     *
     * @param p_filename The name of the file to load.`
     * @return true if successfully loaded, false otherwise.
     */
    public boolean loadMap(String p_filename);

    /**
     * Saves the currently loaded map to a file.
     *
     * @param p_parsing The Parsing object that contains the filename as its first argument.
     * @return true if saving is successful, false otherwise.
     */
    public boolean saveMap(Parsing p_parsing);
}
