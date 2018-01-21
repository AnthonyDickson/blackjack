package blackjack;

/**
 * Main application class.
 * Runs a game of blackjack.
 */
public class Blackjack {
   public static void main(String[] args) {
       Manager m = new Manager();
       m.addPlayer(new HumanPlayer(m, "Player"));
       m.addPlayer(new AIPlayer(m));
       m.play();
   }    
}