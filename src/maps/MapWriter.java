package maps;

import java.io.*;
import java.util.*;
import model.Continent;
import model.Country;

/**
 * MapWriter handles saving the map to a file using model classes.
 */
public class MapWriter {
    private MapReader mapReader;

    /**
     * Constructor to associate with MapReader.
     * @param mapReader The MapReader instance to retrieve the loaded map data.
     */
    public MapWriter(MapReader mapReader) {
        this.mapReader = mapReader;
    }

    /**
     * Saves the currently loaded map to a file.
     * @param filename Name of the file to save.
     * @return true if saving is successful, false otherwise.
     */
    public boolean saveMap(String filename) {
        // Retrieve the currently loaded map data
        Map<String, Continent> continents = mapReader.getContinentsMap();
        Map<String, Country> countries = mapReader.getCountriesMap();
        Map<Country, List<Country>> territories = mapReader.getTerritoriesMap();
    
        if (continents.isEmpty() || countries.isEmpty()) {
            System.err.println("Error: No map data loaded. Cannot save.");
            return false;
        }
    
        // Construct the file path
        String mapFilePath = "resources/maps/" + filename + ".txt";
        File mapFile = new File(mapFilePath);
    
        // Check if the file already exists
        if (mapFile.exists()) {
            System.out.println("Error: A map with the name '" + filename + "' already exists. Please provide a unique name.");
            return false;
        }
    
        // Attempt to create the directory if it does not exist
        if (mapFile.getParentFile() != null) {
            mapFile.getParentFile().mkdirs();
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mapFile))) {
            // Write map header
            writer.write("[Map]\n");
            writer.write("author=Custom World\n");
            writer.write("image=custom_world.bmp\n");
            writer.write("wrap=no\n");
            writer.write("scroll=horizontal\n");
            writer.write("warn=yes\n\n");
    
            // Write continents section
            writer.write("[Continents]\n");
    
            // Continent order as required
            List<String> continentOrder = Arrays.asList(
                "North America", "South America", "Europe", "Africa", "Asia", "Australia"
            );
    
            for (String continentName : continentOrder) {
                Continent continent = continents.get(continentName);
                if (continent != null) {
                    writer.write(continentName + "=" + continent.getBonus() + "\n");
                }
            }
            writer.write("\n");
    
            // Write territories section
            writer.write("[Territories]\n");
    
            // Iterate through countries and write their territory data
            for (Country country : countries.values()) {
                String continentName = country.getContinent().getName();
                List<String> neighborNames = new ArrayList<>();
                if (territories.containsKey(country)) {
                    for (Country neighbor : territories.get(country)) {
                        neighborNames.add(neighbor.getName());
                    }
                }
    
                // Write the country data with coordinates and neighbors
                int x = 0; 
                int y = 0; 
    
                writer.write(String.format("%s,%d,%d,%s,%s\n", country.getName(), x, y, continentName, String.join(",", neighborNames)));
            }
    
            System.out.println("Map saved successfully to " + mapFilePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving map: " + e.getMessage());
            return false;
        }
    }
        
}
