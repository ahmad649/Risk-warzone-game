package com.adapter;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gameplay.Parsing;
import com.maps.MapReader;
import com.model.Continent;
import com.model.Country;

/**
 * Adapter for loading and saving Conquest maps.
 * This class implements the MapFileAdapter interface and handles the
 * loading and saving of Conquest format maps.
 */
public class ConquestMapAdapter implements MapAdapter {

    private MapReader d_mapReader;
    /**
     * A map of continents loaded from the map file.
     */
    public Map<String, Continent> d_continents;
    /**
     * A map of countries loaded from the map file.
     */
    public Map<String, Country> d_countries;
    /**
     * Instantiate a new Preload state.
     *
     * @param p_mapReader the MapReader instance used to load and parse map data
     */
    public ConquestMapAdapter(MapReader p_mapReader) {
        this.d_mapReader = p_mapReader;
        d_continents = d_mapReader.getContinentsMap();
        d_countries = d_mapReader.getCountriesMap();
    }

    /**
     * Loads a Conquest map from the specified file.
     *
     * @param p_filename The name of the file to load.
     * @return true if successfully loaded, false otherwise.
     */
    @Override
    public boolean loadMap(String p_filename) {
        // Construct the full file path
        String l_mapFilePath = "src/main/resources/maps/" + p_filename;
        File l_mapFile = new File(l_mapFilePath);
    
        // If the file does not exist, create a new one
        if (!l_mapFile.exists()) {
            try {
                if (l_mapFile.getParentFile() != null) {
                    l_mapFile.getParentFile().mkdirs(); // Ensure the directory exists
                }
                if (l_mapFile.createNewFile()) {
                    System.out.println("Map file did not exist, so a new map file was created: " + l_mapFilePath);
                    return true; // Return true since the file was created
                } else {
                    System.err.println("Failed to create the new map file.");
                    return false;
                }
            } catch (IOException e) {
                System.err.println("Error creating new map file: " + e.getMessage());
                return false;
            }
        }
    
        // Read from the existing file
        try (BufferedReader l_reader = new BufferedReader(new FileReader(l_mapFile))) {
            String l_line;
            boolean l_readingContinents = false, l_readingTerritories = false, l_readingMetaData = false;
    
            while ((l_line = l_reader.readLine()) != null) {
                l_line = l_line.trim();
                if (l_line.isEmpty()) continue;
    
                if (l_line.equals("[Continents]")) {
                    l_readingContinents = true;
                    l_readingTerritories = false;
                    l_readingMetaData = false;
                    continue;
                } else if (l_line.equals("[Territories]")) {
                    l_readingContinents = false;
                    l_readingTerritories = true;
                    l_readingMetaData = false;
                    continue;
                } else if(l_line.equals("[Map]")){
                    d_mapReader.d_metaData.add(l_line);
                    l_readingMetaData = true;
                    l_readingContinents = false;
                    l_readingTerritories = false;
                    continue;
                }
    
                if (l_readingContinents) {
                    String[] l_parts = l_line.split("=");
                    if (l_parts.length == 2) {
                        String l_name = l_parts[0];
                        l_name = l_name.replaceAll(" ", "_");
                        int l_bonus = Integer.parseInt(l_parts[1]);
                        int l_continentId = d_mapReader.getContinentIdCounter();
                        Continent l_continent = new Continent(l_continentId++, l_name, l_bonus);
                        d_mapReader.setContinentIdCounter(l_continentId);
                        d_continents.put(l_name, l_continent);
                    }
                } else if (l_readingTerritories) {
                    String[] l_parts = l_line.split(",");
                    if (l_parts.length >= 4) {
                        String l_countryName = l_parts[0];
                        l_countryName = l_countryName.replaceAll(" ", "_");
                        String l_x = l_parts[1];
                        String l_y = l_parts[2];
                        String l_continentName = l_parts[3];
                        l_continentName = l_continentName.replaceAll(" ", "_");
    
                        Continent l_continent = d_continents.get(l_continentName);
                        if (l_continent == null) {
                            System.err.println("Error: Continent " + l_continentName + " not found for country " + l_countryName);
                            return false;
                        }
    
                        // Create or update the country
                        Country l_country = d_countries.get(l_countryName);
                        if (l_country == null) {
                            // Create a new country if it doesn't exist
                            int l_countryID = d_mapReader.getCountryIdCounter();
                            l_country = new Country(l_countryID++, l_countryName, l_continent);
                            d_mapReader.setCountryIdCounter(l_countryID);
                            d_countries.put(l_countryName, l_country);
                            l_continent.addCountry(l_country); // Add the country to its continent's country list
                        }else{
                            l_country.setContinent(l_continent);
                            l_continent.addCountry(l_country);
                        }

                        l_country.setXCoordinate(l_x);  // Set X coordinate
                        l_country.setYCoordinate(l_y);  // Set Y coordinate
    
                        // Process neighbors
                        for (int i = 4; i < l_parts.length; i++) {
                            String l_neighborName = l_parts[i];
                            l_neighborName = l_neighborName.replaceAll(" ", "_");
                            Country l_neighbor = d_countries.get(l_neighborName);
    
                            if (l_neighbor == null) {
                                // Create new neighbor country if it doesn't exist
                                int l_countryID = d_mapReader.getCountryIdCounter();
                                l_neighbor = new Country(l_countryID++, l_neighborName, null);
                                d_mapReader.setCountryIdCounter(l_countryID);
                                d_countries.put(l_neighborName, l_neighbor);
                            }
    
                            // Add to each other's neighbors list (no duplicates)
                            l_country.addNeighbor(l_neighbor);
                            l_neighbor.addNeighbor(l_country);
                        }
                    }
                } else if(l_readingMetaData){
                    d_mapReader.d_metaData.add(l_line);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves the Domination map to the specified file.
     *
     * @param l_parsing the Parsing object that contains the filename as its first argument
     * @return true if saving is successful, false otherwise
     */
    @Override
    public boolean saveMap(Parsing l_parsing) {
        String p_filename = l_parsing.getArgArr().getFirst();
        // Retrieve the currently loaded map data
        Map<String, Continent> l_continents = d_mapReader.getContinentsMap();
        Map<String, Country> l_countries = d_mapReader.getCountriesMap();

        if (l_continents.isEmpty() || l_countries.isEmpty()) {
            System.err.println("Error: No map data loaded. Cannot save.");
            return false;
        }

        // Construct the file path
        String l_mapFilePath = "src/main/resources/maps/" + p_filename;
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
            while(!d_mapReader.d_metaData.isEmpty()){
                l_writer.write(d_mapReader.d_metaData.get(0)+"\n");
                d_mapReader.d_metaData.remove(0);
            }
            l_writer.write("\n\n");
            // Write continents section
            l_writer.write("[Continents]\n");

            // Iterate over all continents in the order they are present in the mapReader
            for (Continent l_continent : l_continents.values()) {
                l_writer.write(l_continent.getName() + "=" + l_continent.getBonus() + "\n");
            }
            l_writer.write("\n\n");
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
                String l_x = l_country.getXCoordinate();
                String l_y = l_country.getYCoordinate();

                l_writer.write(String.format("%s,%s,%s,%s,%s\n", l_country.getName(), l_x, l_y, continentName, String.join(",", l_neighborNames)));
            }

            System.out.println("Map saved successfully to " + l_mapFilePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving map: " + e.getMessage());
            return false;
        }
    }


}
