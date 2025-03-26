package com.gameplay;

import com.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Advance extends Order{
    private final GameEngine d_gameEngine;
    private final Player d_player;
    private final Country d_countryFrom;
    private Country d_countryTo;
    private final String d_countryNameFrom, d_countryNameTo;
    private final int d_numArmies;

    public Advance(GameEngine p_gameEngine, Player p_player, String p_countryFrom, String p_countryTo, int p_numArmies) {
        this.d_gameEngine = p_gameEngine;
        this.d_player = p_player;
        this.d_countryFrom = p_player.getCountryByName(p_countryFrom);
        this.d_countryNameFrom = p_countryFrom;
        this.d_countryNameTo = p_countryTo;
        this.d_numArmies = p_numArmies;

        for (Player l_player : p_gameEngine.getPlayersList()) {
            if (l_player.getCountryByName(p_countryTo) != null) {
                this.d_countryTo = l_player.getCountryByName(this.d_countryNameTo);
            }
        }
    }

    @Override
    public boolean isValid() {
        // Ensure the player owns the source country before advancing
        if (!this.d_player.ownsCountry(this.d_countryNameFrom)) {
            System.out.println("\nError: Player does not own country " + this.d_countryNameFrom);
            return false;
        }

        // If the target country is owned by player in the diplomacy list
        ArrayList<String> diplomacyPlayerNames = new ArrayList<>();
        for (Player l_player : this.d_player.getD_diplomacyPlayers()) {
            diplomacyPlayerNames.add(l_player.getName());
        }

        if (diplomacyPlayerNames.contains(this.d_countryTo.getOwner().getName())) {
            System.out.println("\nError: " + this.d_countryNameTo + " is owned by a player that's in the diplomacy list");
            return false;
        }

        // Source and target country are the same
        if (this.d_countryFrom.getName().equals(this.d_countryTo.getName())) {
            System.out.println("\nError: Source and target country are the same");
            return false;
        }

        // Ensure the country has enough reinforcements
        if (this.d_numArmies > this.d_countryFrom.getArmies()) {
            System.out.println("\nError: Not enough armies are available in " + this.d_countryNameFrom);
            return false;
        }

        //Ensure both countries are adjacent
        if (!this.d_countryFrom.isNeighbor(this.d_countryTo.getName())){
            System.out.println("\nError: Countries are not adjacent");
            return false;
        }

        return true;
    }

    @Override
    public void execute(){
        if (isValid()) {
            ArrayList<String> l_countryNamesOwned = new ArrayList<>();
            for (Country l_country : this.d_player.getOwnedCountries()) {
                l_countryNamesOwned.add(l_country.getName());
            }
            if (l_countryNamesOwned.contains(this.d_countryTo.getName())) {
                // Move armies
                this.d_countryFrom.setArmies(this.d_countryFrom.getArmies() - this.d_numArmies);
                this.d_countryTo.setArmies(this.d_countryTo.getArmies() + this.d_numArmies);
            } else {
                // Battle
                int l_attackingWinningChance = (int) Math.round(this.d_numArmies * 0.6);
                int l_defendingWinningChance = (int) Math.round(this.d_countryTo.getArmies() * 0.7);

                int l_attackingArmies = this.d_numArmies - l_defendingWinningChance;
                int l_defendingArmies = this.d_countryTo.getArmies() - l_attackingWinningChance;

                if (l_defendingArmies <= 0) {
                    // Attacker wins

                    // Give random card to player
                    Card l_card = this.getRandomCard();
                    this.d_player.addCards(l_card);

                    // Set number of armies
                    this.d_countryFrom.setArmies(this.d_countryFrom.getArmies() - this.d_numArmies);
                    this.d_countryTo.setArmies(l_attackingArmies);

                    // Add and remove country
                    this.d_player.addCountryToOwnedCountries(this.d_countryTo);
                    this.d_countryTo.getOwner().removeCountry(this.d_countryTo.getName());

                    // Change owner
                    this.d_countryTo.setOwner(this.d_player);
                } else {
                    // Defender wins

                    // Set number of armies
                    this.d_countryFrom.setArmies(l_attackingArmies + (this.d_countryFrom.getArmies() - this.d_numArmies));
                    this.d_countryTo.setArmies(l_defendingArmies);
                }
            }
        }

    }

    public Card getRandomCard() {
        ArrayList<Card> cards = new ArrayList<>(List.of(Card.BOMB, Card.BLOCKADE, Card.AIRLIFT, Card.DIPLOMACY));

        Random random = new Random();
        return cards.get(random.nextInt(cards.size()));
    }
}
