package blackjack;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * A human controlled player in a game of blackjack.
 * 
 * @author Anthony Dickson.
 */
public class HumanPlayer extends Player {
    private static Scanner in = new Scanner(System.in);
    
    public HumanPlayer(Manager m) {
        this(m, "Player");
    }
    
    public HumanPlayer(Manager m, String name) {
        super(m);
        
        this.name = name;
    }
    
    @Override
    public int getBet(int maxBet) {
        int bet = Integer.MAX_VALUE;
    
        do {
            try {
                System.out.print("Enter your bet (max=" + maxBet + "): ");
                bet = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Bet must be a whole number.");
                in.nextLine(); // Clear the buffer.
            }
        } while (bet < 1 || bet > maxBet);

        return bet;
    }
    
    @Override
    public Move getMove() {
        String move = "";
        System.out.println("Your hand: " + m.getHand(this));
        System.out.println("Your hand is worth: " + m.handValue(this));
        System.out.print("Will you (h)it, (d)ouble down or (s)tand? ");
        move = in.nextLine();
        
        while (!move.equals("h") && !move.equals("d") && !move.equals("s")) {
            System.out.println("Enter 'h' to hit, 'd' to double down, or 's' to stand.");
            move = in.nextLine();
        }

        if (move.equals("h")) {
            return Move.HIT;
        } else if (move.equals("d")) {
            return Move.DOUBLE;
        }

        return Move.STAND;
    }
}