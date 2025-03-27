package com.gameplay;

/**
 * The special orders represented by cards.
 *
 * <p>
 * A player receives a card at the end of his
 * turn if they successfully conquered at least one territory during their turn. Cards can be played as
 * the players are giving orders. Each card has a different effect that influences the course of the
 * game
 * </p>
 */
public enum Card {
    /**
     * Bomb card.
     */
    BOMB,
    /**
     * Blockade card.
     */
    BLOCKADE,
    /**
     * Airlift card.
     */
    AIRLIFT,
    /**
     * Diplomacy card.
     */
    DIPLOMACY
}
