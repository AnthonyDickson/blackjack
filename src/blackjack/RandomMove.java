package blackjack;

/**
 * An AI controlled player in a game of blackjack. 
 * This AI player chooses a move at random.
 * 
 * @author Anthony Dickson.
 */
public class RandomMove extends AIPlayer {
    public RandomMove(Manager m) {
        super(m);

        name = "HDF7823HDF&^*2";
    }

    @Override
    public int getBet(int maxBet) {
        return (int)(0.1 * maxBet);
    }

    @Override
    public Move getMove() {
        int n = r.nextInt(3);

        if (n == 0) {
            return Move.HIT;
        } else if (n == 1) {
            return Move.DOUBLE;
        }

        return Move.STAND;
    }
}