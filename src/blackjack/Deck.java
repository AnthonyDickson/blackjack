package blackjack;

import java.util.ArrayList;
import java.util.Collections;

import blackjack.Card.RANKS;
import blackjack.Card.SUITS;

/**
 * Represents a deck of playing cards.
 * 
 * @author Anthony Dickson.
 */
public class Deck {
    ArrayList<Card> cards;    
    int i;
    
    public Deck() {
        i = 0;
        cards = new ArrayList<>(52);

        for (RANKS rank : RANKS.values()) {
            for (SUITS suit : SUITS.values()) {
                cards.add(new Card(rank, suit));
            }
        }

        Collections.shuffle(cards);
    }

    /**
     * Returns true if there are more cards in the deck.
     * 
     * @return true if there are more cards in the deck.
     */
    public boolean hasNext() {
        return i < cards.size();
    }

    /**
     * Returns the next card in the deck.
     * 
     * @return the next card.
     */
    public Card next() {
        return cards.get(i++);
    }

    /**
     * Reshuffles the deck.
     */
    public void reshuffle() {
        i = 0;
        Collections.shuffle(cards);
    }

    @Override
    public String toString() {
        String result = "";

        for (Card c : cards.subList(1, 10)) {
            result += "[" + c + "], ";            
        }

        return result + "...";
    }

    public static void main(String[] args) {
        Deck d = new Deck();
        System.out.println(d);
        System.out.println(d.hasNext());
        System.out.println(d.next());
    }
}