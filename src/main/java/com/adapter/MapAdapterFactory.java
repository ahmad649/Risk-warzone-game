package com.adapter;

import com.maps.MapReader;

/**
 * A factory class that provides the appropriate MapFileAdapter 
 * based on the given map type (e.g., "domination" or "conquest").
 * This class encapsulates the creation logic for different map formats.
 */
public class MapAdapterFactory {

    /**
     * Retrieves the appropriate MapFileAdapter based on the specified map type.
     * 
     * @param p_mapType The type of the map, which could be "domination", "conquest", or other map formats.
     * @return A MapFileAdapter instance that corresponds to the given map type.
     * @throws IllegalArgumentException If the map type is unknown or unsupported.
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
