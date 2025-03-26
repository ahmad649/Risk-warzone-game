package com.States;

import com.gameplay.Parsing;
import com.gameplay.GameEngine;

public interface Phase {
    abstract public String currentPhase();

    default void addGamePlayer(Parsing l_parsing) {
        System.out.println("DEFAULTBODY");
    }

    default void loadMap(Parsing l_parsing) {
        System.out.println("DEFAULTBODY");

    }

    default void displayMap() {
        System.out.println("DEFAULTBODY");
    }

    default void assignCountries() {
        System.out.println("DEFAULTBODY");
    }

    default void assignReinforcements() {
        System.out.println("DEFAULTBODY");
    }

    default boolean createOrder(Parsing l_parsing) {
        System.out.println("DEFAULTBODY");
        return false;
    }

    default boolean executeOrder() {
        System.out.println("DEFAULTBODY");
        return false;
    }

    default void addGamePlayer(GameEngine engine, Parsing l_parsing){
        System.out.println("DEFAULTBODY");
    }

    default void showMap(){
    System.out.println("DEFAULTBODY");
    }

    default boolean saveMap(String p_filename){
    System.out.println("DEFAULTBODY");
    return false;
    }

    default void addContinent(String p_name, int p_bonusValue){
    System.out.println("DEFAULTBODY");}

    default void removeContinent(String p_name){
    System.out.println("DEFAULTBODY");}

    default void addCountry(String p_name, String p_continentName){
    System.out.println("DEFAULTBODY");}

    default void removeCountry(String p_name){
    System.out.println("DEFAULTBODY");}

    default void addNeighbor(String p_countryName, String p_neighborName){
    System.out.println("DEFAULTBODY");}

    default void removeNeighbor(String p_countryName, String p_neighborName){
    System.out.println("DEFAULTBODY");}

    default boolean loadMap(String p_filename){
    System.out.println("DEFAULTBODY");
    return false;
    }
}
