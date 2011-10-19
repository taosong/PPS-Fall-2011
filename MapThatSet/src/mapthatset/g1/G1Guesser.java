package mapthatset.g1;

import java.util.ArrayList;

import mapthatset.g1.util.GuesserStrategy;
import mapthatset.sim.Guesser;
import mapthatset.sim.GuesserAction;

public class G1Guesser extends Guesser {

	GuesserStrategy guesserStrategy = null;
	int mappingLength = 0;

	ArrayList<Integer> nextGuessList = null;

	@Override
	public void startNewMapping(int mappingLength) {
		this.mappingLength = mappingLength;
		guesserStrategy = new GuesserStrategy(mappingLength);

	}

	@Override
	public GuesserAction nextAction() {

		nextGuessList = guesserStrategy.nextGuess();
		if (!guesserStrategy.isGuess()) {
			return new GuesserAction("q", nextGuessList);
		} else {
			return new GuesserAction("g", nextGuessList);
		}

	}

	@Override
	public void setResult(ArrayList<Integer> queryResult) {

		guesserStrategy.setResult(queryResult);
//		if (guesserStrategy.isGuess()) {
//			System.err.println("Score=" + guesserStrategy.queryIndex);
//			// System.exit(0);
//		}
	}

	@Override
	public String getID() {
		return guesserStrategy.getClass().toString();
	}

}