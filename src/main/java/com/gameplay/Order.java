package com.gameplay;

/**
 * Order class containing the orders information for its future identification inside the main.com.gameplay loop
 * String d_countryName target country name to assign the order
 * int d_numArmy number of reinforcements involved in the order
 * String d_orderType type of the order being issued and executed
 * Player d_player player owner of the order
 */
public abstract class Order {
//    public String d_countryName;
//    public int d_numArmy;
    /**
     * The D order type.
     */
//    public String d_orderType;
//    public Player d_player;

    /**
     * Order constructor
     *
     * @param p_orderType   String type of the order being issued and executed
     * @param p_countryName String target country name to assign the order
     * @param p_numArmy     Integer number of reinforcements involved in the order
     * @param p_player      Player object owner of the order
     */
//    public Order(String p_orderType, String p_countryName, int p_numArmy, Player p_player) {
//        this.d_orderType = p_orderType;
//        this.d_countryName = p_countryName;
//        this.d_numArmy = p_numArmy;
//        this.d_player = p_player;
//    }


    public abstract boolean isValid();

    /**
     * Deploy armies at d_countryName
     */
    public abstract void execute();

/*
    /**
     * executeDeploy method
     * Executes the deployment of orders issues by the players
     * Makes basic verifications if the player is able to execute the order based on how many reinforcements wants to send vs how many reinforcements possesses
     */
    /*
    private void executeDeploy() {
        System.out.println(d_player.getName() + " is deploying " + d_numArmy + " armies to country " + d_countryName);

        // Ensure the player owns the country before deploying
        if (!d_player.ownsCountry(d_countryName)) {
            System.out.println("Error: Player does not own country " + d_countryName);
            return;
        }

        // Ensure the player has enough reinforcements
        if (d_player.getReinforcements() < d_numArmy) {
            System.out.println("Error: Not enough armies in reinforcement pool.");
            return;
        }

        // Deploy armies
        //        d_player.removeReinforcement(d_numArmy);
        d_player.addArmiesToCountry(d_countryName, d_numArmy);
        System.out.println("Successfully deployed " + d_numArmy + " armies to country " + d_countryName);
        System.out.println("-----------------------------------------------------------------------------");
    }

    /**
     * Handles the advance order (moving armies between owned countries).
     */
    /*
    private void executeAdvance() {
        System.out.println(d_player.getName() + " is advancing " + d_numArmy + " armies to country " + d_countryName);

        // TODO: Implement movement logic
    }

    /**
     * Get order type.
     *
     * @return the order type
     */
/*
    public String getOrderType() { return d_orderType; }

    /**
     * Get country name.
     *
     * @return the country name
     */
    /*
    public String getCountryName() { return d_countryName; }

    /**
     * Get num army.
     *
     * @return the num army
     */
    /*
    public int getNumArmy() { return d_numArmy; }

    /**
     * Get player.
     *
     * @return the player
     */
    /*
    public Player getPlayer() { return d_player; }
*/
}
