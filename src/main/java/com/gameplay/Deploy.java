package com.gameplay;

public class Deploy extends Order{

    public Deploy(String p_orderType, String p_countryName, int p_numArmy, Player p_player){
        super(p_orderType, p_countryName, p_numArmy, p_player);
    }

    @Override
    public void execute(){
        System.out.println(d_player.getName() + " is deploying " + d_numArmy + " armies to country " + d_countryName);

        // Ensure the player owns the country before deploying
        if (!d_player.ownsCountry(d_countryName)) {
            System.out.println("Error: Player does not own country " + d_countryName);
            return;
        }

        // Deploy armies
        //        d_player.removeReinforcement(d_numArmy);
        d_player.addArmiesToCountry(d_countryName, d_numArmy);
        System.out.println("Successfully deployed " + d_numArmy + " armies to country " + d_countryName);
        System.out.println("-----------------------------------------------------------------------------");
    }

}
