package com.States;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gameplay.GameEngine;
import com.gameplay.Parsing;
import com.maps.MapReader;

@JsonTypeName("menu")
/**
 * Defines Menu phase and its methods.
 */
public class Menu implements Phase {
    GameEngine d_engine;

    /**
     * Menu no-params constructor for serialization
     */
    public Menu(){}

    /**
     * Instantiates a new Menu.
     *
     * @param p_engine the engine
     */
    public Menu(GameEngine p_engine){
        this.d_engine = p_engine;
        System.out.println("""
                -----------------------------------------------------------------------
                                                MAIN MENU
                -----------------------------------------------------------------------
                Commands:

                startgame
                starttournament
                editor
                quit
                -----------------------------------------------------------------------
                """
        );
    }

    /**
     * d_engine getter method
     * @return GameEngine type object
     */
    public GameEngine getD_engine() {
        return d_engine;
    }

    /**
     * d_engine setter method
     * @param d_engine takes a GameEngine object to set it
     */
    public void setD_engine(GameEngine d_engine) {
        this.d_engine = d_engine;
    }

    /**
     * Starts the map editor and transitions to the Preload phase.
     * The map editor will be used to create or modify maps for the game.
     */
    public void editor(){
        d_engine.d_phase = new Preload(d_engine,new MapReader());
    }

    /**
     * Starts the game and transitions to the Startup phase.
     * This method begins the game by initializing the necessary game components.
     */
    public void startGame(){
        d_engine.d_phase = new Startup(d_engine); 
    }

    public void startTournament(Parsing p_parsing) {
        d_engine.d_phase = new TournamentMode();
    }

    /**
     * Current phase string.
     *
     * @return the string
     */
    public String currentPhase() {
        return "Menu";
    }
}
