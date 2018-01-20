package blackjack;

import java.util.HashMap;

/**
 * Represents a playing card.
 * 
 * @author Anthony Dickson.
 */
public class Card {
    public static enum SUITS { 
        CLUBS       { public String toString() { return "Clubs";    } }, 
        SPADES      { public String toString() { return "Spades";   } },                               
        DIAMONDS    { public String toString() { return "Diamonds"; } },                               
        HEARTS      { public String toString() { return "Hearts";   } }
    };
    public static enum RANKS { 
        ACE     { public String toString() { return "Ace";      } } , 
        TWO     { public String toString() { return "Two";      } } , 
        THREE   { public String toString() { return "Three";    } } ,                                
        FOUR    { public String toString() { return "Four";     } } , 
        FIVE    { public String toString() { return "Five";     } } , 
        SIX     { public String toString() { return "Six";      } } ,                                
        SEVEN   { public String toString() { return "Seven";    } } , 
        EIGHT   { public String toString() { return "Eight";    } } , 
        NINE    { public String toString() { return "Nine";     } } , 
        TEN     { public String toString() { return "Ten";      } } , 
        JACK    { public String toString() { return "Jack";     } } , 
        QUEEN   { public String toString() { return "Queen";    } } , 
        KING    { public String toString() { return "King";     } }  
    };
    public final static HashMap<RANKS, Integer> VALUES;
    static {
        VALUES = new HashMap<>();
        VALUES.put(RANKS.ACE, 1);
        VALUES.put(RANKS.TWO, 2);
        VALUES.put(RANKS.THREE, 3);
        VALUES.put(RANKS.FOUR, 4);
        VALUES.put(RANKS.FIVE, 5);
        VALUES.put(RANKS.SIX, 6);
        VALUES.put(RANKS.SEVEN, 7);
        VALUES.put(RANKS.EIGHT, 8);
        VALUES.put(RANKS.NINE, 9);
        VALUES.put(RANKS.TEN, 10);
        VALUES.put(RANKS.JACK, 10);
        VALUES.put(RANKS.QUEEN, 10);
        VALUES.put(RANKS.KING, 10);
    } 

    private RANKS rank;
    private SUITS suit;
    
    public Card(RANKS rank, SUITS suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public RANKS getRank() {
        return rank;
    }

    public SUITS getSuit() {
        return suit;
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
        Card c = new Card(RANKS.THREE, SUITS.DIAMONDS);
        Card c2 = new Card(RANKS.THREE, SUITS.DIAMONDS);
        System.out.println(c);
        System.out.println(c.equals(c2));
    }
}