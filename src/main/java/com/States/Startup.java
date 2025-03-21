package com.States;

import com.gameplay.Command;
import com.gameplay.GameEngine;
import com.gameplay.Player;
import com.maps.MapReader;

public class Startup implements Phase {
    @Override
    public void addGamePlayer(GameEngine engine, Command l_command) {
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
    public void loadMap(GameEngine engine, Command l_command) {
        MapReader l_mapreader = new MapReader();
        l_mapreader.loadMap(l_command.d_argArr.getFirst());
        engine.d_countryList = l_mapreader.getCountriesMap().values().stream().toList();
        if (engine.d_countryList.isEmpty()) {
            System.out.println("Empty map loaded. Please try again.");
            return;
        }
        l_mapreader.showMap();
    }

    @Override
    public void displayMap() {

    }

    @Override
    public void assignCountries() {

    }

}
