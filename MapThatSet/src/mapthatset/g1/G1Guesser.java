package mapthatset.g1;

import java.util.ArrayList;

import mapthatset.g1.util.GuesserOverlappingGuesser;
import mapthatset.sim.Guesser;
import mapthatset.sim.GuesserAction;

public class G1Guesser extends Guesser {

	GuesserOverlappingGuesser overlappingGuesser = null;
	int mappingLength = 0;
	
	ArrayList<Integer> nextGuessList = null;

	@Override
	public void startNewMapping(int mappingLength) {
		this.mappingLength = mappingLength;
		overlappingGuesser = new GuesserOverlappingGuesser(mappingLength);
		
	}

	@Override
	public GuesserAction nextAction() {

		nextGuessList = overlappingGuesser.nextGuess();
		
		return new GuesserAction("q", nextGuessList);
	}

	@Override
	public void setResult(ArrayList<Integer> queryResult) {
		
		overlappingGuesser.setResult(queryResult);
	}

	@Override
	public String getID() {
		return overlappingGuesser.getClass().toString();
	}

}