package com.model;

import java.util.*;

// Represents a Continent in the game
public class Continent {
    private int d_id;
    private String d_name;
    private int d_bonus;
    private List<Country> d_countries;
    
    public Continent(int continentIdCounter, String name, int bonus) {
        this.d_id = continentIdCounter;
        this.d_name = name;
        this.d_bonus = bonus;
        this.d_countries = new ArrayList<>();
    }
    
    public int getId() { return d_id; }
    public String getName() { return d_name; }
    public int getBonus() { return d_bonus; }
    public List<Country> getCountries() { return d_countries; }
    
    public void addCountry(Country country) { d_countries.add(country); }
    public void removeCountry(Country country) { d_countries.remove(country); }
}
