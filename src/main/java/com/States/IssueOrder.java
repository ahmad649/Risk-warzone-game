package com.States;

import com.gameplay.*;
import com.model.Continent;
import com.model.Country;

import java.util.*;

/**
 * Defines IssueOrder phase and its methods.
 */
public class IssueOrder implements Phase {

    GameEngine d_engine;
    
    /**
     * Queue of players.
     */
    public Queue<Player> d_players;

    /**
     * Current phase string.
     *
     * @return the string
     */ 
    public String currentPhase() {
        return "IssueOrder";
    }


    /**
     * Current player for the turn.
     */
    public Player d_current_player;


    /**
     * next_player method is used to switch to the next player in the queue.
     */
    public void next_player(){
        d_current_player = d_players.poll();
        if(d_current_player == null){return;}
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(d_current_player.getName()+"'s turn to play");
        System.out.println("CARDS OWNED: ");
        for (Card l_card : d_current_player.getCards()) {
            System.out.print(l_card + " ");
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
    }

    /**
     * Used to switch to IssueOrder phase and assign reinforcements to the players.
     * Prints the commands available to the player.
     * @param p_engine p_engine the GameEngine object that manages the game's state, players, and phases
     */
    public IssueOrder(GameEngine p_engine) {
        if (p_engine.d_playersList.isEmpty()) {
            System.out.println("No players available. Exiting game loop.");
            p_engine.d_phase = new Startup(p_engine);
            return;
        }
        this.d_engine = p_engine;
        System.out.println("""
                \n-----------------------------------------------------------------------
                                             ISSUE ORDERS
                -----------------------------------------------------------------------
                Commands:

                showmap
                endturn
                menu
                deploy
                advance
                bomb
                diplomacy
                airlift
                blockade
                -----------------------------------------------------------------------
                """
        );
        d_players = new LinkedList<>(p_engine.d_playersList);
        next_player();
        assignReinforcements();
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
     * End the turn and switch to the next player.
     * If all players have played, switch to ExecuteOrder phase.
     * If a player has conquered all countries, the player wins the game.
     */
    @Override
    public void endTurn() {
        next_player();
        if(d_players.isEmpty()&&d_current_player == null) {
            d_engine.d_phase = new ExecuteOrder(d_engine);
            if (d_engine.d_phase.currentPhase().equals("ExecuteOrder")) {
                while (true) {
                    if (d_engine.d_phase.executeOrder()) {
                        HashSet<Player> l_processedPlayers = new HashSet<>();
                        for (Country l_country : d_engine.d_countryList){
                            if (l_country.getOwner()!=null) {
                                l_processedPlayers.add(l_country.getOwner());
                            }
                        }
                        if (l_processedPlayers.size()==1){
                            System.out.println(((Player)l_processedPlayers.toArray()[0]).getName() + " CONQUERED ALL COUNTRIES AND WON THE GAME!!!!");
                            System.out.println("THANKS FOR PLAYING");
                            d_engine.d_phase = new Menu(d_engine);
                            return;
                        }
                        d_engine.d_phase = new IssueOrder(d_engine);
                        break;
                    }
                }
            }
        }

    }

    /**
     * Calculates the reinforcements for each player.
     * The player receives 5 reinforcements and an additional reinforcement for each continent owned.
     */
    @Override
    public void assignReinforcements() {
        System.out.println("-----------------------------------------------------------------------------");
        for (Player l_player : d_engine.d_playersList) {
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

    /**
     * Creates an order for the current player based on the provided parsing input.
     * Adds the player back to the queue and moves to the next player.
     * @param p_parsing parsed commands for the order
     */
    @Override
    public void createOrder(Parsing p_parsing) {
        d_current_player.issue_order(d_engine, p_parsing);
        d_players.add(d_current_player);
        next_player();
    }
}
