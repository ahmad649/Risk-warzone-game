package com;

import com.gameplay.GameEngine;

/**
 * The Main class serves as the entry point to run the application.
 * It initializes the GameEngine and starts the game loop.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        GameEngine MapEngine = new GameEngine();
        MapEngine.gameLoop();
    }
}
