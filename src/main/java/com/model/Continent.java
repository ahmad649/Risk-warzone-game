package com.model;

import java.util.*;

/**
 * Represents a Continent in the game
 */
// Represents a Continent in the game
public class Continent {
    private int d_id;
    private String d_name;
    private int d_bonus;
    private List<Country> d_countries;

    /**
     * Instantiates a new Continent.
     *
     * @param continentIdCounter the continent id counter
     * @param name               the name
     * @param bonus              the bonus
     */
    public Continent(int continentIdCounter, String name, int bonus) {
        this.d_id = continentIdCounter;
        this.d_name = name;
        this.d_bonus = bonus;
        this.d_countries = new ArrayList<>();
    }

    /**
     * Get continent ID.
     *
     * @return the continent ID
     */
    public int getId() { return d_id; }

    /**
     * Get continent name.
     *
     * @return the continent name
     */
    public String getName() { return d_name; }

    /**
     * Get bonus.
     *
     * @return the bonus
     */
    public int getBonus() { return d_bonus; }

    /**
     * Get a list of countries.
     *
     * @return the countries
     */
    public List<Country> getCountries() { return d_countries; }

    /**
     * Add country.
     *
     * @param country the country
     */
    public void addCountry(Country country) { d_countries.add(country); }

    /**
     * Remove country.
     *
     * @param country the country
     */
    public void removeCountry(Country country) { d_countries.remove(country); }
}
