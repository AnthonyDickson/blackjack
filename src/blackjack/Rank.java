package blackjack;

/** A rank of a playing card. */
public enum Rank { 
    ACE { 
        @Override
        public String toString() { 
            return "Ace";      
        } 
    }, 
    TWO { 
        @Override
        public String toString() { 
            return "Two";      
        } 
    }, 
    THREE { 
        @Override
        public String toString() { 
            return "Three";    
        } 
    },                                
    FOUR { 
        @Override
        public String toString() { 
            return "Four";     
        } 
    }, 
    FIVE { 
        @Override
        public String toString() { 
            return "Five";     
        } 
    }, 
    SIX { 
        @Override
        public String toString() { 
            return "Six";      
        } 
    },                                
    SEVEN { 
        @Override
        public String toString() { 
            return "Seven";    
        } 
    }, 
    EIGHT { 
        @Override
        public String toString() { 
            return "Eight";    
        } 
    }, 
    NINE { 
        @Override
        public String toString() { 
            return "Nine";     
        } 
    }, 
    TEN { 
        @Override
        public String toString() { 
            return "Ten";      
        } 
    }, 
    JACK { 
        @Override
        public String toString() { 
            return "Jack";     
        } 
    }, 
    QUEEN { 
        @Override
        public String toString() { 
            return "Queen";    
        } 
    }, 
    KING { 
        @Override
        public String toString() { 
            return "King";     
        } 
    }  
};