package com.adapter;

import com.maps.MapReader;

/**
 * A factory class that provides the appropriate MapFileAdapter 
 * based on the given map type (e.g., "domination" or "conquest").
 * This class encapsulates the creation logic for different map formats.
 * This class is not meant to be instantiated as it only contains static methods.
 */
public class MapAdapterFactory {
    /**
     * Private constructor to prevent instantiation of this utility class.
     * This class only contains static methods for creating map adapters.
     */
    private MapAdapterFactory() {
        // Prevent instantiation
    }
    
    /**
     * Retrieves the appropriate MapFileAdapter based on the specified map type.
     * 
     * @param p_mapType The type of the map, which could be "domination", "conquest", or other map formats.
     * @param p_MapReader The MapReader instance used to read map data from a file.
     * This is passed to the specific map adapter.
     * @return A MapFileAdapter instance that corresponds to the given map type.
     */
    public static MapAdapter getAdapter(String p_mapType, MapReader p_MapReader) {

        if (p_mapType.toLowerCase().contains(".map")) {
            return new ConquestMapAdapter(p_MapReader);
        } else if (p_mapType.toLowerCase().contains(".txt")) {
            return new DominationMapAdapter(p_MapReader);
        }
        System.out.println("Unknown Map format provided: "+ p_mapType);
        return null;
    }
}
