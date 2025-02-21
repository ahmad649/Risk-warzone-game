package maps;

import model.Continent;
import model.Country;
import java.io.*;
import java.util.*;

/**
 * MapReader class to load and validate domination map files.
 * MapReader class to load and validate domination map files.
 */
public class MapReader {
    private Map<String, Continent> continents;
    private Map<String, Country> countries;
    private Map<Country, List<Country>> territories;
    private int continentIdCounter = 1;
    private int countryIdCounter = 1;

    /**
     * Constructor initializes data structures.
     */
    public MapReader() {
        continents = new HashMap<>();
        countries = new HashMap<>();
        territories = new HashMap<>();
    }


    /**
     * Loads a map file into memory.
     * Loads a map file into memory.
     * @param filename Name of the file to load.
     * @return true if successfully loaded, false otherwise.
     * @return true if successfully loaded, false otherwise.
     */
    public boolean loadMap(String filename) {
        // Construct the full file path
        String mapFilePath = "resources/maps/" + filename + ".txt";
        File mapFile = new File(mapFilePath);
    
        // If the file does not exist, create a new one
        if (!mapFile.exists()) {
            try {
                if (mapFile.getParentFile() != null) {
                    mapFile.getParentFile().mkdirs(); // Ensure the directory exists
                }
                if (mapFile.createNewFile()) {
                    System.out.println("Map file did not exist, so a new map file was created: " + mapFilePath);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(mapFile))) {
            String line;
            boolean readingContinents = false, readingTerritories = false;
    
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
    
                if (line.equals("[Continents]")) {
                    readingContinents = true;
                    readingTerritories = false;
                    continue;
                } else if (line.equals("[Territories]")) {
                    readingContinents = false;
                    readingTerritories = true;
                    continue;
                }
    
                if (readingContinents) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String name = parts[0];
                        int bonus = Integer.parseInt(parts[1]);
                        Continent continent = new Continent(continentIdCounter++, name, bonus);
                        continents.put(name, continent);
                    }
                } else if (readingTerritories) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String countryName = parts[0];
                        String continentName = parts[3];
    
                        Continent continent = continents.get(continentName);
                        if (continent == null) {
                            System.err.println("Error: Continent " + continentName + " not found for country " + countryName);
                            return false;
                        }
    
                        Country country = new Country(countryIdCounter++, countryName, continent);
                        countries.put(countryName, country);
                        territories.put(country, new ArrayList<>());
    
                        // Add country to its continent
                        continent.addCountry(country);
    
                        // Process neighbors
                        for (int i = 4; i < parts.length; i++) {
                            String neighborName = parts[i];
                            if (countries.containsKey(neighborName)) {
                                Country neighbor = countries.get(neighborName);
                                territories.get(country).add(neighbor);
                                territories.get(neighbor).add(country);
                            }
                        }
                    }
                }
            }
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
        System.out.println("Continents:");
        for (Continent continent : continents.values()) {
            System.out.println("--------------------------------------------------");
            System.out.println(continent.getName() + " (ID: " + continent.getId() + ", Bonus: " + continent.getBonus() + ")");
            System.out.println("Countries in this continent:");
    
            // Display countries in the continent
            for (Country country : countries.values()) {
                if (country.getContinent().equals(continent)) {
                    System.out.print("    " + country.getName() + " (ID: " + country.getId() + ") -> Neighbors: ");
                    
                    List<String> neighborNames = new ArrayList<>();
                    for (Country neighbor : territories.get(country)) {
                        neighborNames.add(neighbor.getName());
                    }
                    
                    // Print neighbors
                    System.out.println(String.join(", ", neighborNames));
                }
            }
    
            System.out.println("--------------------------------------------------\n");
        }
    }
    

    /**
     * Validates the map structure.
     * @return true if valid, false otherwise.
     */
    public boolean validateMap() {
        if (continents.isEmpty() || countries.isEmpty()) {
            System.out.println("Map validation failed: No continents or countries found.");
            return false;
        }

        // Check if each continent has at least one country
        for (Continent continent : continents.values()) {
            if (continent.getCountries().isEmpty()) {
                System.out.println("Validation failed: Continent " + continent.getName() + " has no countries.");
                return false;
            }
        }

        // Ensure all countries have valid neighbors
        for (Country country : territories.keySet()) {
            for (Country neighbor : territories.get(country)) {
                if (!territories.containsKey(neighbor)) {
                    System.out.println("Validation failed: " + country.getName() + " has an invalid neighbor " + neighbor.getName());
                    return false;
                }
            }
        }

        System.out.println("Map is valid.");
        return true;
    }

        /**
     * Getter methods to access map data.
     */
    public Map<String, Continent> getContinentsMap() {
        return continents;
    }

    public Map<String, Country> getCountriesMap() {
        return countries;
    }

    public Map<Country, List<Country>> getTerritoriesMap() {
        return territories;
    }

    // Getter for continentIdCounter
    public int getContinentIdCounter() {
        return continentIdCounter;
    }

    // Setter for continentIdCounter
    public void setContinentIdCounter(int continentIdCounter) {
        this.continentIdCounter = continentIdCounter;
    }

    // Getter for countryIdCounter
    public int getCountryIdCounter() {
        return countryIdCounter;
    }

    // Setter for countryIdCounter
    public void setCountryIdCounter(int countryIdCounter) {
        this.countryIdCounter = countryIdCounter;
    }


}
