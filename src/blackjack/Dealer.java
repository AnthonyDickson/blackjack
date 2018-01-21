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
        return 0; // Dealers do not bet.
    }

    @Override
    public Move getMove() {
        return (m.handValue(this) < 17) ? Move.HIT : Move.STAND;
    }
}