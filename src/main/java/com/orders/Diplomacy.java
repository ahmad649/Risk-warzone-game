package com.orders;

import com.gameplay.GameEngine;
import com.gameplay.Player;

import java.util.Objects;

/**
 * Diplomacy class is used to prevent attacks between the current player and another player until the end of
 * the turn.
 */
public class Diplomacy extends Order {

    private final Player d_currentPlayer;
    private Player d_targetPlayer;
    private final String d_targetPlayerName;
    private final GameEngine d_gameEngine;
    private String d_LogINFO;
    /**
     * Instantiates a new Diplomacy.
     *
     * @param p_gameEngine       the game engine that controls the game flow
     * @param p_currentPlayer    the current player
     * @param p_targetPlayerName the target player name
     */
    public Diplomacy(GameEngine p_gameEngine, Player p_currentPlayer, String p_targetPlayerName) {
        this.d_currentPlayer = p_currentPlayer;
        this.d_gameEngine = p_gameEngine;
        this.d_targetPlayerName = p_targetPlayerName;
        d_LogINFO = String.format(
                "-----------------------------------------------------------------------\n" +
                        "ISSUED: Diplomacy: IssuingPlayer: %s, TargetPlayer: %s\n" +
                        "-----------------------------------------------------------------------\n",
                d_currentPlayer.getName(), d_targetPlayerName
        );
    }

    /**
     * Gets the log info.
     */
    @Override
    public String getLogInfo() {
        return d_LogINFO;
    }


    /**
     * Checks if the diplomacy order is valid.
     *
     * @return true if the diplomacy order is valid, false otherwise
     */
    @Override
    public boolean isValid() {
        // Check if the player owns a diplomacy card
        if (!this.d_currentPlayer.getCards().contains(Card.DIPLOMACY)) {
            System.out.println("\nError: Player " + this.d_currentPlayer.getName() + " does not own " + Card.DIPLOMACY + " card");
            return false;
        }

        // Check if target player exists
        for (Player l_player: this.d_gameEngine.d_playersList) {
            if (l_player.getName().equals(this.d_targetPlayerName)) {
                this.d_targetPlayer = l_player;
            }
        }

        if (this.d_targetPlayer == null) {
            System.out.println("\nError: Target player " + this.d_targetPlayerName + " does not exist");
            return false;
        }

        // Check if the current and target player are the same
        if (Objects.equals(this.d_currentPlayer.getName(), this.d_targetPlayer.getName())) {
            System.out.println("\nError: Target player cannot be the same as current player");
            return false;
        }

        return true;
    }

    /**
     * Executes the diplomacy order.
     */
    @Override
    public void execute() {
        System.out.println(d_LogINFO);
        if (this.isValid()) {
            d_LogINFO = "\n-----------------------------------------------------------------------------";
            // Add players to diplomacy list
            this.d_currentPlayer.addDiplomacyPlayers(this.d_targetPlayer);
            this.d_targetPlayer.addDiplomacyPlayers(this.d_currentPlayer);
            d_LogINFO += "\nSuccess: Now, " + this.d_currentPlayer.getName() + " cannot attack " + this.d_targetPlayer.getName() + " until the end of the turn and vice versa";

            // Remove diplomacy card from the current player
            this.d_currentPlayer.removeCard(Card.DIPLOMACY);
            d_LogINFO += "\n-----------------------------------------------------------------------------";
            System.out.println(d_LogINFO);
        }
    }
}
