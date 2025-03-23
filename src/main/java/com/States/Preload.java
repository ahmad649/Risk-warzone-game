package com.States;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;
import com.maps.MapReader;
import com.model.Continent;
import com.model.Country;

public class Preload implements Phase{
    
    private MapReader d_mapReader;
    public Map<String, Continent> d_continents;
    public Map<String, Country> d_countries;

    /**
     * Instantiate a new Pre load state.
     *
     * @param p_mapReader the map reader
     */
    public Preload(MapReader p_mapReader) {
        this.d_mapReader = p_mapReader;
        d_continents = d_mapReader.getContinentsMap();
        d_countries = d_mapReader.getCountriesMap();
    }




    /**
     * Loads a map file into memory.
     *
     * @param p_filename Name of the file to load.
     * @return true if successfully loaded, false otherwise.
     */
    public boolean loadMap(String p_filename) {
        // Construct the full file path
        String l_mapFilePath = "src/main/resources/maps/" + p_filename + ".txt";
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
            boolean l_readingContinents = false, l_readingTerritories = false;
    
            while ((l_line = l_reader.readLine()) != null) {
                l_line = l_line.trim();
                if (l_line.isEmpty()) continue;
    
                if (l_line.equals("[Continents]")) {
                    l_readingContinents = true;
                    l_readingTerritories = false;
                    continue;
                } else if (l_line.equals("[Territories]")) {
                    l_readingContinents = false;
                    l_readingTerritories = true;
                    continue;
                }
    
                if (l_readingContinents) {
                    String[] l_parts = l_line.split("=");
                    if (l_parts.length == 2) {
                        String l_name = l_parts[0];
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
                        String l_continentName = l_parts[3];
    
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
    
                        // Process neighbors
                        for (int i = 4; i < l_parts.length; i++) {
                            String l_neighborName = l_parts[i];
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
                }
            }
            boolean l_b = validateMap();
            System.out.println("Map is loaded successfully!");
            return true;
        } catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
            return false;
        }
    }

        /**
     * Validates the map structure.
     *
     * @return true if valid, false otherwise.
     */
    public boolean validateMap() {
        if (d_continents.isEmpty() || d_countries.isEmpty()) {
            System.out.println("Map validation failed: No continents or countries found.");
            return false;
        }

        // Check if each continent has at least one country
        for (Continent l_continent : d_continents.values()) {
            if (l_continent.getCountries().isEmpty()) {
                System.out.println("Validation failed: Continent " + l_continent.getName() + " has no countries.");
                return false;
            }
        }

        // Ensure all countries have valid neighbors
        for (Country l_country : d_countries.values()) {
            for (Country l_neighbor : l_country.getNeighbors()) {
                if (!d_countries.containsKey(l_neighbor.getName())) {
                    System.out.println("Validation failed: " + l_country.getName() + " has an invalid neighbor " + l_neighbor.getName());
                    return false;
                }
            }
        }

        System.out.println("Map is valid!");
        return true;
    }


    @Override
    public void addGamePlayer(GameEngine engine, Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }




    @Override
    public void showMap() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }




    @Override
    public boolean saveMap(String p_filename) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
        return false;
    }




    @Override
    public void addContinent(String p_name, int p_bonusValue) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }




    @Override
    public void removeContinent(String p_name) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }




    @Override
    public void addCountry(String p_name, String p_continentName) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }




    @Override
    public void removeCountry(String p_name) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }




    @Override
    public void addNeighbor(String p_countryName, String p_neighborName) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }




    @Override
    public void removeNeighbor(String p_countryName, String p_neighborName) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in preload phase!");
    }
}
