package com.gameplay;

import java.util.ArrayList;
import java.util.List;

import com.States.ExecuteOrder;
import com.States.IssueOrder;
import com.States.Phase;
import com.States.Startup;
import com.model.Country;

/**
 * GameEngine class that works as the controller for the game flow
 * Manages the game stages and has the list for Player and Country objects:
 * d_playerList: list of player objects
 * d_countryList: list of country objects
 */
public class GameEngine {
    /**
     * A list of players.
     */
    public List<Player> d_playersList = new ArrayList<>();
    /**
     * A list of countries.
     */
    public List<Country> d_countryList = new ArrayList<>();

    private final Player d_neutralPlayer = new Player("Neutral");

    public Phase l_phase;

    public GameEngine(){
        l_phase = new Startup(this);
    }

    /**
     * Gets players list.
     *
     * @return the players list
     */
    public List<Player> getPlayersList() {
        return d_playersList;
    }

    public Player getneutralPlayer() {
        return this.d_neutralPlayer;
    }

    /**
     * Sets country list.
     *
     * @param countryList the country list
     */
    public void setCountryList(List<Country> countryList) {
        this.d_countryList = countryList;
    }

    /**
     * startup() method in charge of initiating the game taking no parameters
     * Manages player creation and controls the first stage of the game managing the users input through the InputOutput class methods
     * If the command given by the user is valid, Command class methods are called to determine either if the command adds or removes players
     * On the other hand, if the command corresponds to assigning countries, the process is called as well as the loop method running the main stage of the game
     */
    // Game starter

    public void gameLoop() {
        while (true) {
            Parsing l_parsing = null;
            while (l_parsing == null) {
                l_parsing = InputOutput.get_user_command();
            }
            if (l_parsing.d_commandType.equals("gameplayer")) {
                l_phase.addGamePlayer(l_parsing);
            } else if (l_parsing.d_commandType.equals("loadmap")) {
                l_phase.loadMap(l_parsing);
            } else if (l_parsing.d_commandType.equals("showmap")) {
                l_phase.displayMap();
            } else if (l_parsing.d_commandType.equals("assigncountries")) {
                l_phase.assignCountries();
                l_phase = new IssueOrder(this);
                l_phase.assignReinforcements();
            } else if (checkIssuable(l_parsing)) {
                if (l_phase.createOrder(l_parsing)) {
                    l_phase = new ExecuteOrder(this);
                    while (true) {
                        if (l_phase.executeOrder()) {
                            l_phase = new IssueOrder(this);
                            break;
                        }
                    }
                }
            } else if (l_parsing.d_commandType.equals("quit")) {
                return;
            } else {
                System.out.println("Invalid command. Try again.");
            }
        }
    }

    public boolean checkIssuable(Parsing l_parsing) {
        ArrayList<String> possibleOrders = new ArrayList<>(List.of("deploy","advance"));
        return possibleOrders.contains(l_parsing.d_commandType.toLowerCase());
    }

}
