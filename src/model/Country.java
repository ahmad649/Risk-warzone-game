package model;

import java.util.*;

// Represents a Country in the game
public class Country {
    private int id;
    private String name;
    private Continent continent;
    private Player owner;
    private int armies;
    private List<Country> neighbors;
    
    public Country(int countryIdCounter, String name, Continent continent) {
        this.id = countryIdCounter;
        this.name = name;
        this.continent = continent;
        this.armies = 0;
        this.neighbors = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public Continent getContinent() { return continent; }
    public Player getOwner() { return owner; }
    public int getArmies() { return armies; }
    public List<Country> getNeighbors() { return neighbors; }
    
    public void setOwner(Player owner) { this.owner = owner; }
    public void setArmies(int armies) { this.armies = armies; }
    public void addNeighbor(Country neighbor) { neighbors.add(neighbor); }
    public void removeNeighbor(Country neighbor) { neighbors.remove(neighbor); }
}
