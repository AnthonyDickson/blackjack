package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Manager for a game of blackjack.
 */
public class Manager { 
    public static enum Move { SURRENDER, STAND, HIT, SPLIT, DOUBLE_DOWN };
    public static enum Status { READY, STAND, BUST, WON };

    private Deck deck;
    private ArrayList<Player> players;
    private Dealer dealer;
    private HashMap<Player, ArrayList<Card>> hands;
    private HashMap<Player, Integer> bets;
    private HashMap<Player, Integer> credits;
    private HashMap<Player, Status> statuses; 

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
        credits = new HashMap<>();
        state = new HashMap<>();

        hands.put(dealer, new ArrayList<>());
    }

    public void addPlayer(Player p) {
        if (players.size() == 8) {
            return;
        }

        players.add(p);
        hands.put(p, new ArrayList<>());
        bets.put(p, 0);
        credits.put(p, 1000);
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
            checkBlackjack();
            // for (Player p : players) {
                //     Move move = p.getMove();
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
            System.out.println(p.getName() + " has bet " + bets.get(p) + " credit(s)!");
            statuses.put(p, Status.READY);
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

    /** Check if anyone has blackjack. */
    private void checkBlackjack() {
        boolean dealerBlackjack = false;

        if (hands.get(dealer).get(0).getRank() == Rank.ACE) {
            System.out.println("Dealer has a face-up ace. Checking hole card...");

            if (handValue(dealer) == 21) {
                System.out.println("Dealer has blackjack!");
                dealerBlackjack = true;
            }
        }

        for (Player p : players) {
            if (handValue(p) == 21) {
                statuses.put(p, Status.WON);

                if (dealerBlackjack) {
                    System.out.println("Standoff!");
                    credits.put(p, credits.get(p) + bets.get(p));
                } else {
                    int amount = (int)(1.5 * bets.get(p));
                    credits.put(p, credits.get(p) + amount);                
                    System.out.println(p.getName() + " has blackjack!");
                    System.out.println(p.getName() + " has won " + amount + " credit(s)!");
                }
            } else if (dealerBlackjack) {
                statuses.put(p, Status.BUST);
                System.out.println(p.getName() + " has lost " + bets.get(p) + " credit(s).");
            }          
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
     * Aces count for eleven unless the hand totals over 21.
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

        if (hasAce && value > 21) {
            value -= 10;
        }

        return value;
    }

    /** Get the dealer's face up card. */
    public Card getDealerCard() {
        return hands.get(dealer).get(0);
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