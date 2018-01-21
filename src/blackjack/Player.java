package blackjack;

/**
 * A Player in a game of blackjack.
 * 
 * @author Anthony Dickson
 */
public abstract class Player {
    final Manager m;

    String name;
    int wins;
    int losses;
    
    public Player(Manager m) {
        this.m = m;
        this.wins = 0;
        this.losses = 0;
    }
    
    /**
     * Get the player's bet as requested by the manager.
     * 
     * @param maxBet The maximum bet a player can make.
     * @return the player's bet.
     */
    public abstract int getBet(int maxBet);
    
    /**
     * Get the player's move as requested by the manager.
     * 
     * @return the player's move.
     */
    public abstract Move getMove();

    /**
     * Gives the player the result of the last round.
     * 
     * @param result The result of the last round.
     */
    public void sendResult(Status result) {
        if (result == Status.WON) {
            wins++;
        } else {
            losses++;
        }
    }
    
    /**
     * Gets the win ratio of the player.
     * 
     * @return the win ratio (0.0-1.0) of the player.
     */
    public double getWinRatio() {
        return (double) wins / (wins + losses);
    }

    /** Reset win/losses */
    public void resetWinLoss() {
        wins = 0;
        losses = 0;
    }
    
    /**
     * Get the player's name.
     * 
     * @return the player's name as a string.
     */
    public String getName() {
        return name;
    }
}