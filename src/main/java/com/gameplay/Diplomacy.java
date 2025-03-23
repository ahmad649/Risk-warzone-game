package com.gameplay;

import java.util.Objects;

import static com.gameplay.GameEngine.d_playersList;

public class Diplomacy extends Order {

    private final Player d_currentPlayer;
    private Player d_targetPlayer;

    public Diplomacy(Player p_currentPlayer, String p_targetPlayerName) {
        super("negotiate", "none", 0, p_currentPlayer);
        this.d_currentPlayer = p_currentPlayer;
        for (Player l_player: d_playersList) {
            if (l_player.getName().equals(p_targetPlayerName)) {
                this.d_targetPlayer = l_player;
            }
        }
    }

    @Override
    public void execute() {
        // Check if the player owns a diplomacy card
        if (!d_player.getD_cards().contains(Card.DIPLOMACY)) {
            System.out.println("\nError: Player " + d_player.getName() + " does not own " + Card.DIPLOMACY + " card");
            return;
        }

        // Check if the current and target player are the same
        if (Objects.equals(this.d_currentPlayer.getName(), this.d_targetPlayer.getName())) {
            System.out.println("\nError: Target player cannot be the same as current player");
            return;
        }

        // Add players to diplomacy list
        this.d_currentPlayer.addDiplomacyPlayers(this.d_targetPlayer);
        this.d_targetPlayer.addDiplomacyPlayers(this.d_currentPlayer);
        System.out.println("\nSuccess: Now, " + this.d_currentPlayer.getName() + " cannot attack " + this.d_targetPlayer.getName() + " until the end of the turn and vice versa");

        // Remove diplomacy card from the current player
        d_player.getD_cards().remove(Card.DIPLOMACY);
    }
}
