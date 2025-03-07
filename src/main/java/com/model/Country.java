package com.model;

import com.gameplay.Player;

import java.util.*;

// Represents a Country in the game
public class Country {
    private int d_id;
    private String d_name;
    private Continent d_continent;
    private Player d_owner;
    private int d_armies;
    private List<Country> d_neighbors;

    public Country(int p_countryIdCounter, String p_name, Continent p_continent) {
        this.d_id = p_countryIdCounter;
        this.d_name = p_name;
        this.d_continent = p_continent;
        this.d_armies = 0;
        this.d_neighbors = new ArrayList<>();
    }

    public int getId() {
        return d_id;
    }

    public String getName() {
        return d_name;
    }

    public Continent getContinent() {
        return d_continent;
    }

    public Player getOwner() {
        return d_owner;
    }

    public int getArmies() {
        return d_armies;
    }

    public List<Country> getNeighbors() {
        return d_neighbors;
    }

    public void setContinent(Continent p_continent) {
        this.d_continent = p_continent;
    }

    public void setOwner(Player p_owner) {
        this.d_owner = p_owner;
    }

    public void setArmies(int p_armies) {
        this.d_armies = p_armies;
    }

    public void addNeighbor(Country p_neighbor) {
        if (!this.d_neighbors.contains(p_neighbor)) {  // Ensure no duplicate neighbors
            this.d_neighbors.add(p_neighbor);
        }
    }

    public void removeNeighbor(Country p_neighbor) {
        d_neighbors.remove(p_neighbor);
    }

    public void addReinforcements(int p_reinforcements) {
        d_armies += p_reinforcements;
    }
}
