package gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Player {

    private Queue<Order> d_playerOrders = new LinkedList<>();
    private int d_numReinforcement;


    /**
     * Takes input from user in this format "deploy countryID num" and adds a command to playerOrders
     * Decreases the appropriate number of reinforcements from the numReinforcement
     */
    public void issue_order(){
        // TODO: Use InputOutput class to take the input and continue further
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Removes the first command from the playerOrders and returns it
     * @return The order at the front of the queue
     */
    public Order next_order(){
        // TODO: Implement this method
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}
