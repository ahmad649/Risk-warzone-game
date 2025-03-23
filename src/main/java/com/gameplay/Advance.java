package com.gameplay;

import com.model.Country;

public class Advance extends Order{

    private String p_countryTo;

    public Advance(String p_orderType, String p_countryFrom, String p_countryTo , int p_numArmy, Player p_player){
        super(p_orderType, p_countryFrom, p_numArmy, p_player);
        this.p_countryTo = p_countryTo;
    }

    @Override
    public void execute(){
        System.out.println(d_player.getName() + " is moving " + d_numArmy + " armies from country " + d_countryName + " to country " + p_countryTo);
        Country d_sourceCountry = d_player.getCountryByName(d_countryName);
        Country d_destiantionCountry = d_player.getCountryByName(p_countryTo);

        // Ensure the player owns the country before advancing
        if (!d_player.ownsCountry(d_countryName)) {
            System.out.println("Error: Player does not own country " + d_countryName);
            return;
        }

        // Ensure the country has enough reinforcements
        if (d_sourceCountry.getArmies() < d_numArmy) {
            System.out.println("Error: Not enough armies in available in the country.");
            return;
        }

        //Ensure both countries are adjacent
        if (!d_sourceCountry.isNeighbor(p_countryTo)){
            System.out.println("Error: Countries are not adjacent");
            return;
        }

        //Advance logic
        if (d_player.ownsCountry(p_countryTo)){
            d_sourceCountry.removeReinforcements(d_numArmy);
            d_destiantionCountry.addReinforcements(d_numArmy);
            System.out.println("Successfully moved " + d_numArmy + " armies from country " + d_countryName + " to country " + p_countryTo);
            System.out.println("-----------------------------------------------------------------------------");
        }
    }
}
