package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Manager for a game of blackjack.
 */
public class Manager { 
    public static enum MOVES { SURRENDER, STAND, HIT, SPLIT, DOUBLE_DOWN };

    Deck deck;
    ArrayList<Player> players;
    HashMap<Player, ArrayList<Card>> hands;
    HashMap<Player, Integer> bets;
    HashMap<Player, Integer> scores;

    /** 
     * Set up a game of blackjack.
     * Players also need to be added with addPlayer()
     * @see Manager.addPlayer(Player p)
     */
    public Manager() {
        deck = new MultiDeck(4);
        players = new ArrayList<>();
        hands = new HashMap<>();
        bets = new HashMap<>();
        scores = new HashMap<>();

        addPlayer(new Dealer(this));
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
        deck.shuffle();
        getBets();
        dealCards();
        
        // for (Player p : players) {
        //     MOVES move = p.getMove();
        // }
    }

    private void getBets() {
        for (Player p : players) {
            bets.put(p, p.getBet());
        }
    }

    private void dealCards() {
        for (Player p : players) {
            for (int i = 0; i < 2; i++) {
                hands.get(p).add(deck.next());
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
     * Counts aces as being worth one. The player class should decide whether 
     * or not they want to 'promote' an ace to 11 points.
     * 
     * @param p The player whose hand we should compute the value of.
     * @return The min value of the players hand.
     */
    public int handValue(Player p) {
        int value = 0;
        
        for (Card c : hands.get(p)) {
            value += c.getValue();            
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
            int value = handValue(p);

            if (hasAce(p) && p.promotesAce(value)) {
                value += 10;
            }

            if (value > max) {
                winner = p;
            }
        }

        return winner;
    }

    private boolean hasAce(Player p) {
        return getHand(p).stream()
            .filter((c) -> c.getRank() == Rank.ACE)
            .collect(Collectors.toList())
            .size() > 0;
    }
}