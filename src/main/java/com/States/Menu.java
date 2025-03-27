package com.States;

import com.gameplay.GameEngine;
import com.maps.MapReader;

public class Menu implements Phase {
    GameEngine engine;
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
    public void editor(){
        engine.d_phase = new Preload(engine,new MapReader());
    }
    public void startGame(){
        engine.d_phase = new Startup(engine); 
    }
    public String currentPhase() {
        return "Menu";
    }
}
