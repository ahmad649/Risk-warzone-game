package com.gameplay;

import java.util.List;

import com.model.Country;
import com.model.Continent;
import com.States.Phase;

/**
 * GameState acts as a container for all the data needed
 * to save and restore the state of the game. It includes
 * players, countries, continents, and the current phase.
 */
public class GameState {

    private List<Player> d_players;
    private List<Country> d_countries;
    private List<Continent> d_continents;
    private Phase d_currentPhase;

    /**
     * Default no-argument constructor required for Jackson deserialization.
     */
    public GameState() {
    }

    /**
     * Gets the list of players saved in the game state.
     *
     * @return a list of type Player containing the players saved.
     */
    public List<Player> getD_players() {
        return d_players;
    }

    /**
     * Sets the list of players to be saved in the game state.
     *
     * @param d_players a list of Player objects to store.
     */
    public void setD_players(List<Player> d_players) {
        this.d_players = d_players;
    }

    /**
     * Gets the list of countries saved in the game state.
     *
     * @return a list of type Country containing the countries saved.
     */
    public List<Country> getD_countries() {
        return d_countries;
    }

    /**
     * Sets the list of countries to be saved in the game state.
     *
     * @param d_countries a list of Country objects to store.
     */
    public void setD_countries(List<Country> d_countries) {
        this.d_countries = d_countries;
    }

    /**
     * Gets the list of continents saved in the game state.
     *
     * @return a list of type Continent containing the continents saved.
     */
    public List<Continent> getD_continents() {
        return d_continents;
    }

    /**
     * Sets the list of continents to be saved in the game state.
     *
     * @param d_continents a list of Continent objects to store.
     */
    public void setD_continents(List<Continent> d_continents) {
        this.d_continents = d_continents;
    }

    /**
     * Gets the current phase of the game.
     *
     * @return the Phase object representing the current game phase.
     */
    public Phase getD_currentPhase() {
        return d_currentPhase;
    }

    /**
     * Sets the current phase of the game.
     *
     * @param d_currentPhase the Phase object representing the current game phase to be saved.
     */
    public void setD_currentPhase(Phase d_currentPhase) {
        this.d_currentPhase = d_currentPhase;
    }
}
