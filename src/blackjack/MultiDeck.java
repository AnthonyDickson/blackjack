package blackjack;

import java.util.ArrayList;
import java.util.Collections;

import blackjack.Card.RANKS;
import blackjack.Card.SUITS;

/**
 * Represents a deck made up of multiple decks of playing cards.
 * 
 * @author Anthony Dickson.
 */
public class MultiDeck extends Deck {
    
    public Deck() {
        Deck(2);
    }

    public Deck(int numDecks) {
        i = 0;
        cards = new ArrayList<>(52 * numDecks);

        for (int ii = 0; ii < numDecks; i++) {
            addDeck();
        }
        
        Collections.shuffle(cards);
    }   
    
    private void addDeck() {        
        for (RANKS rank : RANKS.values()) {
            for (SUITS suit : SUITS.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public static void main(String[] args) {
        Deck d = new Deck();
        System.out.println(d);
        System.out.println(d.hasNext());
        System.out.println(d.next());
    }
}