package blackjack;

/**
 * A human controlled player in a game of blackjack.
 */
public class HumanPlayer extends Player {
    public HumanPlayer(Manager m) {
        this(m, "Player");
    }

    public HumanPlayer(Manager m, String name) {
        super(m);

        this.name = name;
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