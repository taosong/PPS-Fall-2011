package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class GuesserOverlappingGuesser {
	
	private int mappingLength;
	private int global_k = 0, global_overlap = 0, global_confidenceLevel = 0;
	private int round = -1;
	
	List<Integer> question = null;
	List<GuessParams> guessParamsList = null;
	List<GuessRounds> guessRounds = null;
	
	
	public GuesserOverlappingGuesser(int mappingLength) {
		this.mappingLength = mappingLength;
		question = new ArrayList<Integer>();
		for(int i=0; i<mappingLength; i++){
			question.add(i+1);
		}
		guessParamsList = new ArrayList<GuessParams>();
		guessRounds = new ArrayList<GuessRounds>();
		
		global_k = 5; global_overlap = 2; // as of now k can be 5 and overlap can be 2 --- but k should be a function of n
		global_confidenceLevel = 0;

		updateGuessParams(0, mappingLength-1, global_confidenceLevel);
	}
	
	/**
	 * Private method returns the List of GuessParams based on the parameters
	 * @param start - start index of the original set(question)
	 * @param end - end index of the original set(question)
	 * @param k
	 * @param overlap 
	 * @return List<GuessParams>
	 */
	private List<GuessParams> findGuessParams(int start, int end, int k, int overlap){
		int startSubList, endSubList;
		List<GuessParams> gprms = new ArrayList<GuessParams>();
		
		for(int i=start; i<end; ){
			startSubList = i;
			if(i+k <= end){
				endSubList = i+k;
			} else {
				endSubList = end+1;
			}
			GuessParams gp1 = new GuessParams();
			gp1.setStartIndex(startSubList);
			gp1.setEndIndex(endSubList);
			gp1.setLength(endSubList-startSubList);
			gprms.add(gp1);
			i = i+k-overlap;
		}
		
		return gprms;
	}
	
	
	/**
	 * Private method which updates the global varaible guessParamsList
	 * @param start
	 * @param end
	 * @param confidenceLevel
	 */
	private void updateGuessParams(int start, int end, int confidenceLevel){
		
		if( start+confidenceLevel > mappingLength){
			guessParamsList = findGuessParams(start, end, mappingLength-start, global_overlap);
		} else {
			guessParamsList = findGuessParams(start, end, global_k + confidenceLevel, global_overlap);
		}
		
//		for(GuessParams gp : guessParamsList){
//			System.out.println(" - " + gp.toString() + " == " + question.subList(gp.getStartIndex(), gp.getEndIndex()));
//		}
		
	}

	
	/**
	 * called from G1Guesser.nextAction(). All Logic goes here... 
	 * @return
	 */
	public List<Integer> nextGuess(){
		List<Integer> nextGuessList = null; 
		round++;

		if(!guessParamsList.isEmpty()){
			GuessParams gp = guessParamsList.get(0);
			guessParamsList.remove(0);
			System.out.println(" nextGuess() " + gp.getStartIndex() + " , " +gp.getEndIndex());
			nextGuessList = question.subList(gp.getStartIndex(), gp.getEndIndex());
			GuessRounds gr = new GuessRounds(gp, round, nextGuessList);
			guessRounds.add(gr);
		} else {
			System.out.println("\n");
			for(GuessRounds gr : guessRounds){
				System.out.println(" - " + gr.toString());
			}
			System.exit(0);
		}
		
		System.out.println(" [GuesserOverlappingGuesser] guessed = " + nextGuessList);
		
		return nextGuessList;
	}
	
	
	/**
	 * called from G1Guesser.setResult(). 
	 * Decide based on the result recieved for the query. Choose weather to use the BEST, WORST or MODERATE Stratergy.
	 * @param alResult
	 */
	public void setResult(ArrayList<Integer> alResult) {
		
		System.out.println(" [GuesserOverlappingGuesser] returned = " + alResult);
		GuessRounds gr = guessRounds.get(guessRounds.size()-1);
		gr.setResult(alResult);
		
		if(alResult.size() == 1){ 	// BEST CASE if all the queries elements are mapped to only 1 element... set the confidenceLevel high
			global_confidenceLevel = mappingLength;
			int start = gr.getGuessParam().getEndIndex() + 1;
			int end = mappingLength;
			updateGuessParams(start, end, global_confidenceLevel);
		} else if(alResult.size() == gr.getQuery().size() || alResult.size() == gr.getQuery().size()-1) {   // WORST CASE all elements are distinct... set confidanceLevel as 0
			global_confidenceLevel = 0;
		} else { 	// MODERATE STRATEGY 
			// to be implemented
		}
		
		
		
	}
	
	
	
}
