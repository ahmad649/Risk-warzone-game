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
        // Get the current continent ID counter, assign to the new continent, and increment it
        int continentId = mapReader.getContinentIdCounter() + 1;
        Continent continent = new Continent(continentId, name, bonusValue);
        mapReader.getContinentsMap().put(name, continent);

        // Increment the continent ID counter
        mapReader.setContinentIdCounter(continentId);
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
                if (entry.getValue().getContinent().getName().equals(name)) {
                    iterator.remove();
                }
            }

            // Remove all neighbors associated with this continent's countries
            mapReader.getTerritoriesMap().forEach((country, neighbors) -> 
                neighbors.removeIf(neighbor -> neighbor.getContinent().getName().equals(name))
            );
        }
    }

    /**
     * Adds a new country.
     * @param name Name of the country.
     * @param continentName Name of the continent the country belongs to.
     */
    public void addCountry(String name, String continentName) {
        Continent continent = mapReader.getContinentsMap().get(continentName);
        if (continent != null) {
            // Get the current country ID counter, assign to the new country, and increment it
            int countryId = mapReader.getCountryIdCounter() + 1;
            Country country = new Country(countryId, name, continent);
            mapReader.getCountriesMap().put(name, country);
            mapReader.getTerritoriesMap().put(country, new ArrayList<>());
            continent.addCountry(country);

            mapReader.setCountryIdCounter(countryId);
        }
    }

    /**
     * Removes a country.
     * @param name Name of the country to remove.
     */
    public void removeCountry(String name) {
        Country country = mapReader.getCountriesMap().get(name);
        if (country != null) {
            mapReader.getCountriesMap().remove(name);
            mapReader.getTerritoriesMap().remove(country);

            // Remove this country from all neighboring lists
            for (List<Country> neighbors : mapReader.getTerritoriesMap().values()) {
                neighbors.remove(country);
            }

            // Remove this country from its continent
            Continent continent = country.getContinent();
            continent.removeCountry(country);
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
        if (country1 != null && country2 != null) {
            mapReader.getTerritoriesMap().get(country1).add(country2);
            mapReader.getTerritoriesMap().get(country2).add(country1);
        }
    }

    /**
     * Removes a neighboring connection between two countries.
     * @param countryName The country.
     * @param neighborName The neighboring country to remove.
     */
    public void removeNeighbor(String countryName, String neighborName) {
        Country country1 = mapReader.getCountriesMap().get(countryName);
        Country country2 = mapReader.getCountriesMap().get(neighborName);
        if (country1 != null && country2 != null) {
            mapReader.getTerritoriesMap().get(country1).remove(country2);
            mapReader.getTerritoriesMap().get(country2).remove(country1);
        }
    }

    /**
     * Displays the current map.
     */
    public void showMap() {
        mapReader.showMap();  // Calls MapReader's showMap method to display the updated map
    }
}
