package com.maps;

import com.model.Continent;
import com.model.Country;

import java.util.*;

/**
 * MapEditor allows users to create and modify main.com.maps dynamically.
 */
public class MapEditor {
    private MapReader d_mapReader;

    /**
     * Instantiate a new Map editor.
     *
     * @param p_mapReader the map reader
     */
    public MapEditor(MapReader p_mapReader) {
        this.d_mapReader = p_mapReader;
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
}
