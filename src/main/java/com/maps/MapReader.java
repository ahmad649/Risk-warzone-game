package com.maps;

import com.model.Continent;
import com.model.Country;
import java.io.*;
import java.util.*;

/**
 * MapReader class to load and validate domination map files.
 */
public class MapReader {
    private Map<String, Continent> d_continents;
    private Map<String, Country> d_countries;
    private int d_continentIdCounter = 1;
    private int d_countryIdCounter = 1;

    /**
     * Constructor initializes data structures.
     */
    public MapReader() {
        d_continents = new HashMap<>();
        d_countries = new HashMap<>();
    }

    /**
     * Loads a map file into memory.
     *
     * @param p_filename Name of the file to load.
     * @return true if successfully loaded, false otherwise.
     */
    public boolean loadMap(String p_filename) {
        // Construct the full file path
        String l_mapFilePath = "resources/main.com.maps/" + p_filename + ".txt";
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
                        Continent l_continent = new Continent(d_continentIdCounter++, l_name, l_bonus);
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
                            l_country = new Country(d_countryIdCounter++, l_countryName, l_continent);
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
                                l_neighbor = new Country(d_countryIdCounter++, l_neighborName, null);
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
            return true;
        } catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
            return false;
        }
    }


    /**
     * Displays the loaded map.
     */
    public void showMap() {
        // Display continents
        System.out.println("--------------------Map---------------------------");
        System.out.println("Continents:");
        for (Continent l_continent : d_continents.values()) {
            System.out.println("--------------------------------------------------");
            System.out.println(l_continent.getName() + " (ID: " + l_continent.getId() + ", Bonus: " + l_continent.getBonus() + ")");
            System.out.println("Countries in this continent:");
    
            // Display countries in the continent
            for (Country l_country : l_continent.getCountries()) {
                System.out.print("    " + l_country.getName() + " (ID: " + l_country.getId() + ") -> Neighbors: ");
                
                List<String> l_neighborNames = new ArrayList<>();
                for (Country l_neighbor : l_country.getNeighbors()) {
                    l_neighborNames.add(l_neighbor.getName());
                }
                
                // Print neighbors
                System.out.println(String.join(", ", l_neighborNames));
            }
    
            System.out.println("--------------------------------------------------\n");
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

        System.out.println("Map is valid.");
        return true;
    }

    /**
     * Get continents map.
     *
     * @return the continents map
     */
    public Map<String, Continent> getContinentsMap() {
        return d_continents;
    }

    /**
     * Get countries map.
     *
     * @return the countries map
     */
    public Map<String, Country> getCountriesMap() {
        return d_countries;
    }

    /**
     * Get continent id counter.
     *
     * @return the continent id counter
     */
    public int getContinentIdCounter() {
        return d_continentIdCounter;
    }

    /**
     * Set continent id counter.
     *
     * @param p_continentIdCounter the p continent id counter
     */
    public void setContinentIdCounter(int p_continentIdCounter) {
        this.d_continentIdCounter = p_continentIdCounter;
    }

    /**
     * Get country id counter.
     *
     * @return the country id counter
     */
    public int getCountryIdCounter() {
        return d_countryIdCounter;
    }

    /**
     * Set country id counter.
     *
     * @param p_countryIdCounter the p country id counter
     */
    public void setCountryIdCounter(int p_countryIdCounter) {
        this.d_countryIdCounter = p_countryIdCounter;
    }

    /**
     * Check if the map is a connected graph.
     *
     * @return true if the map is fully connected, false otherwise.
     */
    public boolean isMapConnected() {
        if (d_countries.isEmpty()) {
            return false; // No countries, so not connected
        }

        Set<String> visited = new HashSet<>();
        String startCountry = d_countries.keySet().iterator().next(); // Pick any country to start traversal

        // Use DFS to traverse
        dfs(startCountry, visited);
            System.out.println(" the number of visited countries are"+visited.size());
            System.out.println(" the countries size are"+d_countries.size());
        // If all countries were visited, the graph is connected
        return visited.size() == d_countries.size();
    }

    /**
     * Depth-First Search (DFS) helper method.
     * @param countryName The starting country name.
     * @param visited The set of visited countries.
     */
    private void dfs(String countryName, Set<String> visited) {
        if (visited.contains(countryName)) return; // Already visited

        visited.add(countryName); // Mark as visited

        Country country = d_countries.get(countryName);
        if (country != null) {
            for (Country neighbor : country.getNeighbors()) {
                dfs(neighbor.getName(), visited);
            }
        }
    }
}