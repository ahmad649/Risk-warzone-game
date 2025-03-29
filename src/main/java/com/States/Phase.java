package com.States;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;

/**
 * Phase interface that defines the methods for the game phases
 */
public interface Phase {
    /**
     * Method to return the current phase
     * @return the current phase
     */
    abstract public String currentPhase();

    /**
     * Method to add a player to the game
     * @param l_parsing the parsing object
     */
    default void addGamePlayer(Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to load a map
     * @param l_parsing the parsing object
     */
    default void loadMap(Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    /**
     * Method to display the map
     */
    default void displayMap() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to assign countries to the players
     */
    default void assignCountries() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to end the turn
     */
    default void endTurn(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    /**
     * Method to assign reinforcements to the players
     */
    default void assignReinforcements() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to create an order
     * @param l_parsing
     */
    default void createOrder(Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    /**
     * Method to execute an order
     * @return true if the all order is executed, false otherwise
     */
    default boolean executeOrder() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");        return false;
    }

    /**
     * Method to add a player to the game
     * @param engine the game engine
     * @param l_parsing the parsing object
     */
    default void addGamePlayer(GameEngine engine, Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to show the map
     */
    default void showMap(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to save the map
     * @param l_parsing the parsing object
     * @return true if the map is saved, false otherwise
     */
    default boolean saveMap(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    return false;
    }
    
    /**
     * Method to validate the map
     * @return true if the map is validated, false otherwise
     */
    default boolean validateMap(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");        return false;
    }

    /**
     * Method to edit the map
     * @param l_parsing the parsing object
     */
    default void editMap(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to edit a country
     * @param l_parsing the parsing object
     */
    default void editCountry(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    };

    /**
     * Method to edit a continent
     * @param l_parsing the parsing object
     */
    default void editContinent(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };

    /**
     * Method to begin editing
     * @param l_parsing the parsing object
     */
    default void editor(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };

    /**
     * Method to start the game
     */
    default void startGame(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };
    
    /**
     * Method to return to the menu
     * @param engine the game engine
     */
    default void returnToMenu(GameEngine engine){
        engine.d_phase = new Menu(engine);
    }
    
    /**
     * Method to edit a neighbor
     * @param l_parsing the parsing object
     */
    default void editNeighbor(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

}
