package model;

import java.util.*;


// Deploy order implementation
class Deploy implements Order {
    private Player player;
    private Country targetCountry;
    private int armies;
    
    public Deploy(Player player, Country targetCountry, int armies) {
        this.player = player;
        this.targetCountry = targetCountry;
        this.armies = armies;
    }
    
    @Override
    public void execute() {
        if (player.getOwnedCountries().contains(targetCountry)) {
            targetCountry.setArmies(targetCountry.getArmies() + armies);
            player.setReinforcementArmies(player.getReinforcementArmies() - armies);
        }
    }
}
