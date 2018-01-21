package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Manager for a game of blackjack.
 */
public class Manager { 
    public static enum MOVES { SURRENDER, STAND, HIT, SPLIT, DOUBLE_DOWN };

    private Deck deck;
    private ArrayList<Player> players;
    private Dealer dealer;
    private HashMap<Player, ArrayList<Card>> hands;
    private HashMap<Player, Integer> bets;
    private HashMap<Player, Integer> scores;

    /** 
     * Set up a game of blackjack.
     * Players also need to be added with addPlayer()
     * @see Manager.addPlayer(Player p)
     */
    public Manager() {
        deck = new MultiDeck(4);
        players = new ArrayList<>();
        dealer = new Dealer(this);
        hands = new HashMap<>();
        bets = new HashMap<>();
        scores = new HashMap<>();

        hands.put(dealer, new ArrayList<>());
    }

    public void addPlayer(Player p) {
        players.add(p);
        hands.put(p, new ArrayList<>());
        bets.put(p, 0);
        scores.put(p, 1000);
        System.out.println(p.getName() + " has joined the game!");
    }

    /** Starts a game of blackjack. */
    public void play() {
        // while (true) {
            System.out.println("\nStarting a new round of blackjack...");
            shuffle();
            getBets();
            dealCards();
            announceCards();
            // for (Player p : players) {
                //     MOVES move = p.getMove();
                // }            
        // }
    }
        
    /** Shuffle the deck. */
    private void shuffle() {
        System.out.println("\nShuffling deck...");

        deck.shuffle();
    }

    /** Get each players bet for each player. */
    private void getBets() {
        System.out.println("\nGetting bets...");

        for (Player p : players) {
            bets.put(p, p.getBet());
            System.out.println(p.getName() + " has bet " + bets.get(p) + " credits!");
        }
    }
    
    /** Deal cards to each player. */
    private void dealCards() {
        System.out.println("\nDealing cards...");
        
        dealCards(dealer);

        for (Player p : players) {
            dealCards(p);
        }
    }

    /**
     * Deal cards to a single player.
     * 
     * @param p The player to deal cards to.
     */
    private void dealCards(Player p) {
        for (int i = 0; i < 2; i++) {
            hands.get(p).add(deck.next());
        }
    }

    /** Announce each player's hand, and the dealer's first card. */
    private void announceCards() {
        System.out.println("The dealer has: " + hands.get(dealer).get(0));
        System.out.println("Here are the player's hands:");

        for (Player p : players) {
            System.out.println(p.getName() + " has: " + hands.get(p));
        }
    }

    /**
     * Get the hand of cards for a given player.
     * 
     * @param p The player whose hand should be returned.
     * @return The player's hand.
     */
    public ArrayList<Card> getHand(Player p) {
        return hands.get(p);
    } 

    /**
     * Computes the value of a hand of the given player. 
     * Aces count as one by default unless the player signals they wish to 
     * count one of their aces as eleven.
     * 
     * @param p The player whose hand we should compute the value of.
     * @return The min value of the players hand.
     */
    public int handValue(Player p) {
        int value = 0;
        boolean hasAce = false;

        for (Card c : hands.get(p)) {
            value += c.getValue();

            if (c.getRank() == Rank.ACE) {
                hasAce = true;
            }
        }        

        if (hasAce && p.promotesAce(value)) {
            value += 10;
        }

        return value;
    }

    /**
     * Gets the player with the winning hand.
     * 
     * @return the winning player.
     */
    public Player getWinner() {
        Player winner = null;
        int max = -1;

        for (Player p : players) {
            if (handValue(p) > max) {
                winner = p;
            }
        }

        return winner;
    }
}