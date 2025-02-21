package maps;



/**
 * Main class to test the MapReader functionality.
 */
public class Main {
    public static void main(String[] args) {
        MapReader mapReader = new MapReader();
        MapWriter mapWriter = new MapWriter(mapReader);
    
        // Provide the correct file path to a domination map file
        String mapFile = "world"; 

        if (mapReader.loadMap(mapFile)) {
            System.out.println("\nMap loaded successfully!\n");

            // Display the map contents
            mapReader.showMap();

            // Validate the map
            System.out.println("\nValidating Map...");
            boolean isValid = mapReader.validateMap();

            if (isValid) {
                System.out.println("Map validation successful!");
                mapWriter.saveMap("world2");
            } else {
                System.out.println("Map validation failed.");
            }
        } else {
            System.out.println("Failed to load the map.");
        }
    }
}
