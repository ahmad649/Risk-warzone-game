package com.model;

import com.gameplay.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a Country in the game.
 */
//
public class Country {
    private int d_id;
    private String d_name;
    private Continent d_continent;
    private Player d_owner;
    private int d_armies;
    private List<Country> d_neighbors;
    private String d_xCoordinate;
    private String d_yCoordinate;

    /**
     * Instantiates a new Country.
     *
     * @param p_countryIdCounter the p country id counter
     * @param p_name             the p name
     * @param p_continent        the p continent
     */
    public Country(int p_countryIdCounter, String p_name, Continent p_continent) {
        this.d_id = p_countryIdCounter;
        this.d_name = p_name;
        this.d_continent = p_continent;
        this.d_armies = 0;
        this.d_neighbors = new ArrayList<>();
    }

    /**
     * Gets the X coordinate of the country.
     *
     * @return the X coordinate as a string.
     */
    public String getXCoordinate() {
        return d_xCoordinate;
    }

    /**
     * Gets the Y coordinate of the country.
     *
     * @return the Y coordinate as a string.
     */
    public String getYCoordinate() {
        return d_yCoordinate;
    }


    /**
     * Get country ID.
     *
     * @return the id
     */
    public int getId() {
        return d_id;
    }

    /**
     * Get country name.
     *
     * @return the name
     */
    public String getName() {
        return d_name;
    }

    /**
     * Get continent that owns this country.
     *
     * @return the continent
     */
    public Continent getContinent() {
        return d_continent;
    }

    /**
     * Get owner of this country.
     *
     * @return the owner
     */
    public Player getOwner() {
        return d_owner;
    }

    /**
     * Get armies in this country.
     *
     * @return the armies
     */
    public int getArmies() {
        return d_armies;
    }

    /**
     * Get a list of neighboring countries.
     *
     * @return the neighboring countries
     */
    public List<Country> getNeighbors() {
        return d_neighbors;
    }
    /**
     * Retrieves a neighbor country by its name.
     *
     * @param d_countryName the name of the country to find among the neighbors
     * @return the neighbor country that matches the provided name, or null if no such neighbor exists
     */
    public Country getNeighborByName(String d_countryName){
        for (Country l_country : d_neighbors){
            if (l_country.getName().equals(d_countryName)){
                return l_country;
            }
        }
        return null;
    }
    /**
     * Checks if a given country is a neighbor of the current country.
     *
     * @param d_countryName the name of the country to check for adjacency
     * @return true if the country is a neighbor, false otherwise
     */
    public boolean isNeighbor(String d_countryName){
        for (Country l_country : d_neighbors){
            if (l_country.getName().equals(d_countryName)){
                return true;
            }
        }
        return false;
    }


    /**
     * Sets the X coordinate of the country.
     *
     * @param p_xCoordinate the X coordinate to set.
     */
    public void setXCoordinate(String p_xCoordinate) {
        this.d_xCoordinate = p_xCoordinate;
    }
    /**
     * Sets the Y coordinate of the country.
     *
     * @param p_yCoordinate the Y coordinate to set.
     */
    public void setYCoordinate(String p_yCoordinate) {
        this.d_yCoordinate = p_yCoordinate;
    }

    /**
     * Set continent that owns this country.
     *
     * @param p_continent the continent
     */
    public void setContinent(Continent p_continent) {
        this.d_continent = p_continent;
    }

    /**
     * Set owner of this country.
     *
     * @param p_owner the owner
     */
    public void setOwner(Player p_owner) {
        this.d_owner = p_owner;
    }

    /**
     * Set armies in this country.
     *
     * @param p_armies the armies
     */
    public void setArmies(int p_armies) {
        this.d_armies = p_armies;
    }

    /**
     * Add neighboring country.
     *
     * @param p_neighbor the neighbor
     */
    public void addNeighbor(Country p_neighbor) {
        if (!this.d_neighbors.contains(p_neighbor)) {  // Ensure no duplicate neighbors
            this.d_neighbors.add(p_neighbor);
        }
    }

    /**
     * Remove neighboring country.
     *
     * @param p_neighbor the neighbor
     */
    public void removeNeighbor(Country p_neighbor) {
        d_neighbors.remove(p_neighbor);
    }

    /**
     * Add reinforcements.
     *
     * @param p_reinforcements the reinforcements
     */
    public void addReinforcements(int p_reinforcements) {
        d_armies += p_reinforcements;
    }

    /**
     * Remove reinforcements.
     *
     * @param p_reinforcements the reinforcements
     */
    public void removeReinforcements(int p_reinforcements) {
        d_armies -= p_reinforcements;
    }

    @Override
    public String toString() {

        String neighbors = d_neighbors != null ? d_neighbors.stream()
                .map(Country::getName)
                .collect(Collectors.joining(", ")) : "None";

        return "Country{" +
                ", Name='" + d_name + '\'' +
                ", Continent=" + (d_continent != null ? d_continent.getName() : "None") +
                ", Owner=" + (d_owner != null ? d_owner.getName() : "None") +
                ", Armies=" + d_armies +
                ", Neighbors=[" + neighbors + "]" +
                '}';

    }
}
