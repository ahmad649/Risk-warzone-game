package com.States;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;
import com.maps.MapReader;
import com.model.Continent;
import com.model.Country;

public class Postload implements Phase{
    
    private MapReader d_mapReader;
    private Map<String, Continent> d_continents;
    private Map<String, Country> d_countries;
    /**
     * Instantiate a new Post load state.
     *
     * @param p_mapReader the map reader
     */
    public Postload(MapReader p_mapReader) {
        this.d_mapReader = p_mapReader;
        d_continents = d_mapReader.getContinentsMap();
        d_countries = d_mapReader.getCountriesMap();
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

        System.out.println("Map is valid!");
        return true;
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
        if(!validateMap()) {return false;}
    
        // Construct the file path
        String l_mapFilePath = "src/main/resources/maps/" + p_filename + ".txt";
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

    /**
     * Add a new continent.
     *
     * @param p_name       Name of the continent.
     * @param p_bonusValue Bonus value of the continent.
     */
    public void addContinent(String p_name, int p_bonusValue) {
        // Check if the continent already exists
        if (d_mapReader.getContinentsMap().containsKey(p_name)) {
            System.out.println("Continent " + p_name + " already exists.");
        } else {
            // Get the current continent ID counter, assign to the new continent, and increment it
            int l_continentId = d_mapReader.getContinentIdCounter() + 1;
            Continent continent = new Continent(l_continentId, p_name, p_bonusValue);
            d_mapReader.getContinentsMap().put(p_name, continent);

            // Increment the continent ID counter
            d_mapReader.setContinentIdCounter(l_continentId);
            System.out.println("Continent " + p_name + " added successfully.");
        }
    }

    /**
     * Removes a continent.
     *
     * @param p_name Name of the continent to remove.
     */
    public void removeContinent(String p_name) {
        Continent l_continent = d_mapReader.getContinentsMap().get(p_name);
        if (l_continent != null) {
            // Remove the continent from the continents map
            d_mapReader.getContinentsMap().remove(p_name);

            // Remove all countries that belong to this continent
            Iterator<Map.Entry<String, Country>> l_iterator = d_mapReader.getCountriesMap().entrySet().iterator();
            while (l_iterator.hasNext()) {
                Map.Entry<String, Country> l_entry = l_iterator.next();
                Country l_country = l_entry.getValue();
                
                // Check if the continent is null and print the country name
                if (l_country.getContinent() == null) {
                    System.out.println("Country " + l_country.getName() + " has no continent assigned.");
                } else if (l_country.getContinent().getName().equals(p_name)) {
                    l_iterator.remove();
                }
            }

            // Remove all neighbors associated with this continent's countries
            for (Country l_country : l_continent.getCountries()) {
                l_country.getNeighbors().clear(); // Remove all neighboring countries
            }

            System.out.println("Continent " + p_name + " removed successfully.");
        } else {
            System.out.println("Continent " + p_name + " does not exist.");
        }
    }

    /**
     * Adds a new country.
     *
     * @param p_name          Name of the country.
     * @param p_continentName Name of the continent the country belongs to.
     */
    public void addCountry(String p_name, String p_continentName) {
        // Check if the country already exists
        if (d_mapReader.getCountriesMap().containsKey(p_name)) {
            System.out.println("Country " + p_name + " already exists.");
            return;
        }

        // Check if the continent exists
        Continent l_continent = d_mapReader.getContinentsMap().get(p_continentName);
        if (l_continent == null) {
            System.out.println("Continent " + p_continentName + " does not exist. Adding country failed.");
            return;
        }

        // Check if the country already exists in the specified continent
        for (Country l_country : l_continent.getCountries()) {
            if (l_country.getName().equals(p_name)) {
                System.out.println("Country " + p_name + " already exists in continent " + p_continentName + ".");
                return;
            }
        }

        // Get the current country ID counter, assign to the new country, and increment it
        int l_countryID = d_mapReader.getCountryIdCounter() + 1;
        Country country = new Country(l_countryID, p_name, l_continent);
        d_mapReader.getCountriesMap().put(p_name, country);
        l_continent.addCountry(country);

        // Update the country ID counter
        d_mapReader.setCountryIdCounter(l_countryID);

        System.out.println("Country " + p_name + " added to continent " + p_continentName + " successfully.");
    }

    /**
     * Removes a country.
     *
     * @param p_name Name of the country to remove.
     */
    public void removeCountry(String p_name) {
        Country l_country = d_mapReader.getCountriesMap().get(p_name);
        if (l_country != null) {
            // Remove the country from the countries map
            d_mapReader.getCountriesMap().remove(p_name);

            // Remove this country from its continent
            Continent l_continent = l_country.getContinent();
            l_continent.removeCountry(l_country);

            // Remove all neighbors associated with this country
            for (Country l_ : l_country.getNeighbors()) {
                l_.getNeighbors().remove(l_country); // Remove this country from its neighbor's list
            }

            // Clear this country's neighbors list
            l_country.getNeighbors().clear();

            System.out.println("Country " + p_name + " removed successfully.");
        } else {
            // Country does not exist
            System.out.println("Country " + p_name + " does not exist. Cannot remove.");
        }
    }

    /**
     * Adds a neighboring connection between two countries.
     *
     * @param p_countryName  The country.
     * @param p_neighborName The neighboring country.
     */
    public void addNeighbor(String p_countryName, String p_neighborName) {
        Country l_country1 = d_mapReader.getCountriesMap().get(p_countryName);
        Country l_country2 = d_mapReader.getCountriesMap().get(p_neighborName);

        // Check if both countries exist
        if (l_country1 == null) {
            System.out.println("Country " + p_countryName + " does not exist. Cannot add neighbor.");
            return;
        }
        if (l_country2 == null) {
            System.out.println("Country " + p_neighborName + " does not exist. Cannot add neighbor.");
            return;
        }

        // Check if they are already neighbors
        if (l_country1.getNeighbors().contains(l_country2)) {
            System.out.println(p_countryName + " and " + p_neighborName + " are already neighbors.");
            return;
        }

        // Add the neighbor relationship
        l_country1.addNeighbor(l_country2);
        l_country2.addNeighbor(l_country1);

        // Print success message
        System.out.println("Neighbor relationship established between " + p_countryName + " and " + p_neighborName + ".");
    }

    /**
     * Removes a neighboring connection between two countries.
     *
     * @param p_countryName  The country.
     * @param p_neighborName The neighboring country to remove.
     */
    public void removeNeighbor(String p_countryName, String p_neighborName) {
        Country l_country1 = d_mapReader.getCountriesMap().get(p_countryName);
        Country l_country2 = d_mapReader.getCountriesMap().get(p_neighborName);

        // Check if both countries exist
        if (l_country1 == null) {
            System.out.println("Country " + p_countryName + " does not exist. Cannot remove neighbor.");
            return;
        }
        if (l_country2 == null) {
            System.out.println("Country " + p_neighborName + " does not exist. Cannot remove neighbor.");
            return;
        }

        // Check if they are neighbors
        if (!l_country1.getNeighbors().contains(l_country2)) {
            System.out.println(p_countryName + " and " + p_neighborName + " are not neighbors.");
            return;
        }

        // Remove the neighbor relationship
        l_country1.getNeighbors().remove(l_country2);
        l_country2.getNeighbors().remove(l_country1);

        // Print success message
        System.out.println("Neighbor relationship removed between " + p_countryName + " and " + p_neighborName + ".");
    }



    @Override
    public void addGamePlayer(GameEngine engine, Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in postload phase!");
    }



    @Override
    public boolean loadMap(String p_filename) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in postload phase!");
        return false;
    }

}
