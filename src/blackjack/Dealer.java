package blackjack;

import blackjack.Manager.MOVES;

/**
 * A dealer in a game of blackjack.
 */
public class Dealer extends Player {
    public Dealer(Manager m) {
        super(m);
    }

    public MOVES getMove() {
        return MOVES.STAND;
    }
    
    public boolean promotesAce(int value) {
        return true;
    }
}