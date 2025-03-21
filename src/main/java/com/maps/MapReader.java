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