package com.gameplay;

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
    }

    @Override
    public boolean isValid() {
        // Check if the player owns a diplomacy card
        if (!this.d_currentPlayer.getD_cards().contains(Card.DIPLOMACY)) {
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

    @Override
    public void execute() {
        if (this.isValid()) {
            // Add players to diplomacy list
            this.d_currentPlayer.addDiplomacyPlayers(this.d_targetPlayer);
            this.d_targetPlayer.addDiplomacyPlayers(this.d_currentPlayer);
            System.out.println("\nSuccess: Now, " + this.d_currentPlayer.getName() + " cannot attack " + this.d_targetPlayer.getName() + " until the end of the turn and vice versa");

            // Remove diplomacy card from the current player
            this.d_currentPlayer.getD_cards().remove(Card.DIPLOMACY);
        }
    }
}
