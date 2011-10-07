package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class GuesserOverlappingGuesser {
	
	private int mappingLength;
	private int global_queryLength = 0, global_overlap = 0, global_confidenceLevel = 0;
	
	List<Integer> allNumbers = null;
	List<QueryParams> guessParamsList = null;
	List<QueryRound> queryRounds = null;
	
	List<Integer> nextGuessList = null;
	List<Integer> queryContent = null;
	Integer queryIndex;
	Integer counter;
	Boolean isQuery;
	List<Integer> queryResultList = new ArrayList<Integer>();
	List<Integer> previousQueryList = new ArrayList<Integer>();
	List<Integer> nextQueryList = new ArrayList<Integer>();
	KnowledgeBase knowledgeBase = null;
	
	
	public GuesserOverlappingGuesser(int mappingLength) {
		this.mappingLength = mappingLength;
		allNumbers = new ArrayList<Integer>();
		for(int i=0; i<mappingLength; i++){
			allNumbers.add(i+1);
		}
		guessParamsList = new ArrayList<QueryParams>();
		queryRounds = new ArrayList<QueryRound>();
		
		global_queryLength = 5; global_overlap = 2; // as of now k can be 5 and overlap can be 2 --- but k should be a function of n
		global_confidenceLevel = 0;

		updateGuessParams(0, mappingLength-1, global_confidenceLevel);
		
		queryIndex = -1;
		isQuery = true;
		queryResultList = new ArrayList<Integer>();
		previousQueryList = new ArrayList<Integer>();
		nextQueryList = new ArrayList<Integer>();
		
		knowledgeBase = new KnowledgeBase(allNumbers);
	}
	
	
	/**
	 * called from G1Guesser.nextAction(). All Logic goes here... 
	 * @return
	 */
	public ArrayList<Integer> nextGuess(){
		
		// basically qureyIndex is same as the current round.
		queryIndex++;
		System.out.println("[GuesserOverlappingGuesser] current round - " + queryIndex);
		
		// decide if you want to query or guess.
		if (knowledgeBase.readyForGuessing()) {
			isQuery = false;
		} else {
			isQuery = true;
		}
		System.out.println("[GuesserOverlappingGuesser] do I wanna query? - " + isQuery);
		
		// see if youk knowledgebase is complete.
		boolean isKnowledgebaseComplete = true;
		for (QueryElement qe : knowledgeBase.getListOfQueryElements()) {
			if (qe.getMappingListSize() == mappingLength) {
				isKnowledgebaseComplete = false;
				break;
			} else {
				isKnowledgebaseComplete = true;
			}
		}
		System.out.println("[GuesserOverlappingGuesser] do my knowBase complete? - " + isKnowledgebaseComplete);
		
		if (isQuery) {
			// querying
			if(!isKnowledgebaseComplete){
				// retrieve form list of guess params... 
				if(!guessParamsList.isEmpty()){
					QueryParams qp = guessParamsList.get(0);
					guessParamsList.remove(0);
					System.out.println(">> nextGuess() " + qp.getStartIndex() + " , " +qp.getEndIndex());
					
					nextQueryList = allNumbers.subList(qp.getStartIndex(), qp.getEndIndex());
					this.previousQueryList=nextQueryList;
					
					QueryRound qr = new QueryRound(qp, queryIndex, nextQueryList);
					queryRounds.add(qr);
				} else {
					System.out.println("\n --guessParamsList is Empty--ERROR-- \n");
					for(QueryRound qr : queryRounds){
						System.out.println(" - " + qr.toString());
					}
					System.exit(0);
				}
			} else {
				// use Strategy.
				System.out.println(" Knowledgebase complete. ");
				knowledgeBase.printKnowledgeBase();
				System.exit(0);
				
//				GuessStrategy guessStrategy = new GuessStrategy(allNumbers);
//				nextQueryList = guessStrategy.deviseStrategy(queryIndex,
//						mappingLength, queryResultList, previousQueryList,
//						knowledgeBase.getListOfQueryElements(), qp);
			}
			
			System.out.println(" [GuesserOverlappingGuesser] query = " + nextQueryList);
			
			return new ArrayList<Integer>(nextQueryList);
			
		} else {
			// guessing.. hopefully the correct guess...
			nextGuessList = new ArrayList<Integer>();

			for (int i = 0; i < mappingLength; i++) {
				for (int j = 0; j < knowledgeBase.getListOfQueryElements().size(); j++) {
					if (i == knowledgeBase.getListOfQueryElements().get(j).getValue()) {
						nextGuessList.add(knowledgeBase.getListOfQueryElements().get(j)
								.getMapping());
					}
				}
			}
			
			System.out.println(" [GuesserOverlappingGuesser] guessed = " + nextGuessList);
			
			return new ArrayList<Integer>(nextGuessList);
		}
		
	}
	
	/**
	 * called from G1Guesser.setResult(). 
	 * Decide based on the result recieved for the query. Choose weather to use the BEST, WORST or MODERATE Stratergy.
	 * @param alResult
	 */
	public void setResult(ArrayList<Integer> alResult) {
		
		System.out.println(" [GuesserOverlappingGuesser] returned = " + alResult);
		QueryRound qr = queryRounds.get(queryRounds.size()-1);
		qr.setResult(alResult);
		// update my KnowledgeBase based on the result.
		updateKnowledgeBase(qr);
		
		if(alResult.size() == 1){ 	// BEST CASE if all the queries elements are mapped to only 1 element... set the confidenceLevel high
			global_confidenceLevel = mappingLength;
			int start = qr.getQueryParam().getEndIndex() + 1;
			int end = mappingLength;
			updateGuessParams(start, end, global_confidenceLevel);
		} else if(alResult.size() == qr.getQuery().size() || alResult.size() == qr.getQuery().size()-1) {   // WORST CASE all elements are distinct... set confidanceLevel as 0
			global_confidenceLevel = 0;
		} else { 	// MODERATE STRATEGY 
			int queryLength = qr.getQuery().size();
			int resultLength = qr.getResult().size();
			global_confidenceLevel = queryLength - resultLength;
			int start = qr.getQueryParam().getEndIndex() + 1;
			int end = mappingLength;
			updateGuessParams(start, end, global_confidenceLevel);
		}
		
		this.queryResultList = alResult;
		
	}
	
	/**
	 * Private method returns the List of QueryParams based on the parameters
	 * @param start - start index of the original set(question)
	 * @param end - end index of the original set(question)
	 * @param k
	 * @param overlap 
	 * @return List<QueryParams>
	 */
	private List<QueryParams> findGuessParams(int start, int end, int k, int overlap){
		int startSubList, endSubList;
		List<QueryParams> gprms = new ArrayList<QueryParams>();
		
		for(int i=start; i<end; ){
			startSubList = i;
			if(i+k <= end){
				endSubList = i+k;
			} else {
				endSubList = end+1;
			}
			QueryParams gp1 = new QueryParams();
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
			guessParamsList = findGuessParams(start, end, global_queryLength + confidenceLevel, global_overlap);
		}
		
//		for(QueryParams gp : guessParamsList){
//			System.out.println(" - " + gp.toString() + " == " + question.subList(gp.getStartIndex(), gp.getEndIndex()));
//		}
		
	}
	
	/**
	 * uodates the KnowledgeBase for each element in the QueryRound's(qr) query with the result obtained for that query.
	 * @param qr
	 */
	private void updateKnowledgeBase(QueryRound qr){
		for(Integer i : qr.getQuery()){
			knowledgeBase.updateKnowledgeBase(i, qr.getResult());
		}
	}
}
