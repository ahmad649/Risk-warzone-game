package model;

import java.util.*;

// Represents a Player in the game
class Player {
    private String name;
    private List<Country> ownedCountries;
    private Queue<Order> orders;
    private int reinforcementArmies;
    
    public Player(String name) {
        this.name = name;
        this.ownedCountries = new ArrayList<>();
        this.orders = new LinkedList<>();
        this.reinforcementArmies = 0;
    }
    
    public String getName() { return name; }
    public List<Country> getOwnedCountries() { return ownedCountries; }
    public int getReinforcementArmies() { return reinforcementArmies; }
    
    public void addCountry(Country country) { ownedCountries.add(country); country.setOwner(this); }
    public void removeCountry(Country country) { ownedCountries.remove(country); country.setOwner(null); }
    public void setReinforcementArmies(int armies) { this.reinforcementArmies = armies; }
    
    public void issueOrder(Order order) { orders.add(order); }
    public Order nextOrder() { return orders.poll(); }
}
