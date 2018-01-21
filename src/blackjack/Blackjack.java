package blackjack;

/**
 * Main application class.
 * Runs a game of blackjack.
 */
public class Blackjack {
    public static void main(String[] args) {
        Manager m = new Manager();
        WizardOfOdds woo = new WizardOfOdds(m);
        woo.load();
        m.addPlayer(woo);
        m.warmup();

        m.addPlayer(new RandomMove(m));
        m.addPlayer(new HumanPlayer(m));
        //    m.addPlayer(new AlwaysDouble(m));
        //    m.addPlayer(new AlwaysStand(m));
        //    m.addPlayer(new BasicStrategy(m));
        m.play();
        woo.save();
    }    
}