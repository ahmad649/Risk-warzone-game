package com.States;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;

public interface Phase {
    abstract public String currentPhase();

    default void addGamePlayer(Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    default void loadMap(Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    default void displayMap() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    default void assignCountries() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    default void endTurn(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    default void assignReinforcements() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    default void createOrder(Parsing l_parsing) {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    }

    default boolean executeOrder() {
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");        return false;
    }

    default void addGamePlayer(GameEngine engine, Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    default void showMap(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    default boolean saveMap(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    return false;
    }
    
    default boolean validateMap(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");        return false;
    }

    default void editMap(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

    default void editCountry(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    };

    default void editContinent(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };

    default void editor(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };
    default void startGame(){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");
    };
    default void returnToMenu(GameEngine engine){
        engine.d_phase = new Menu(engine);
    }
    default void editNeighbor(Parsing l_parsing){
        System.out.println("Cannot " + Thread.currentThread().getStackTrace()[1].getMethodName() +", currently in "+ currentPhase()+" phase!");    }

}
