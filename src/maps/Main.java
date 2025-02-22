package maps;


/**
 * Main class to test the MapReader functionality.
 */
public class Main {
    public static void main(String[] args) {
        // Create the MapReader to load, manage, and validate the map
        MapReader mapReader = new MapReader();

        // Create the MapWriter to save changes made by MapEditor
        MapWriter mapWriter = new MapWriter(mapReader);

        // Create the MapEditor to modify the map interactively
        MapEditor mapEditor = new MapEditor(mapReader);

        // Provide the correct file path to a domination map file
        String mapFile = "Game of Thrones Map"; // Assuming this is the file with the map content.

        // Load the map from file
        if (mapReader.loadMap(mapFile)) {
            System.out.println("\nMap loaded successfully!\n");

            // Display the loaded map
            mapReader.showMap();

            // --- Remove the continent "Sothoryos" ---
            System.out.println("\nRemoving continent Sothoryos...\n");
            mapEditor.removeContinent("Sothoryos");

            // Display the map after removing the continent
            mapReader.showMap();

            // --- Remove the country "Braavos" from "Essos" ---
            System.out.println("\nRemoving country Braavos from Essos...\n");
            mapEditor.removeCountry("Braavos");

            // Display the map after removing the country
            mapReader.showMap();

            // --- Add Pakistan to Westeros and add neighbors ---
            System.out.println("\nAdding country Pakistan to Westeros and adding neighbors...\n");
            mapEditor.addCountry("Pakistan", "Westeros"); // Add new country to Westeros


            // Add neighbors (for example, Pakistan's neighbors will be Winterfell and King's Landing)
            mapEditor.addNeighbor("Pakistan", "Winterfell");
            mapEditor.addNeighbor("Pakistan", "King's Landing");

            // Display the map after adding Pakistan and neighbors
            mapReader.showMap();

            // --- Remove the country "The Wall" from Westeros ---
            System.out.println("\nRemoving country The Wall from Westeros...\n");
            mapEditor.removeCountry("The Wall");

            // Display the map after removing "The Wall"
            mapReader.showMap();

            // --- Save the updated map as "updated" ---
            mapWriter.saveMap("updated");
            System.out.println("\nMap saved as 'updated'.");
        } else {
            System.out.println("Failed to load the map.");
        }
    }
}
