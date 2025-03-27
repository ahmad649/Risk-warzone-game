package com.States;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;
import com.gameplay.Player;
import com.model.Continent;
import com.model.Country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class IssueOrder implements Phase {

    GameEngine engine;
    public ArrayList<Player> p_players;

    public String currentPhase() {
        return "IssueOrder";
    }
    Iterator<Player> p_players_iterator;
    public Player current_player;

    public IssueOrder(GameEngine engine) {
        if (engine.d_playersList.isEmpty()) {
            System.out.println("No players available. Exiting game loop.");
            engine.d_phase = new Startup(engine);
            return;
        }
        this.engine = engine;
        p_players = new ArrayList<>(engine.d_playersList);
        p_players_iterator = p_players.iterator();
        current_player = p_players_iterator.next();
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
    public void endTurn() {
        p_players_iterator.remove();
        if(p_players.isEmpty()) {
            engine.d_phase = new ExecuteOrder(engine);
        }
    }

    @Override
    public void assignReinforcements() {
        System.out.println("-----------------------------------------------------------------------------");
        for (Player l_player : engine.d_playersList) {
            int l_reinforcements = 5;

            HashSet<Country> l_processedCountries = new HashSet<>();
            for (Country l_country : l_player.d_ownedCountries) {
                if (l_processedCountries.contains(l_country)) {
                    continue;
                }
                Continent l_checkingContinent = l_country.getContinent();
                boolean l_givebonus = true;
                for (Country otherCountry : l_checkingContinent.getCountries()) {
                    l_processedCountries.add(otherCountry);
                    if (otherCountry.getOwner() != l_player) {
                        l_givebonus = false;
                        break;
                    }
                }
                if (l_givebonus) {
                    l_reinforcements += l_checkingContinent.getBonus();
                }
            }

            l_player.setReinforcements(l_reinforcements);
            System.out.println(l_player.getName() + " receives " + l_reinforcements + " reinforcements.");
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    @Override
    public void createOrder(Parsing l_parsing) {
        current_player.issue_order(engine, l_parsing);
        if (p_players_iterator.hasNext()) {
            current_player = p_players_iterator.next();
        }
        System.out.println("Next turn will be" + current_player.getName());
    }
}
