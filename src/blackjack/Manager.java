package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Manager for a game of blackjack.
 * 
 * @author Anthony Dickson
 */
public class Manager { 
    private static final double BLACKJACK_PAYOUT = 2.5;
    private static final double PAYOUT = 2.0;
    private static final boolean SILENT_MODE = true; // Suppresses output to stdout.
    // TESTING
    private static final int NUM_TESTS = 100000;

    private Deck deck;
    private ArrayList<Player> players;
    private Dealer dealer;
    private HashMap<Player, ArrayList<Card>> hands;
    private HashMap<Player, Integer> bets;
    private HashMap<Player, Integer> credits;
    private HashMap<Player, Status> states; 

    private static void print(String msg) {
        if (!SILENT_MODE) {
            System.out.println(msg);
        }
    }

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
        credits.put(dealer, 0); // Used to keep track of dealer winnings/losses.
    }

    public void addPlayer(Player p) {
        if (players.size() == 8) {
            return;
        }

        players.add(p);
        hands.put(p, new ArrayList<>());
        bets.put(p, 0);
        credits.put(p, 1000);
        print(p.getName() + " has joined the game!");
    }

    /** Starts a game of blackjack. */
    public void play() {
        int i = 0;

        while (i++ < NUM_TESTS) {
            print("\nStarting a new round of blackjack...");
            shuffle();
            getBets();
            dealCards();
            announceCards();
            checkBlackjack();
            handleMoves();
            resolve();
        }

        printStats();
    }
        
    /** Shuffle the deck. */
    private void shuffle() {
        print("\nShuffling deck...");

        deck.shuffle();
    }

    /** Get each players bet for each player. */
    private void getBets() {
        print("\nGetting bets...");

        for (Player p : players) {
            int bet = p.getBet(credits.get(p));

            if (bet > credits.get(p)) {
                bet = credits.get(p);
            }

            bets.put(p, bet);
            deductCredits(p, bet);
            print(p.getName() + " has bet " + bet + " credit(s).");
        }
    }
    
    /**
     * Give credits to a player.
     * 
     * @param p The player to give credits to.
     * @param amount How many credits to give.
     */
    private void addCredits(Player p, int amount) {
        credits.put(p, credits.get(p) + amount);
        credits.put(dealer, credits.get(dealer) - amount);
    }
    
    /**
     * Deduct credits from a player.
     * 
     * @param p The player to deduct credits from.
     * @param amount How many credits to deduct.
     */
    private void deductCredits(Player p, int amount) {
        addCredits(p, -amount);
    }
    
    /** Deal cards to each player. */
    private void dealCards() {
        print("\nDealing cards...");
        
        for (Player p : players) {
            dealCards(p);
        }

        dealCards(dealer);
    }

    /**
     * Deal cards to a single player and marks them as ready to start playing.
     * 
     * @param p The player to deal cards to.
     */
    private void dealCards(Player p) {
        hands.get(p).clear();

        for (int i = 0; i < 2; i++) {
            hands.get(p).add(deck.next());
        }

        states.put(p, Status.READY);
    }

    /** Announce each player's hand, and the dealer's first card. */
    private void announceCards() {
        print("\nThe dealer has: [" + hands.get(dealer).get(0) + ", ...]");

        for (Player p : players) {
            print(p.getName() + " has: " + hands.get(p));
        }
    }

    /** Check if anyone has blackjack. */
    private void checkBlackjack() {
        boolean dealerBlackjack = false;

        if (hands.get(dealer).get(0).getRank() == Rank.ACE) {
            print("Dealer has a face-up ace. Checking hole card...");

            if (handValue(dealer) == 21) {
                print("Dealer has blackjack!");
                states.put(dealer, Status.WON);
                dealerBlackjack = true;
            }
        }

        for (Player p : players) {
            if (handValue(p) == 21) {
                states.put(p, Status.WON);

                if (dealerBlackjack) {
                    push(p);
                } else {
                    print(p.getName() + " has blackjack!");
                    win(p, (int)(BLACKJACK_PAYOUT * bets.get(p)));
                }
            } else if (dealerBlackjack) {
                lose(p);
            }          
        }
    }

	private void win(Player p, int amount) {
        print(p.getName() + " has won " + amount + " credits!");
        states.put(p, Status.WON);
        addCredits(p, amount);
        p.sendResult(Status.WON);
        dealer.sendResult(Status.LOST);                
	}
    
	private void lose(Player p) {
        print(p.getName() + " has lost.");
		states.put(p, Status.LOST);
        p.sendResult(Status.LOST);
        dealer.sendResult(Status.WON);                
	}

	private void push(Player p) {
        int bet = bets.get(p);
        print("Push!");
        print(p.getName() + " is returned their bet of " + bet + " credit(s).");
		addCredits(p, bet);
	}

    /**
     * Handle moves for each player in sequence and then the dealer.
     */
    private void handleMoves() {
        for (Player p : players) {
            handleMoves(p);
        }

        if (states.containsValue(Status.STAND)) {
            print(dealer.getName() + "'s cards: " + hands.get(dealer));
            handleMoves(dealer);
        } else {
            print("The dealer has won!");
        }
    }

    /**
     * Handle the moves for a given player until they stand or go bust.
     * 
     * @param p The player whose moves should be handled.
     */
    private void handleMoves(Player p) {
        if (states.get(p) == Status.READY) {
            print("\n" + p.getName() + " is starting their turn.");
        }

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
    
    /**
     * The given player stands and will no longer be able to take anymore 
     * action for the rest of this round.
     * 
     * @param p The player that will stand.
     */
    private void stand(Player p) {
        print(p.getName() + " stands on " + handValue(p));                        
        states.put(p, Status.STAND);
    }
    
    /**
     * The given player hits, receiving another card; the player goes bust
     * if the new total of their hand exceeds 21.
     * 
     * @param p The player that will hit.
     */
    private void hit(Player p) {
        Card next = deck.next();
        print(p.getName() + " hits and gets: " + next);
        hands.get(p).add(next);
        
        if (handValue(p) > 21) {
            print(p.getName() + " has gone bust.");
            states.put(p, Status.BUST);
            lose(p);
        }
    }

    /**
     * The given player doubles down, doubling their bet, drawing one and 
     * only one more card, and is forced to stand.
     * 
     * @param p The player that will double down.
     */
    private void doubleDown(Player p) {
        print(p.getName() + " doubles down and raises their bet" + 
                           " to " + bets.get(p) + " credits.");
        deductCredits(p, bets.get(p));
        bets.put(p, 2 * bets.get(p));
        hit(p);

        if (states.get(p) != Status.BUST) {
            stand(p);
        }
    }

    /**
     * Resolve the current round of blackjack.
     * 
     */
    private void resolve() {
        int dealerHandValue = handValue(dealer);

        for (Player p : players) {
            if (states.get(p) == Status.STAND) {
                if ((states.get(dealer) == Status.BUST) ||
                    (handValue(p) > dealerHandValue)) {
                    win(p, (int)(PAYOUT * bets.get(p)));
                } else if (handValue(p) < dealerHandValue) {
                    lose(p);
                } else {
                    push(p);
                }
            }
        }
    }

    private void printStats() {
        System.err.println(dealer.getName() + "'s credit balance: " + credits.get(dealer));

        for (Player p : players) {
            System.err.println(p.getName() + "'s credit balance: " + credits.get(p));
        }

        System.err.println(dealer.getName() + "'s win ratio: " + dealer.getWinRatio());

        for (Player p : players) {
            System.err.println(p.getName() + "'s win ratio: " + p.getWinRatio());
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