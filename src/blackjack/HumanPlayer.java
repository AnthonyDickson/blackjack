package blackjack;

import blackjack.Manager.MOVES;

/**
 * A human controlled player in a game of blackjack.
 */
public class HumanPlayer extends Player {
    String name;    

    public HumanPlayer(Manager m) {
        this(m, "Player");
    }

    public HumanPlayer(Manager m, String name) {
        super(m);

        this.name = name;
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
        return name;
    }
}