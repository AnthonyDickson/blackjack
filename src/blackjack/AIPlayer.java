package blackjack;

import java.util.Random;

import blackjack.Manager.MOVES;

/**
 * An AI controlled player in a game of blackjack.
 */
public class AIPlayer extends Player {
    static String[] names = { 
        "Aaron Campbell", 
        "Harold	Flores",
        "Richard Lewis",
        "James Long",
        "Rose Clark",
        "Dorothy Alexander",
        "Norma Hernandez",
        "Maria Johnson",
        "Doris Carter",
        "Jason Bell"
    };

    static Random r = new Random(2018);

    public AIPlayer(Manager m) {
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
        return names[r.nextInt(names.length)];
    }
}