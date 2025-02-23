package gameplay;

public class Order {
    private String d_countryName;
    private int d_numArmy;
    public String d_orderType;
    private Player d_player;

    public Order(String p_orderType, String p_countryName, int p_numArmy, Player p_player) {
        this.d_orderType = p_orderType;
        this.d_countryName = p_countryName;
        this.d_numArmy = p_numArmy;
        this.d_player = p_player;
    }

    /**
     * Deploy armies at d_countryName
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
        d_player.removeReinforcement(d_numArmy);
        d_player.addArmiesToCountry(d_countryName, d_numArmy);
        System.out.println("Successfully deployed " + d_numArmy + " armies to country " + d_countryName);
    }

    /**
     * Handles the advance order (moving armies between owned countries).
     */
    private void executeAdvance() {
        System.out.println(d_player.getName() + " is advancing " + d_numArmy + " armies to country " + d_countryName);

        // TODO: Implement movement logic
    }

    // Getters
    public String getOrderType() { return d_orderType; }
    public String getCountryName() { return d_countryName; }
    public int getNumArmy() { return d_numArmy; }
    public Player getPlayer() { return d_player; }

}
