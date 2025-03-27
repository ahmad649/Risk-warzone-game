package com;

import com.gameplay.GameEngine;

/**
 * Main class to run the application
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
