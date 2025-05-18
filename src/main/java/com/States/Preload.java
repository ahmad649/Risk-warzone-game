package com.States;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gameplay.Parsing;
import com.adapter.MapAdapter;
import com.adapter.MapAdapterFactory;
import com.gameplay.GameEngine;
import com.maps.MapReader;
import com.model.Continent;
import com.model.Country;

@JsonTypeName("preload")
/**
 * Preload phase where the map is loaded and processed before the game starts.
 * This phase handles the reading of map data (continents and countries).
 */
public class Preload implements Phase{

    private MapAdapter mapAdapter;
    private MapReader d_mapReader;
    /**
     * A map of continents loaded from the map file.
     */
    public Map<String, Continent> d_continents;
    /**
     * A map of countries loaded from the map file.
     */
    public Map<String, Country> d_countries;
    GameEngine engine;

    /**
     * Preload no-params constructor for serialization
     */
    public Preload(){}




    /**
     * Instantiate a new Preload state.
     *
     * @param engine the GameEngine instance to manage game states and transitions
     * @param p_mapReader the MapReader instance used to load and parse map data
     */
    public Preload(GameEngine engine, MapReader p_mapReader) {
        this.d_mapReader = p_mapReader;
        this.engine = engine;
        d_continents = d_mapReader.getContinentsMap();
        d_countries = d_mapReader.getCountriesMap();
    }

    /**
     * Continents getter method
     * @return A map containing the continents initialized for the game
     */
    public Map<String, Continent> getD_continents() {
        return d_continents;
    }

    /**
     * Continents setter method
     * @param d_continents takes a map containing the continents to set
     */
    public void setD_continents(Map<String, Continent> d_continents) {
        this.d_continents = d_continents;
    }

    /**
     * MapReader getter method
     * @return A MapReader object
     */
    public MapReader getD_mapReader() {
        return d_mapReader;
    }

    /**
     * MapReader setter method
     * @param d_mapReader takes a MapReader object and sets it
     */
    public void setD_mapReader(MapReader d_mapReader) {
        this.d_mapReader = d_mapReader;
    }

    /**
     * Countries getter method
     * @return A map containing the countries initialized for the game
     */
    public Map<String, Country> getD_countries() {
        return d_countries;
    }

    /**
     * Countries setter method
     * @param d_countries takes a Map containing the countries to set
     */
    public void setD_countries(Map<String, Country> d_countries) {
        this.d_countries = d_countries;
    }

    /**
     * GameEngine getter method
     * @return GameEngine type object
     */
    public GameEngine getEngine() {
        return engine;
    }

    /**
     * GameEngine setter method
     * @param engine takes a GameEngine object to set
     */
    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * currentPhase identifier
     * @return returns a string indicating the current phase of the game
     */
    public String currentPhase() {
        return "Preload";
    }

    /**
     * Loads a map file into memory.
     *
     * @param p_filename Name of the file to load.
     * @return true if successfully loaded, false otherwise.
     */
    public boolean loadMap(String p_filename) {
        this.mapAdapter = MapAdapterFactory.getAdapter(p_filename, d_mapReader);
        if(mapAdapter == null){
            return false;
        }
        boolean l_result = mapAdapter.loadMap(p_filename);
        if(l_result){
            validateMap();
            System.out.println("Map is loaded successfully!");
        }
        return l_result;
    }

    /**
     * Method to start map editor
     */
    public void editor(){
        if (engine.d_phase.currentPhase()=="Preload")
        engine.d_phase = new Postload(d_mapReader);
    }


    /**
     * Method to edit the map
     * @param l_parsing the parsing object
     */
    public void editMap(Parsing l_parsing){
        String l_filename = l_parsing.getArgArr().getFirst();
        loadMap(l_filename);
    }

        /**
     * Validates the map structure.
     *
     * @return true if valid, false otherwise.
     */
    public boolean validateMap() {
        if (d_continents.isEmpty() || d_countries.isEmpty()) {
            System.out.println("Map validation failed: No continents or countries found.");
            return false;
        }

        // Check if each continent has at least one country
        for (Continent l_continent : d_continents.values()) {
            if (l_continent.getCountries().isEmpty()) {
                System.out.println("Validation failed: Continent " + l_continent.getName() + " has no countries.");
                return false;
            }
        }

        // Ensure all countries have valid neighbors
        for (Country l_country : d_countries.values()) {
            for (Country l_neighbor : l_country.getNeighbors()) {
                if (!d_countries.containsKey(l_neighbor.getName())) {
                    System.out.println("Validation failed: " + l_country.getName() + " has an invalid neighbor " + l_neighbor.getName());
                    return false;
                }
            }
        }

        System.out.println("\nMap is valid!");
        return true;
    }

}