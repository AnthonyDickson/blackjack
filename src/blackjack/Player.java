package blackjack;

import blackjack.Manager.MOVES;

/**
 * A Player in a game of blackjack.
 */
public abstract class Player {
    final Manager m;
    
    public Player(Manager m) {
        this.m = m;
    }

    /**
     * Get the player's bet as requested by the manager.
     * 
     * @return the player's bet.
     */
    public abstract int getBet();

    /**
     * Get the player's move as requested by the manager.
     * 
     * @return the player's move.
     */
    public abstract MOVES getMove();

    /**
     * Whether or not the player will promote their ace to have a value of 11.
     * 
     * @param value The hand value before promoting the ace.
     * @return whether or not the player will promote their ace.
     */
    public abstract boolean promotesAce(int value);

    /**
     * Get the player's name.
     * 
     * @return the player's name as a string.
     */
    public abstract String getName();
}