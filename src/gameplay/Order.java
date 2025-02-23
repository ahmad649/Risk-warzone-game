package gameplay;

public class Order {
    private int d_countryID;
    private int d_numArmy;
    public String d_orderType;
    private Player d_player;

    public Order(String p_orderType, int p_countryID, int p_numArmy, Player p_player) {
        this.d_orderType = p_orderType;
        this.d_countryID = p_countryID;
        this.d_numArmy = p_numArmy;
        this.d_player = p_player;
    }

    /**
     * Deploy armies at d_countryID
     */
    public void execute(){
        // TODO: Implement this method
        switch (d_orderType.toLowerCase()) {
            case "deploy":
                executeDeploy();
                break;
                case "advance":
                    executeAdvance();
                    break;
                // Add more cases here for future orders like "attack", "bomb", etc.
                default:
                    System.out.println("Unknown order type: " + d_orderType);
        }
    }

    private void executeDeploy() {
        System.out.println(d_player.getName() + " is deploying " + d_numArmy + " armies to country " + d_countryID);

        // Ensure the player owns the country before deploying
        if (!d_player.ownsCountry(d_countryID)) {
            System.out.println("Error: Player does not own country " + d_countryID);
            return;
        }

        // Ensure the player has enough reinforcements
        if (d_player.getReinforcementPool() < d_numArmy) {
            System.out.println("Error: Not enough armies in reinforcement pool.");
            return;
        }

        // Deploy armies
        d_player.removeReinforcement(d_numArmy);
        d_player.addArmiesToCountry(d_countryID, d_numArmy);
        System.out.println("Successfully deployed " + d_numArmy + " armies to country " + d_countryID);
    }

    /**
     * Handles the advance order (moving armies between owned countries).
     */
    private void executeAdvance() {
        System.out.println(d_player.getName() + " is advancing " + d_numArmy + " armies to country " + d_countryID);

        // TODO: Implement movement logic
    }

    // Getters
    public String getOrderType() { return d_orderType; }
    public int getCountryID() { return d_countryID; }
    public int getNumArmy() { return d_numArmy; }
    public Player getPlayer() { return d_player; }

}
