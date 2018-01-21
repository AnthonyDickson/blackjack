package blackjack;

import java.util.*;

/**
 * An AI controlled player in a game of blackjack.
 * This AI player tries to use experience when deciding which move to take.
 * 
 * @author Anthony Dickson
 */
public class WizardOfOdds extends Player {
    private static Map<State, State> states = new HashMap<>();
    private static Map<State, Move> moves = new HashMap<>();
    // TODO: Save states to file.

    public WizardOfOdds(Manager m) {
        super(m);

        this.name = "Wizard of Odds";
    }

    /**
     * Get the player's bet as requested by the manager.
     * 
     * @param maxBet The maximum bet a player can make.
     * @return the player's bet.
     */
    public int getBet(int maxBet) {
        return (int)(0.1 * maxBet);
    }
    
    /**
     * Get the player's move as requested by the manager.
     * 
     * @return the player's move.
     */
    public Move getMove() {
        Move move = Move.STAND;
        State state = new State(m.handValue(this), m.isHandSoft(this), m.getDealerCard().getValue());

        if (states.containsKey(state)) {
            state = states.get(state);
        } else {
            states.put(state, state);
        }

        // if (state.nStand < State.MIN_TRIALS) {
        //     move = Move.STAND;
        // } else if (state.nDoubleDown < State.MIN_TRIALS) {
        //     move = Move.DOUBLE;
        // } else if (state.nHit < State.MIN_TRIALS) {
        //     move = Move.HIT;
        // } else {
            
            double hStand = state.h(Move.STAND); 
            double hDouble = state.h(Move.DOUBLE);
            double hHit = state.h(Move.HIT);
            
            if (state.nStand == 0) {
                move = Move.STAND;
            } else if (state.nDoubleDown == 0) {
                move = Move.DOUBLE;
            } else if (state.nHit == 0) {
                move = Move.HIT;
            } else {
                if (hStand > hDouble && hStand > hHit) {
                    move = Move.STAND;
                } else if (hDouble > hHit) {
                    move = Move.DOUBLE;
                } else {
                    move = Move.HIT;
                }
            }
        // }

        moves.put(state, move);

        return move;
    }

    @Override
    public void sendResult(Status result) {
        super.sendResult(result);
        
        for (Map.Entry<State, Move> e : moves.entrySet()) {
            State state = e.getKey();
            Move move = e.getValue();
            state.update(move, result == Status.WON);
        }

        moves.clear();
        // System.err.println(this);
    }

    @Override
    public String toString() {
        String result = "Num states: " + states.size() + "\n";

        for (Map.Entry<State, State> e : states.entrySet()) {
            result = result + e.getValue() + "\n";
        }

        return result;
    }

    /**
     * Represents a soft/hard hand of a given value, and the probabilities
     * of winning with a given move.
     */
    private class State {
        static final int MIN_TRIALS = 30;

        int handValue, dealerValue;
        boolean handSoft;
        // Number of wins for a given move from this state.
        int hit, doubleDown, stand;
        // Number of trials for a given move from this state.
        int nHit, nDoubleDown, nStand;
        // Total number of moves made from this state.
        int total;

        public State(int handValue, boolean handSoft, int dealerValue) {
            this.handValue = handValue;
            this.handSoft = handSoft;
            this.dealerValue = dealerValue;
        }

        public double h(Move move) {
            return exploitation(move) + exploration(move);
        }

        private double exploitation(Move move) {
            if (move == Move.HIT && nHit > 0) {
                return (double) hit / nHit;
            } else if (move == Move.DOUBLE && nDoubleDown > 0) {
                return (double) doubleDown / nDoubleDown;
            } else if (move == Move.STAND && nStand > 0) {
                return (double) stand / nStand;
            }

            return 0.0;
        }

        private double exploration(Move move) {
            if (move == Move.HIT && nHit > 0) {
                return Math.sqrt(2) * Math.sqrt(Math.log(total)/nHit);
            } else if (move == Move.DOUBLE && nDoubleDown > 0) {
                return Math.sqrt(2) * Math.sqrt(Math.log(total)/nDoubleDown);
            } else if (move == Move.STAND && nStand > 0) {
                return Math.sqrt(2) * Math.sqrt(Math.log(total)/nStand);
            }

            return 0.0;
        }

        public void update(Move move, boolean won) {
            if (move == Move.HIT) {
                if (won) {
                    hit++;
                }

                nHit++;
            } else if (move == Move.DOUBLE) {
                if (won) {
                    doubleDown++;
                }

                nDoubleDown++;
            } else if (move == Move.STAND) {
                if (won) {
                    stand++;
                }

                nStand++;
            }

            total++;
        }
        
        @Override
        public boolean equals(Object other) {
            return this.handValue == ((State) other).handValue && 
                   this.handSoft == ((State) other).handSoft &&
                   this.dealerValue == ((State) other).dealerValue;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + handValue;
            hash = 31 * hash + ((handSoft) ? 0 : 137);
            hash = 31 * hash + dealerValue;

            return hash;
        }

        @Override
        public String toString() {
            return String.format("State: %2d%s%2d\ttotal: %4d" + 
                "\thit: %.3f (%.2f, %.2f) (%5d, %5d)" + 
                "\tdouble: %.3f (%.2f, %.2f) (%5d,%5d) " + 
                "\tstand: %.3f (%.2f, %.2f) (%5d,%5d)", 
                handValue, (handSoft) ? "s" : "h", dealerValue, total,
                h(Move.HIT), exploitation(Move.HIT), exploration(Move.HIT), hit, nHit, 
                h(Move.DOUBLE), exploitation(Move.DOUBLE), exploration(Move.DOUBLE), doubleDown, nDoubleDown, 
                h(Move.STAND), exploitation(Move.STAND), exploration(Move.STAND), stand, nStand); 
        }
    }
}