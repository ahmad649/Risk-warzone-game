package maps;

import model.Continent;
import model.Country;

import java.util.*;

/**
 * MapEditor allows users to create and modify maps dynamically.
 */
public class MapEditor {
    private MapReader mapReader;

    public MapEditor(MapReader mapReader) {
        this.mapReader = mapReader;
    }

    /**
     * Adds a new continent.
     * @param name Name of the continent.
     * @param bonusValue Bonus value of the continent.
     */
    public void addContinent(String name, int bonusValue) {
        // Check if the continent already exists
        if (mapReader.getContinentsMap().containsKey(name)) {
            System.out.println("Continent " + name + " already exists.");
        } else {
            // Get the current continent ID counter, assign to the new continent, and increment it
            int continentId = mapReader.getContinentIdCounter() + 1;
            Continent continent = new Continent(continentId, name, bonusValue);
            mapReader.getContinentsMap().put(name, continent);

            // Increment the continent ID counter
            mapReader.setContinentIdCounter(continentId);
            System.out.println("Continent " + name + " added successfully.");
        }
    }

    /**
     * Removes a continent.
     * @param name Name of the continent to remove.
     */
    public void removeContinent(String name) {
        Continent continent = mapReader.getContinentsMap().get(name);
        if (continent != null) {
            // Remove the continent from the continents map
            mapReader.getContinentsMap().remove(name);

            // Remove all countries that belong to this continent
            Iterator<Map.Entry<String, Country>> iterator = mapReader.getCountriesMap().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Country> entry = iterator.next();
                Country country = entry.getValue();
                
                // Check if the continent is null and print the country name
                if (country.getContinent() == null) {
                    System.out.println("Country " + country.getName() + " has no continent assigned.");
                } else if (country.getContinent().getName().equals(name)) {
                    iterator.remove();
                }
            }

            // Remove all neighbors associated with this continent's countries
            for (Country country : continent.getCountries()) {
                country.getNeighbors().clear(); // Remove all neighboring countries
            }

            System.out.println("Continent " + name + " removed successfully.");
        } else {
            System.out.println("Continent " + name + " does not exist.");
        }
    }

    /**
     * Adds a new country.
     * @param name Name of the country.
     * @param continentName Name of the continent the country belongs to.
     */
    public void addCountry(String name, String continentName) {
        // Check if the country already exists
        if (mapReader.getCountriesMap().containsKey(name)) {
            System.out.println("Country " + name + " already exists.");
            return;
        }

        // Check if the continent exists
        Continent continent = mapReader.getContinentsMap().get(continentName);
        if (continent == null) {
            System.out.println("Continent " + continentName + " does not exist. Adding country failed.");
            return;
        }

        // Check if the country already exists in the specified continent
        for (Country country : continent.getCountries()) {
            if (country.getName().equals(name)) {
                System.out.println("Country " + name + " already exists in continent " + continentName + ".");
                return;
            }
        }

        // Get the current country ID counter, assign to the new country, and increment it
        int countryId = mapReader.getCountryIdCounter() + 1;
        Country country = new Country(countryId, name, continent);
        mapReader.getCountriesMap().put(name, country);
        continent.addCountry(country);

        // Update the country ID counter
        mapReader.setCountryIdCounter(countryId);

        System.out.println("Country " + name + " added to continent " + continentName + " successfully.");
    }

    /**
     * Removes a country.
     * @param name Name of the country to remove.
     */
    public void removeCountry(String name) {
        Country country = mapReader.getCountriesMap().get(name);
        if (country != null) {
            // Remove the country from the countries map
            mapReader.getCountriesMap().remove(name);

            // Remove this country from its continent
            Continent continent = country.getContinent();
            continent.removeCountry(country);

            // Remove all neighbors associated with this country
            for (Country neighbor : country.getNeighbors()) {
                neighbor.getNeighbors().remove(country); // Remove this country from its neighbor's list
            }

            // Clear this country's neighbors list
            country.getNeighbors().clear();

            System.out.println("Country " + name + " removed successfully.");
        } else {
            // Country does not exist
            System.out.println("Country " + name + " does not exist. Cannot remove.");
        }
    }

    /**
     * Adds a neighboring connection between two countries.
     * @param countryName The country.
     * @param neighborName The neighboring country.
     */
    public void addNeighbor(String countryName, String neighborName) {
        Country country1 = mapReader.getCountriesMap().get(countryName);
        Country country2 = mapReader.getCountriesMap().get(neighborName);

        // Check if both countries exist
        if (country1 == null) {
            System.out.println("Country " + countryName + " does not exist. Cannot add neighbor.");
            return;
        }
        if (country2 == null) {
            System.out.println("Country " + neighborName + " does not exist. Cannot add neighbor.");
            return;
        }

        // Check if they are already neighbors
        if (country1.getNeighbors().contains(country2)) {
            System.out.println(countryName + " and " + neighborName + " are already neighbors.");
            return;
        }

        // Add the neighbor relationship
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);

        // Print success message
        System.out.println("Neighbor relationship established between " + countryName + " and " + neighborName + ".");
    }

    /**
     * Removes a neighboring connection between two countries.
     * @param countryName The country.
     * @param neighborName The neighboring country to remove.
     */
    public void removeNeighbor(String countryName, String neighborName) {
        Country country1 = mapReader.getCountriesMap().get(countryName);
        Country country2 = mapReader.getCountriesMap().get(neighborName);

        // Check if both countries exist
        if (country1 == null) {
            System.out.println("Country " + countryName + " does not exist. Cannot remove neighbor.");
            return;
        }
        if (country2 == null) {
            System.out.println("Country " + neighborName + " does not exist. Cannot remove neighbor.");
            return;
        }

        // Check if they are neighbors
        if (!country1.getNeighbors().contains(country2)) {
            System.out.println(countryName + " and " + neighborName + " are not neighbors.");
            return;
        }

        // Remove the neighbor relationship
        country1.getNeighbors().remove(country2);
        country2.getNeighbors().remove(country1);

        // Print success message
        System.out.println("Neighbor relationship removed between " + countryName + " and " + neighborName + ".");
    }
}
