package blackjack;

/**
 * An AI controlled player in a game of blackjack. 
 * This AI player always stands.
 * 
 * @author Anthony Dickson.
 */
public class AlwaysDouble extends AIPlayer {
    public AlwaysDouble(Manager m) {
        super(m);
    }

    @Override
    public int getBet(int maxBet) {
        return (int)(0.1 * maxBet);
    }

    @Override
    public Move getMove() {
        return Move.STAND;
    }
}