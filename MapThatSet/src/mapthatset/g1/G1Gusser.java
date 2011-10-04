package mapthatset.g1;

import java.util.ArrayList;
import java.util.List;

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

		nextGuessList = new ArrayList<Integer>();
		List<Integer> tempList = overlappingGuesser.nextGuess();
		
		for ( int temp : tempList )
		{
			nextGuessList.add(temp);
		}
		return new GuesserAction("q", nextGuessList);
	}

	@Override
	public void setResult(ArrayList<Integer> alResult) {
		overlappingGuesser.setResult(alResult);
	}

	@Override
	public String getID() {
		return overlappingGuesser.getClass().toString();
	}
	
}
