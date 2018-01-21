package blackjack;

/**
 * A dealer in a game of blackjack.
 */
public class Dealer extends Player {
    public Dealer(Manager m) {
        super(m);

        name = "Dealer";
    }

    @Override
    public int getBet(int maxBet) {
        return 0;
    }

    @Override
    public Move getMove() {
        return Move.STAND;
    }
}