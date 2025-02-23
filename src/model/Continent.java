package model;

import java.util.*;

// Represents a Continent in the game
public class Continent {
    private int id;
    private String name;
    private int bonus;
    private List<Country> countries;
    
    public Continent(int continentIdCounter, String name, int bonus) {
        this.id = continentIdCounter;
        this.name = name;
        this.bonus = bonus;
        this.countries = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public int getBonus() { return bonus; }
    public List<Country> getCountries() { return countries; }
    
    public void addCountry(Country country) { countries.add(country); }
    public void removeCountry(Country country) { countries.remove(country); }
}
