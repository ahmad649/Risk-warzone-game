package com.States;

import com.gameplay.GameEngine;
import com.maps.MapReader;

/**
 * Defines Menu phase and its methods.
 */
public class Menu implements Phase {
    GameEngine engine;

    /**
     * Instantiates a new Menu.
     *
     * @param engine the engine
     */
    public Menu(GameEngine engine){
        this.engine = engine;
        System.out.println("""
                -----------------------------------------------------------------------
                                                MAIN MENU
                -----------------------------------------------------------------------
                Commands:

                startgame
                editor
                quit
                -----------------------------------------------------------------------
                """
        );
    }
    /**
     * Starts the map editor.
     */
    public void editor(){
        engine.d_phase = new Preload(engine,new MapReader());
    }
    /**
     * Starts the game.
     */
    public void startGame(){
        engine.d_phase = new Startup(engine); 
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
