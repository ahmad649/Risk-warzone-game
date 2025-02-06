package gameplay;

public class Order {
    private int d_countryID;
    private int d_numArmy;
    public String d_orderType;

    /**
     * Deploy armies at d_countryID
     */
    public void execute(){
        if (d_orderType.equals("deploy")){
            // TODO: Implement this method
            throw new UnsupportedOperationException("Method not yet implemented");
        }
    }
}
