package mapthatset.g1.util;

import java.util.List;


/**
 * Utility function that keeps track of paramenter for each rounds.
 * Will be useful - I will explain
 * 
 * @author Kanna
 *
 */
public class GuessRounds {
	
	GuessParams guessParam = null; 		// for retriving start and end index of the query
	List<Integer> query = null;			// query(guess) used
	List<Integer> result = null;		// result of the query
	int guessRound = -1;				// the number of the guess
	int confidenceLevel = -1;			// current confidance level... not used...
	
	public GuessRounds(GuessParams guessParam, int round, List<Integer> query) {
		this.guessParam = guessParam;
		this.guessRound = round;
		this.query = query;
	}
	
	public GuessParams getGuessParam() {
		return guessParam;
	}
	public void setGuessParam(GuessParams guessParam) {
		this.guessParam = guessParam;
	}
	public List<Integer> getQuery() {
		return query;
	}
	public void setQuery(List<Integer> query) {
		this.query = query;
	}
	public List<Integer> getResult() {
		return result;
	}
	public void setResult(List<Integer> result) {
		this.result = result;
	}
	public int getGuessRound() {
		return guessRound;
	}
	public void setGuessRound(int guessRound) {
		this.guessRound = guessRound;
	}
	
	
	@Override
	public String toString() {
		return " [GuessRound] for round=" + guessRound + " - query = " + query + "\t\t result = " + result;
	}
}
