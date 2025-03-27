 package com.maps;

 import com.States.Postload;
 import com.States.Preload;
 import com.gameplay.GameEngine;
 import com.gameplay.Parsing;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;

 import java.io.File;

 import static org.junit.jupiter.api.Assertions.*;

 /**
 * Unit tests for Map functionality.
 */
 class MapTest {
    private GameEngine d_gameEngine;
    private MapReader mapReader;

    /**
     * Setup the MapReader, MapWriter, and MapEditor before each test.
     */
    @BeforeEach
    void setUp() {
        mapReader = new MapReader();
    }

    /**
     * Test case for loading the map successfully.
     */
    @Test
    void testLoadMapSuccessfully() {
        Preload l_preload = new Preload(this.d_gameEngine, this.mapReader);
        boolean l_isLoaded = l_preload.loadMap("Witcher_Map");

        assertTrue(l_isLoaded, "Map should load successfully");
    }

    /**
     * Test case for verifying map connectivity.
     */
    @Test
    void testMapConnectivity() {
        Preload l_preload = new Preload(this.d_gameEngine, this.mapReader);
        boolean l_isLoaded = l_preload.loadMap("Witcher_Map");

        assertTrue(mapReader.isMapConnected(), "Map should be a connected graph");
    }

    /**
     * Test case for removing a continent from the map.
     */
    @Test
    void testRemoveContinent() {
        Preload l_preload = new Preload(this.d_gameEngine, this.mapReader);
        boolean l_isLoaded = l_preload.loadMap("Witcher_Map");

        Postload l_postload = new Postload(this.mapReader);

        l_postload.removeContinent("Northern_Kingdoms");
        assertNull(mapReader.getContinentsMap().get("Northern_Kingdoms"), "Northern_Kingdoms should be removed");
    }

    /**
     * Test case for removing a country from the map.
     */
    @Test
    void testRemoveCountry() {
        Preload l_preload = new Preload(this.d_gameEngine, this.mapReader);
        boolean l_isLoaded = l_preload.loadMap("Witcher_Map");

        Postload l_postload = new Postload(this.mapReader);
        l_postload.removeCountry("Novigrad");

        assertNull(mapReader.getCountriesMap().get("Novigrad"), "Novigrad should be removed");
    }

    /**
     * Test case for adding a new country to a continent.
     */
    @Test
    void testAddCountry() {
        Preload l_preload = new Preload(this.d_gameEngine, this.mapReader);
        boolean l_isLoaded = l_preload.loadMap("Witcher_Map");

        Postload l_postload = new Postload(this.mapReader);

        l_postload.addCountry("Pakistan", "Nilfgaardian_Empire");
        assertNotNull(mapReader.getCountriesMap().get("Pakistan"), "Pakistan should be added to Nilfgaardian_Empire");
    }

    /**
     * Test case for adding a neighbor to an existing country.
     */
    @Test
    void testAddNeighbor() {
        Preload l_preload = new Preload(this.d_gameEngine, this.mapReader);
        boolean l_isLoaded = l_preload.loadMap("Witcher_Map");

        Postload l_postload = new Postload(this.mapReader);

        l_postload.addCountry("Pakistan", "Nilfgaardian_Empire");
        l_postload.addNeighbor("Pakistan", "Oxenfurt");
        assertTrue(mapReader.getCountriesMap().get("Pakistan").getNeighbors().contains(mapReader.getCountriesMap().get("Oxenfurt")),
                "Pakistan should have Oxenfurt as a neighbor");
    }

    /**
     * Test case for saving the map successfully.
     */
    @Test
    void testSaveMap() {
        Preload l_preload = new Preload(this.d_gameEngine, this.mapReader);
        boolean l_isLoaded = l_preload.loadMap("Witcher_Map");

        Postload l_postload = new Postload(this.mapReader);

        boolean isSaved = l_postload.saveMap(new Parsing("savemap New_Witcher_Map"));
        assertTrue(isSaved, "Map should be saved successfully");

        // Construct the file path
        String l_mapFilePath = "src/main/resources/maps/New_Witcher_Map.txt";
        File l_mapFile = new File(l_mapFilePath);

        // Delete the created file
        if (l_mapFile.exists()) {
            l_mapFile.delete();
        }
    }
 }
