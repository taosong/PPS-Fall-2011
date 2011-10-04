package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.List;

public class GuesserOverlappingGuesser {
	
	private int mappingLength;
	private int numGuesses = 0;
	
	List<Integer> question = null;
	List<GuessParams> guessParamsList = null;
	
	public GuesserOverlappingGuesser(int mappingLength) {
		this.mappingLength = mappingLength;
		question = new ArrayList<Integer>();
		for(int i=0; i<mappingLength; i++){
			question.add(i+1);
		}
		guessParamsList = new ArrayList<GuessParams>();
		
		guessParamsList = findGuessParams(0, mappingLength - 1, mappingLength);
	}
	
	private List<GuessParams> findGuessParams(int start, int end, int length){
		int startSubList, endSubList, subListLength;
		List<GuessParams> gprms = new ArrayList<GuessParams>();
		
		startSubList = start;
		endSubList = (int) Math.ceil( (3*(end-1)) / 4 );
		GuessParams gp1 = new GuessParams();
		gp1.setStartIndex(startSubList);
		gp1.setEndIndex(endSubList);

		gprms.add(gp1);
		return gprms;
	}

	public ArrayList<Integer> nextGuess(){
		ArrayList<Integer> nextGuessList = new ArrayList<Integer>(); 
		
		GuessParams gp = guessParamsList.get(0);
		nextGuessList = (ArrayList<Integer>) question.subList(gp.getStartIndex(), gp.getEndIndex());
		
		return nextGuessList;
	}
	
	
}
