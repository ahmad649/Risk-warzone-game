package com.gameplay;

/**
 * Deploy class is used to place some armies on one of the current player’s territories.
 */
public class Deploy extends Order{
    private final Player d_player;
    private final String d_countryName;
    private final int d_numArmy;

    /**
     * Instantiates a new Deploy object.
     *
     * @param p_player      the current player
     * @param p_countryName the target country name
     * @param p_numArmy     the number of armies
     */
    public Deploy(Player p_player, String p_countryName, int p_numArmy){
        this.d_player = p_player;
        this.d_countryName = p_countryName;
        this.d_numArmy = p_numArmy;
    }

    @Override
    public boolean isValid() {
        // Ensure the player owns the country before deploying
        if (!this.d_player.ownsCountry(this.d_countryName)) {
            System.out.println("\nError: Player does not own country " + this.d_countryName);
            return false;
        }

        // Ensure the player has enough reinforcements
        if (this.d_player.getReinforcements() < this.d_numArmy) {
            System.out.println("\nError: Player reinforcement limit exceeded");
            return false;
        }

        return true;
    }

    @Override
    public void execute(){
        if (this.isValid()) {
            System.out.println(d_player.getName() + " is deploying " + this.d_numArmy + " armies to country " + this.d_countryName);

            // Deploy armies
            //        d_player.removeReinforcement(d_numArmy);
            d_player.addArmiesToCountry(this.d_countryName, this.d_numArmy);
            System.out.println("Successfully deployed " + this.d_numArmy + " armies to country " + this.d_countryName);
            System.out.println("-----------------------------------------------------------------------------");
        }
    }
}
