package mapthatset.g1;

import java.util.ArrayList;

import mapthatset.g1.util.GuesserOverlappingGuesser;
import mapthatset.sim.Guesser;
import mapthatset.sim.GuesserAction;

public class G1Gusser extends Guesser {
	
	GuesserOverlappingGuesser overlappingGuesser = null;

	ArrayList<Integer> nextGuessList = null;
	
	
	@Override
	public void startNewMapping(int intMappingLength) {
		overlappingGuesser = new GuesserOverlappingGuesser(intMappingLength);
		
	}

	@Override
	public GuesserAction nextAction() {

		nextGuessList = overlappingGuesser.nextGuess();
		
		return new GuesserAction("q", nextGuessList);
	}

	@Override
	public void setResult(ArrayList<Integer> alResult) {
//		overlappingGuesser.getClass();
	}

	@Override
	public String getID() {
		return overlappingGuesser.getClass().toString();
	}
	
}
