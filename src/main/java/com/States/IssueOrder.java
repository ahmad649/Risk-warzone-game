package com.States;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;
import com.gameplay.Player;
import com.model.Continent;
import com.model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class IssueOrder implements Phase {

    GameEngine engine;
    public ArrayList<String> possibleOrders = new ArrayList<>(List.of("deploy","advance"));


    public IssueOrder(GameEngine engine) {
        if (engine.d_playersList.isEmpty()) {
            System.out.println("No players available. Exiting game loop.");
            engine.l_phase = new Startup(engine);
            return;
        }
        this.engine = engine;
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
    public boolean createOrder(Parsing l_parsing) {
        for (Player l_player : engine.d_playersList) {
            if (l_player.getReinforcements() > 0) {
                l_player.issue_order(engine, l_parsing);
                //TODO: MAYBE CREATE A ENDTURN COMMAND
                return false;
            }
        }
        return true;
    }
}
