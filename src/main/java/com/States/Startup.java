package com.States;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;
import com.gameplay.Player;
import com.maps.MapReader;
import com.model.Continent;
import com.model.Country;
import com.strategy.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Defines Startup phase and its methods.
 */
public class Startup implements Phase {

    GameEngine d_engine;

    /**
     * Instantiates a new Startup.
     *
     * @param p_engine the engine
     */
    public Startup(GameEngine p_engine) {
        this.d_engine = p_engine;
        //clearing previous game
        this.d_engine.clearPlayerList();
        System.out.println("""
                -----------------------------------------------------------------------
                                                STARTUP
                -----------------------------------------------------------------------
                Commands:
                
                loadmap
                gameplayer
                showmap
                assigncountries
                menu
                -----------------------------------------------------------------------
                """
        );
    }

    /**
     * Current phase string.
     *
     * @return the string
     */
    public String currentPhase() {
        return "Startup";
    }

    /**
     * Add game player.
     *
     * @param p_parsing the parsing
     */
    @Override
    public void addGamePlayer(Parsing p_parsing) {
        if (p_parsing.d_argsLabeled.containsKey("-add")) {
            List<String> l_playerNames = p_parsing.d_argsLabeled.get("-add");
            List<String> l_playerStrategies = p_parsing.getArgsLabeled().getOrDefault("-P",new ArrayList<>());
            for (int i = 0; i< l_playerNames.size(); i+=1) {
                Player l_player = new Player(l_playerNames.get(i));
                String l_strategy = "";
                if (i<l_playerStrategies.size()){
                    l_strategy = l_playerStrategies.get(i).toLowerCase();
                }
                switch (l_strategy) {
                    case "aggressive":
                        l_player.setPlayerStrategy(new AggressivePlayerStrategy(this.d_engine, l_player));
                        this.d_engine.getPlayersList().add(l_player);
                        break;
                    case "benevolent":
                        l_player.setPlayerStrategy(new BenevolentPlayerStrategy(this.d_engine, l_player));
                        this.d_engine.getPlayersList().add(l_player);
                        break;
                    case "random":
                        l_player.setPlayerStrategy(new RandomPlayerStrategy(this.d_engine, l_player));
                        this.d_engine.getPlayersList().add(l_player);
                        break;
                    case "cheater":
                        l_player.setPlayerStrategy(new CheaterPlayerStrategy(this.d_engine, l_player));
                        this.d_engine.getPlayersList().add(l_player);
                        break;
                    case "human":
                        l_player.setPlayerStrategy(new HumanPlayerStrategy(this.d_engine, l_player));
                        this.d_engine.getPlayersList().add(l_player);
                        break;
                    default:
                        l_player.setPlayerStrategy(new HumanPlayerStrategy(this.d_engine, l_player));
                        this.d_engine.getPlayersList().add(l_player);
                        break;

                }
                System.out.println("Player added: " + l_player.getName());
            }
        }
        if (p_parsing.d_argsLabeled.containsKey("-remove")) {
            for (String l_playername : p_parsing.d_argsLabeled.get("-remove")) {
                d_engine.d_playersList.removeIf(p -> p.getName().equals(l_playername));
                System.out.println("Player removed: " + l_playername);
            }
        }
    }

    /**
     * Load map.
     *
     * @param p_parsing the parsing
     */
    @Override
    public void loadMap(Parsing p_parsing) {
        Preload l_mapreader = new Preload(d_engine, new MapReader());
        l_mapreader.loadMap(p_parsing.d_argArr.getFirst());
        d_engine.d_countryList = l_mapreader.d_countries.values().stream().toList();
        if (d_engine.d_countryList.isEmpty()) {
            System.out.println("Empty map loaded. Please try again.");
            return;
        }
        this.displayMap();
    }

    /**
     * Display the map.
     */
    @Override
    public void displayMap() {
        HashSet<Country> l_processedCountries = new HashSet<>();
        for (Country l_country : d_engine.d_countryList) {
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

    /**
     * Assign countries to the players.
     */
    @Override
    public void assignCountries() {
        if (d_engine.d_playersList.isEmpty() || d_engine.d_countryList.isEmpty()) {
            System.out.println("Cannot assign countries. Ensure players and countries are available.");
            return;
        }

        int l_index = 0;
        int l_loop= 0;

        List<Country> l_shuffledCountries = new ArrayList<>(d_engine.d_countryList); // Create a mutable copy
        Collections.shuffle(l_shuffledCountries); // Shuffle the countries

        for (Country l_country : l_shuffledCountries) {
            if(l_loop>=4){
                break;
            }
            l_country.setArmies(5);
            Player l_player = d_engine.d_playersList.get(l_index);
            l_player.d_ownedCountries.add(l_country);
            l_country.setOwner(l_player);
            l_index = (l_index + 1) % d_engine.d_playersList.size();
            l_loop+=1;
        }
        System.out.println("All countries have been assigned to players.");
        d_engine.d_phase = new IssueOrder(d_engine);
    }
}
