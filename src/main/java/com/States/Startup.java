package com.States;

import com.gameplay.Command;
import com.gameplay.GameEngine;
import com.gameplay.Player;
import com.maps.MapReader;
import com.model.Continent;
import com.model.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Startup implements Phase {

    GameEngine engine;

    public Startup(GameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void addGamePlayer(Command l_command) {
        if (l_command.d_argsLabeled.containsKey("-add")) {
            String l_playername = l_command.d_argsLabeled.get("-add").getFirst();
            engine.d_playersList.add(new Player(l_playername));
            System.out.println("Player added: " + l_playername);
        } else if (l_command.d_argsLabeled.containsKey("-remove")) {
            String l_playerName = l_command.d_argsLabeled.get("-remove").getFirst();
            engine.d_playersList.removeIf(p -> p.getName().equals(l_playerName));
            System.out.println("Player removed: " + l_playerName);
        }
    }

    @Override
    public void loadMap(Command l_command) {
        Preload l_mapreader = new Preload(new MapReader());
        l_mapreader.loadMap(l_command.d_argArr.getFirst());
        engine.d_countryList = l_mapreader.d_countries.values().stream().toList();
        if (engine.d_countryList.isEmpty()) {
            System.out.println("Empty map loaded. Please try again.");
            return;
        }
        l_mapreader.showMap();
    }

    @Override
    public void displayMap() {
        HashSet<Country> l_processedCountries = new HashSet<>();
        for (Country l_country : engine.d_countryList) {
            if (l_processedCountries.contains(l_country)) {
                continue;
            }
            Continent l_checkingContinent = l_country.getContinent();
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println(" Continent's name : " + l_checkingContinent.getName());
            System.out.println("----------------------------------Countries----------------------------------");
            for (Country otherCountry : l_checkingContinent.getCountries()) {
                l_processedCountries.add(otherCountry);
                System.out.println(otherCountry);
            }
        }
    }

    @Override
    public void assignCountries() {
        if (engine.d_playersList.isEmpty() || engine.d_countryList.isEmpty()) {
            System.out.println("Cannot assign countries. Ensure players and countries are available.");
            return;
        }

        int l_index = 0;

        List<Country> l_shuffledCountries = new ArrayList<>(engine.d_countryList); // Create a mutable copy
        Collections.shuffle(l_shuffledCountries); // Shuffle the countries

        for (Country l_country : l_shuffledCountries) {
            Player l_player = engine.d_playersList.get(l_index);
            l_player.d_ownedCountries.add(l_country);
            l_country.setOwner(l_player);
            l_index = (l_index + 1) % engine.d_playersList.size();
        }
        System.out.println("All countries have been assigned to players.");
    }
}
