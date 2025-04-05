package com.gameplay;

/**
 * Order class containing the orders information for its future identification inside the main.com.gameplay loop
 * String d_countryName target country name to assign the order
 * int d_numArmy number of reinforcements involved in the order
 * String d_orderType type of the order being issued and executed
 * Player d_player player owner of the order
 */
public abstract class Order {

    /**
     * Instantiates a new Order object.
     */
    public Order() {}
    /**
     * Retrieves the log information related to the order.
     *
     * @return a string that contains the details of the order's execution and any related information
     */
    public abstract String getLogInfo();
    /**
     * To verify whether the given order is valid or not
     *
     * @return <code>true</code> if order is valid; otherwise <code>false</code>
     */
    public abstract boolean isValid();

    /**
     * To execute the given order command.
     */
    public abstract void execute();

}
