package blackjack;

import java.util.HashMap;

/**
 * Represents a playing card.
 * 
 * @author Anthony Dickson.
 */
public class Card { 
    public final static HashMap<Rank, Integer> VALUES;
    static {
        VALUES = new HashMap<>();
        VALUES.put(Rank.ACE, 1);
        VALUES.put(Rank.TWO, 2);
        VALUES.put(Rank.THREE, 3);
        VALUES.put(Rank.FOUR, 4);
        VALUES.put(Rank.FIVE, 5);
        VALUES.put(Rank.SIX, 6);
        VALUES.put(Rank.SEVEN, 7);
        VALUES.put(Rank.EIGHT, 8);
        VALUES.put(Rank.NINE, 9);
        VALUES.put(Rank.TEN, 10);
        VALUES.put(Rank.JACK, 10);
        VALUES.put(Rank.QUEEN, 10);
        VALUES.put(Rank.KING, 10);
    } 

    private Rank rank;
    private Suit suit;
    
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return VALUES.get(rank);
    }

    @Override
    public boolean equals(Object other) {
        return this.getRank() == ((Card) other).getRank() && 
               this.getSuit() == ((Card) other).getSuit();
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public static void main(String[] args) {
        Card c = new Card(Rank.THREE, Suit.DIAMONDS);
        Card c2 = new Card(Rank.THREE, Suit.DIAMONDS);
        System.out.println(c);
        System.out.println(c.equals(c2));
    }
}