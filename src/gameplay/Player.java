package gameplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Player {

    private Queue<Order> d_playerOrders = new LinkedList<>();
    private int d_numReinforcement;

    private String d_name;

    public Player(String p_name) {
        d_name = p_name;
        d_numReinforcement = 0;
    }

    public String getName() {
        return d_name;
    }

    public void setReinforcements(int p_reinforcements) {
        d_numReinforcement = p_reinforcements;
    }

    public int getReinforcements() {
        return d_numReinforcement;
    }

    /**
     * Takes input from user in this format "deploy countryID num" and adds a command to playerOrders
     * Decreases the appropriate number of reinforcements from the numReinforcement
     */
    public void issue_order(){
        // TODO: Use InputOutput class to take the input and continue further
        System.out.println(d_name + ", enter your order (deploy <countryID> <num>):");
        String command = InputOutput.get_user_command();

        if (InputOutput.is_deploy_command_valid(command)) {
            String[] parts = command.split(" ");
            int countryID = Integer.parseInt(parts[1]);
            int num = Integer.parseInt(parts[2]);

            if (num <= d_numReinforcement) {
                Order newOrder = new Order(countryID, num);
                d_playerOrders.add(newOrder);
                d_numReinforcement -= num;
                System.out.println("Order added: Deploy " + num + " armies to country " + countryID);
            } else {
                System.out.println("Not enough reinforcements available.");
            }
        } else {
            System.out.println("Invalid order. Please try again.");
        }
    }

    /**
     * Removes the first command from the playerOrders and returns it
     * @return The order at the front of the queue
     */
    public Order next_order(){
        // TODO: Implement this method
        if (!d_playerOrders.isEmpty()) {
            return d_playerOrders.poll();
        }
        return null;
    }
}
