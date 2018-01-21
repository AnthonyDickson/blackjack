package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Manager for a game of blackjack.
 */
public class Manager { 
    private static enum Status { READY, STAND, BUST, WON, LOST };

    private Deck deck;
    private ArrayList<Player> players;
    private Dealer dealer;
    private HashMap<Player, ArrayList<Card>> hands;
    private HashMap<Player, Integer> bets;
    private HashMap<Player, Integer> credits;
    private HashMap<Player, Status> states; 

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
        states = new HashMap<>();

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
        System.out.println("\nStarting a new round of blackjack...");
        shuffle();
        getBets();
        dealCards();
        announceCards();
        checkBlackjack();
        handleMoves();
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
        }
    }
    
    /** Deal cards to each player. */
    private void dealCards() {
        System.out.println("\nDealing cards...");
        
        for (Player p : players) {
            dealCards(p);
        }

        dealCards(dealer);
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
        
        states.put(p, Status.READY);
    }

    /** Announce each player's hand, and the dealer's first card. */
    private void announceCards() {
        System.out.println("The dealer has: [" + hands.get(dealer).get(0) + ", ...]");

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
                states.put(p, Status.WON);

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
                states.put(p, Status.LOST);
                System.out.println(p.getName() + " has lost.");
            }          
        }
    }

    /**
     * Handle moves for each player in sequence and then the dealer.
     */
    private void handleMoves() {
        for (Player p : players) {
            handleMoves(p);
        }

        handleMoves(dealer);
    }

    private void handleMoves(Player p) {
        while (states.get(p) == Status.READY) {
            Move move = p.getMove();

            if (move == Move.STAND) {
                stand(p);
            } else if (move == Move.HIT) {
                hit(p);
            } else if (move == Move.DOUBLE) {
                doubleDown(p);
            }
        }
    }
    
    private void stand(Player p) {
        states.put(p, Status.STAND);
        System.out.println(p.getName() + " stands on " + handValue(p));                        
    }
    
    private void hit(Player p) {
        Card next = deck.next();
        hands.get(p).add(next);
        System.out.println(p.getName() + " hits and gets: " + next);
        
        if (handValue(p) > 21) {
            states.put(p, Status.BUST);
            System.out.println(p.getName() + " has gone bust.");
        }
    }

    private void doubleDown(Player p) {
        bets.put(p, 2 * bets.get(p));
        System.out.println(p.getName() + " doubles down and raises their bet" + 
                           " to " + bets.get(p) + " credits.");
        hit(p);

        if (states.get(p) != Status.BUST) {
            stand(p);
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
}