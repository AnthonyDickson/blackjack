package blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck made up of multiple decks of playing cards.
 * 
 * @author Anthony Dickson.
 */
public class MultiDeck extends Deck {
    
    public MultiDeck() {
        this(2);
    }

    public MultiDeck(int numDecks) {
        i = 0;
        cards = new ArrayList<>(52 * numDecks);

        for (int ii = 0; ii < numDecks; i++) {
            addDeck();
        }
        
        Collections.shuffle(cards);
    }   
    
    private void addDeck() {        
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public static void main(String[] args) {
        Deck d = new MultiDeck(4);
        System.out.println(d);
        System.out.println(d.hasNext());
        System.out.println(d.next());
    }
}