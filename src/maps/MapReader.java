package maps;

import java.io.*;
import java.util.*;

/**
 * MapReader class to load and validate domination map files.
 */
public class MapReader {
    private Map<String, Integer> continents;
    private Map<String, List<String>> territories;
    
    /**
     * Constructor initializes data structures.
     */
    public MapReader() {
        continents = new HashMap<>();
        territories = new HashMap<>();
    }

    /**
     * Loads a map file into memory.
     * @param filename Name of the file to load.
     * @return true if successfully loaded, false otherwise.
     */
    public boolean loadMap(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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
                        continents.put(parts[0], Integer.parseInt(parts[1]));
                    }
                } else if (readingTerritories) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String country = parts[0];
                        List<String> neighbors = Arrays.asList(Arrays.copyOfRange(parts, 4, parts.length));
                        territories.put(country, neighbors);
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
        System.out.println("Continents:");
        for (var entry : continents.entrySet()) {
            System.out.println(entry.getKey() + " (Bonus: " + entry.getValue() + ")");
        }
        System.out.println("\nTerritories:");
        for (var entry : territories.entrySet()) {
            System.out.println(entry.getKey() + " -> " + String.join(", ", entry.getValue()));
        }
    }
    
    /**
     * Validates the map structure.
     * @return true if valid, false otherwise.
     */
    public boolean validateMap() {
        if (continents.isEmpty() || territories.isEmpty()) {
            System.out.println("Map validation failed: No continents or territories found.");
            return false;
        }
        // Example: Check if each country belongs to a continent
        for (String country : territories.keySet()) {
            boolean belongsToContinent = false;
            for (String continent : continents.keySet()) {
                if (territories.containsKey(country)) {
                    belongsToContinent = true;
                    break;
                }
            }
            if (!belongsToContinent) {
                System.out.println("Validation failed: " + country + " has no continent.");
                return false;
            }
        }
        System.out.println("Map is valid.");
        return true;
    }
}
