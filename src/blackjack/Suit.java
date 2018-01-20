package blackjack;

/**
 * A suit of a playing card.
 */
public enum Suit { 
    CLUBS {
        @Override 
        public String toString() { 
            return "Clubs";    
        } 
    }, 
    SPADES {
        @Override 
        public String toString() { 
            return "Spades";   
        } 
    },                               
    DIAMONDS {
        @Override 
        public String toString() { 
            return "Diamonds"; 
        } 
    },                               
    HEARTS { 
        @Override
        public String toString() { 
            return "Hearts";   
        } 
    }
};