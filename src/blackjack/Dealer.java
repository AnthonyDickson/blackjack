package blackjack;

import blackjack.Manager.MOVES;

/**
 * A dealer in a game of blackjack.
 */
public class Dealer extends Player {
    public Dealer(Manager m) {
        super(m);
    }

    @Override
    public int getBet() {
        return 0;
    }

    @Override
    public MOVES getMove() {
        return MOVES.STAND;
    }

    @Override
    public boolean promotesAce(int value) {
        return true;
    }

    @Override
    public String getName() {
        return "Dealer";
    }
}