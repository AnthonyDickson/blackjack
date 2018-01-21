package blackjack;

/**
 * Main application class.
 * Runs a game of blackjack.
 */
public class Blackjack {
   public static void main(String[] args) {
       Manager m = new Manager();
       m.addPlayer(new HumanPlayer(m));
       m.addPlayer(new RandomMove(m));
       m.play();
   }    
}