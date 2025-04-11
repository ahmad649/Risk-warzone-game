package com.orders;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "orderType"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Deploy.class, name = "deploy"),
        @JsonSubTypes.Type(value = Advance.class, name = "advance"),
        @JsonSubTypes.Type(value = Bomb.class, name = "bomb"),
        @JsonSubTypes.Type(value = Blockade.class, name = "blockade"),
        @JsonSubTypes.Type(value = Airlift.class, name = "airlift"),
        @JsonSubTypes.Type(value = Diplomacy.class, name = "diplomacy")
})

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
