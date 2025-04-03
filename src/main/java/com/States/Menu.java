package com.States;

import com.gameplay.GameEngine;
import com.gameplay.Parsing;
import com.maps.MapReader;

/**
 * Defines Menu phase and its methods.
 */
public class Menu implements Phase {
    GameEngine d_engine;

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
