package blackjack;

import blackjack.Manager.MOVES;

/**
 * A Player in a game of blackjack.
 */
public abstract class Player {
    final Manager m;

    String name;
    
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
     * Get the player's name.
     * 
     * @return the player's name as a string.
     */
    public String getName() {
        return name;
    }
}