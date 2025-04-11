package com.States;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;

/**
 * Phase interface that defines the methods for the game phases
 */
public interface Phase {
    /**
     * Method to return the current phase
     *
     * @return the current phase
     */
    abstract public String currentPhase();

    /**
     * Method to add a player to the game
     *
     * @param p_parsing the parsing object
     */
    default void addGamePlayer(Parsing p_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to load a map
     *
     * @param p_parsing the parsing object
     */
    default void loadMap(Parsing p_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    default void processIfNonHuman() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    default boolean checkIfNonHuman() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
        return false;
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
     *
     * @param p_parsing the p parsing
     */
    default void createOrder(Parsing p_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    /**
     * Method to execute an order
     *
     * @return true if the all order is executed, false otherwise
     */
    default boolean executeOrder() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");        return false;
    }

    /**
     * Method to add a player to the game
     *
     * @param p_engine  the game engine
     * @param p_parsing the parsing object
     */
    default void addGamePlayer(GameEngine p_engine, Parsing p_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to show the map
     */
    default void showMap(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to save the map
     *
     * @param p_parsing the parsing object
     * @return true if the map is saved, false otherwise
     */
    default boolean saveMap(Parsing p_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    return false;
    }

    /**
     * Method to validate the map
     *
     * @return true if the map is validated, false otherwise
     */
    default boolean validateMap(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");        return false;
    }

    /**
     * Method to edit the map
     *
     * @param p_parsing the parsing object
     */
    default void editMap(Parsing p_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    /**
     * Method to edit a country
     *
     * @param p_parsing the parsing object
     */
    default void editCountry(Parsing p_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    };

    /**
     * Method to edit a continent
     *
     * @param p_parsing the parsing object
     */
    default void editContinent(Parsing p_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };

    /**
     * Method to begin editing
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
     * Start tournament.
     *
     * @param p_parsing the parsed user command
     */
    default void startTournament(Parsing p_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };

    /**
     * Method to return to the menu
     *
     * @param p_engine the game engine
     */
    default void returnToMenu(GameEngine p_engine){
        p_engine.d_phase = new Menu(p_engine);
    }

    /**
     * Method to edit a neighbor
     *
     * @param p_parsing the parsing object
     */
    default void editNeighbor(Parsing p_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

}
