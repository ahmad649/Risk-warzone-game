// package com;

// import com.maps.MapReader;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import java.io.File;

// import static org.junit.jupiter.api.Assertions.*;

// /**
// * Unit tests for Map functionality.
// */
// class MapTest {

//    private MapReader mapReader;
//    private MapWriter mapWriter;
//    private MapEditor mapEditor;

//    /**
//     * Setup the MapReader, MapWriter, and MapEditor before each test.
//     */
//    @BeforeEach
//    void setUp() {
//        mapReader = new MapReader();
//        mapWriter = new MapWriter(mapReader);
//        mapEditor = new MapEditor(mapReader);
//    }

//    /**
//     * Test case for loading the map successfully.
//     */
//    @Test
//    void testLoadMapSuccessfully() {
//        boolean isLoaded = mapReader.loadMap("Witcher_Map");
//        assertTrue(isLoaded, "Map should load successfully");
//    }

//    /**
//     * Test case for verifying map connectivity.
//     */
//    @Test
//    void testMapConnectivity() {
//        mapReader.loadMap("Witcher_Map");
//        assertTrue(mapReader.isMapConnected(), "Map should be a connected graph");
//    }

//    /**
//     * Test case for removing a continent from the map.
//     */
//    @Test
//    void testRemoveContinent() {
//        mapReader.loadMap("Witcher_Map");
//        mapEditor.removeContinent("Northern_Kingdoms");
//        assertNull(mapReader.getContinentsMap().get("Northern_Kingdoms"), "Northern_Kingdoms should be removed");
//    }

//    /**
//     * Test case for removing a country from the map.
//     */
//    @Test
//    void testRemoveCountry() {
//        mapReader.loadMap("Witcher_Map");
//        mapEditor.removeCountry("Novigrad");
//        assertNull(mapReader.getCountriesMap().get("Novigrad"), "Novigrad should be removed");
//    }

//    /**
//     * Test case for adding a new country to a continent.
//     */
//    @Test
//    void testAddCountry() {
//        mapReader.loadMap("Witcher_Map");
//        mapEditor.addCountry("Pakistan", "Nilfgaardian_Empire");
//        assertNotNull(mapReader.getCountriesMap().get("Pakistan"), "Pakistan should be added to Nilfgaardian_Empire");
//    }

//    /**
//     * Test case for adding a neighbor to an existing country.
//     */
//    @Test
//    void testAddNeighbor() {
//        mapReader.loadMap("Witcher_Map");
//        mapEditor.addCountry("Pakistan", "Nilfgaardian_Empire");
//        mapEditor.addNeighbor("Pakistan", "Oxenfurt");
//        assertTrue(mapReader.getCountriesMap().get("Pakistan").getNeighbors().contains(mapReader.getCountriesMap().get("Oxenfurt")),
//                "Pakistan should have Oxenfurt as a neighbor");
//    }

//    /**
//     * Test case for saving the map successfully.
//     */
//    @Test
//    void testSaveMap() {
//        mapReader.loadMap("Witcher_Map");
//        boolean isSaved = mapWriter.saveMap("New_Witcher_Map");
//        assertTrue(isSaved, "Map should be saved successfully");

//        // Construct the file path
//        String l_mapFilePath = "src/main/resources/maps/New_Witcher_Map.txt";
//        File l_mapFile = new File(l_mapFilePath);

//        // Delete the created file
//        if (l_mapFile.exists()) {
//            l_mapFile.delete();
//        }
//    }
// }
