package com.gameplay;

import com.model.Country;

import java.util.Random;

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
            d_destiantionCountry.setOwner(d_player);
            d_player.d_ownedCountries.add(d_destiantionCountry);
            System.out.println("Successfully moved " + d_numArmy + " armies from country " + d_countryName + " to country " + p_countryTo);
            System.out.println("-----------------------------------------------------------------------------");
        }else{
            //Battle Logic if the destination country is owned by another player
            System.out.println("Starting attack from country " + d_countryName + " to country " + p_countryTo);
            Country d_defenderCountry = d_sourceCountry.getNeighborByName(p_countryTo);
            Player p_defenderPlayer = d_defenderCountry.getOwner();

            Random random = new Random();
            int d_attackingArmies = d_numArmy;
            int d_defendingArmies = d_defenderCountry.getArmies();

            //Kill count to decide a winner after the attack
            int d_attackersKilled = 0;
            int d_defendersKilled = 0;

            //60% chances of killing a defender army
            for (int i = 0; i<d_attackingArmies; i++){
                if (random.nextDouble() < 0.60){
                    d_defendersKilled++;
                }
            }
            //70% chances of killing an attacker army
            for (int i = 0; i<d_defendingArmies; i++){
                if (random.nextDouble() < 0.60){
                    d_attackersKilled++;
                }
            }

            //Updating the armies after attack
            d_attackingArmies -= d_attackersKilled;
            d_defendingArmies -= d_defendersKilled;

            //After the attack results, the winner and owner of the defending country is set
            //If the attacker wins, the country is conquered
            if (d_defendingArmies <= 0){
                System.out.println("Country has been captured by " +d_player.getName() + "!!!");
                d_sourceCountry.removeReinforcements(d_numArmy);
                d_defenderCountry.setArmies(d_numArmy);
                d_player.d_ownedCountries.add(d_defenderCountry);
                d_defenderCountry.setOwner(d_player);
                p_defenderPlayer.d_ownedCountries.remove(d_defenderCountry);
            }else{
                System.out.println("Country was defended successfully!!!");
                d_defenderCountry.setArmies(d_defendingArmies);
                d_sourceCountry.setArmies(d_attackingArmies);
            }
        }
    }
}
