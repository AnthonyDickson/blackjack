package blackjack;

/**
 * An AI controlled player in a game of blackjack. 
 * This AI player follows a basic strategy as described on the website:
 * https://wizardofodds.com/games/blackjack/basics/.
 * 
 * @author Anthony Dickson.
 */
public class BasicStrategy extends AIPlayer {
    public BasicStrategy(Manager m) {
        super(m);
    }

    @Override
    public int getBet(int maxBet) {
        return (int)(0.1 * maxBet);
    }

    @Override
    public Move getMove() {
        int myHand = m.handValue(this);
        int dealerCard = m.getDealerCard().getValue();
        
        if (m.isHandSoft(this)) {
            return softHandMove(myHand, dealerCard);
        }

        return hardHandMove(myHand, dealerCard);
    }

    private Move softHandMove(int myHand, int dealerCard) {
        if (13 <= myHand && myHand <= 15) {
            return Move.HIT;
        } else if (16 <= myHand && myHand <= 18) {
            if (dealerCard < 7) {
                return Move.DOUBLE;
            } else {
                return Move.HIT;
            }
        }

        return Move.STAND;
    }

    private Move hardHandMove(int myHand, int dealerCard) {
        if (myHand <= 8)  {
            return Move.HIT;
        } else if (myHand == 9) {
            if (dealerCard < 7) {
                return Move.DOUBLE;
            } else {
                return Move.HIT;
            }
        } else if (myHand == 10 || myHand == 11) {
            if (myHand > dealerCard) {
                return Move.DOUBLE;
            } else {
                return Move.HIT;
            }
        } else if (12 <= myHand && myHand <= 16) {
            if (dealerCard < 7) {
                return Move.STAND;
            } else {
                return Move.HIT;
            }
        } 

        return Move.STAND;
    }
}