package com.maps;

import java.io.*;
import java.util.*;
import com.model.Continent;
import com.model.Country;

/**
 * MapWriter handles saving the map to a file using main.com.model classes.
 */
public class MapWriter {
    private MapReader d_mapReader;

    /**
     * Constructor to associate with MapReader.
     * @param p_mapReader The MapReader instance to retrieve the loaded map data.
     */
    public MapWriter(MapReader p_mapReader) {
        this.d_mapReader = p_mapReader;
    }

    /**
     * Saves the currently loaded map to a file.
     * @param p_filename Name of the file to save.
     * @return true if saving is successful, false otherwise.
     */
    public boolean saveMap(String p_filename) {
        // Retrieve the currently loaded map data
        Map<String, Continent> l_continents = d_mapReader.getContinentsMap();
        Map<String, Country> l_countries = d_mapReader.getCountriesMap();
    
        if (l_continents.isEmpty() || l_countries.isEmpty()) {
            System.err.println("Error: No map data loaded. Cannot save.");
            return false;
        }
    
        // Construct the file path
        String l_mapFilePath = "resources/main.com.maps/" + p_filename + ".txt";
        File l_mapFile = new File(l_mapFilePath);
    
        // Check if the file already exists
        if (l_mapFile.exists()) {
            System.out.println("Error: A map with the name '" + p_filename + "' already exists. Please provide a unique name.");
            return false;
        }
    
        // Attempt to create the directory if it does not exist
        if (l_mapFile.getParentFile() != null) {
            l_mapFile.getParentFile().mkdirs();
        }
    
        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_mapFile))) {
            // Write map header
            l_writer.write("[Map]\n");
            l_writer.write("author=Custom World\n");
            l_writer.write("image=custom_world.bmp\n");
            l_writer.write("wrap=no\n");
            l_writer.write("scroll=horizontal\n");
            l_writer.write("warn=yes\n\n");
    
            // Write continents section
            l_writer.write("[Continents]\n");
    
            // Iterate over all continents in the order they are present in the mapReader
            for (Continent l_continent : l_continents.values()) {
                l_writer.write(l_continent.getName() + "=" + l_continent.getBonus() + "\n");
            }
            l_writer.write("\n");
    
            // Write territories section
            l_writer.write("[Territories]\n");
    
            // Iterate through countries and write their territory data
            for (Country l_country : l_countries.values()) {
                String continentName = l_country.getContinent().getName();
                List<String> l_neighborNames = new ArrayList<>();
                for (Country l_neighbor : l_country.getNeighbors()) {
                    l_neighborNames.add(l_neighbor.getName());
                }
    
                // Write the country data with coordinates and neighbors
                int l_x = 0;
                int l_y = 0;
    
                l_writer.write(String.format("%s,%d,%d,%s,%s\n", l_country.getName(), l_x, l_y, continentName, String.join(",", l_neighborNames)));
            }
    
            System.out.println("Map saved successfully to " + l_mapFilePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving map: " + e.getMessage());
            return false;
        }
    }
}
