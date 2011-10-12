package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lenovo
 *
 */
public class GuesserStrategy {
	
	private int mappingLength;
	private int global_queryLength = 0, global_overlap = 0, global_confidenceLevel = 0;
	private Boolean readyToGuess = null;
	
	List<Integer> allNumbers = null;
	List<QueryParams> queryparamsList = null;
	List<QueryRound> queryRounds = null;
	List<QueryRound> inferredRounds = null;
	List<DistinctQueryElement> listOfDistinctQueryElements = null;
	
	public Integer queryIndex;
	Integer counter;
	Boolean isQuery;
	KnowledgeBase knowledgeBase = null;
	
	
	public GuesserStrategy(int mappingLength) {
		this.readyToGuess = false;
		this.mappingLength = mappingLength;
		allNumbers = new ArrayList<Integer>();
		for(int i=0; i<mappingLength; i++){
			allNumbers.add(i+1);
		}
//		Collections.shuffle(allNumbers);
		
		queryparamsList = new ArrayList<QueryParams>();
		queryRounds = new ArrayList<QueryRound>();
		inferredRounds = new ArrayList<QueryRound>();
		listOfDistinctQueryElements = new ArrayList<DistinctQueryElement>();
		
		global_queryLength = 20; //mappingLength/20;
		if( mappingLength< 3){
			global_queryLength = 1;
		}else if( mappingLength< 5){
			global_queryLength = 2;
		}else if( mappingLength< 10){
			global_queryLength= 3;
		}else if(mappingLength <100){
			global_queryLength= 6;
		}else if(mappingLength<1000){
			global_queryLength= mappingLength/20;
		}
		//global_queryLength = AutomateSim.QUERY_LENGTH;
		System.out.print(" -- using QueryLength = " + global_queryLength + " >>> ");
		global_overlap = 2; // as of now k can be 5 and overlap can be 2 --- but k should be a function of n
		if(global_overlap <= mappingLength){
			global_overlap = 0;
		}
		global_confidenceLevel = 0;

		updateQueryParams(0, mappingLength-1, global_confidenceLevel, true);
		
		queryIndex = -1;
		isQuery = true;
		
		knowledgeBase = new KnowledgeBase(allNumbers);
//		knowledgeBase.printKnowledgeBase();
	}
	
	
	/**
	 * called from G1Guesser.nextAction(). All Logic goes here... 
	 * @return
	 */
	public ArrayList<Integer> nextGuess(){
		// basically qureyIndex is same as the current round.
		queryIndex++;
		if(queryIndex%100 == 0){
			System.out.println(".");
		} else {
			System.out.print(".");
		}
//		System.out.println(" - " + queryIndex);
		
		// decide if you want to query or guess.
		if (knowledgeBase.readyForGuessing()) {
			isQuery = false;
		} else {
			isQuery = true;
		}
//		System.out.println("[GuesserOverlappingGuesser] do I wanna query? - " + isQuery);
		
		// see if youk knowledgebase is complete.
		boolean isKnowledgebaseComplete = knowledgeBase.isInitialKnowledgeBaseComplete();
		System.out.println("[GuesserOverlappingGuesser] is my knowBase complete? - " + isKnowledgebaseComplete + " <> " + queryIndex);
		
		if (isQuery) {
			// querying
			List<Integer> toBeQueried = null;
			if(!isKnowledgebaseComplete){
				// retrieve form list of guess params... 
				if(!queryparamsList.isEmpty()){
					QueryParams qp = queryparamsList.get(0);
					queryparamsList.remove(0);

					try {
						toBeQueried = allNumbers.subList(qp.getStartIndex(), qp.getEndIndex());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
					
					QueryRound qr = new QueryRound(qp, queryIndex, toBeQueried);
					queryRounds.add(qr);
				} else {
					System.out.println("\n --queryparamsList is Empty--ERROR-- \n");
					knowledgeBase.printKnowledgeBase();
					for(QueryRound qr : queryRounds){
						System.out.println(" - " + qr.toString());
					}
					
					updateQueryParams(0, mappingLength-1, global_confidenceLevel, false);
					Collections.shuffle(allNumbers);
					QueryParams qp = queryparamsList.get(0);
					queryparamsList.remove(0);
					try {
						toBeQueried = allNumbers.subList(qp.getStartIndex(), qp.getEndIndex());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
					QueryRound qr = new QueryRound(qp, queryIndex, toBeQueried);
					queryRounds.add(qr);
				}
			} else {
				// use Strategy.
//				System.out.println(" Knowledgebase complete. Starting Strategy.");
				
				if(listOfDistinctQueryElements != null && listOfDistinctQueryElements.size()>0){
					toBeQueried = listOfDistinctQueryElements.get(0).getListOfDistinctElements();
					listOfDistinctQueryElements.remove(0);
				} else {
					listOfDistinctQueryElements = knowledgeBase.getDistinctElements();
					toBeQueried = listOfDistinctQueryElements.get(0).getListOfDistinctElements();
					listOfDistinctQueryElements.remove(0);
				}
				// trying to query elements with intersected knowledge bases
				
				QueryRound qr = new QueryRound(null, queryIndex, toBeQueried);
				queryRounds.add(qr);
			}
			return new ArrayList<Integer>(toBeQueried);
			
		} else {
			// guessing.. hopefully the correct guess...
			List<Integer> toBeGuessed = null;
			
			try {
				toBeGuessed = knowledgeBase.getGuessList();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println(" [GuesserOverlappingGuesser] guessed = " + nextGuessList);
			this.readyToGuess = true;
			return new ArrayList<Integer>(toBeGuessed);
		}
		
	}
	
	/**
	 * called from G1Guesser.setResult(). 
	 * Decide based on the result recieved for the query. Choose weather to use the BEST, WORST or MODERATE Stratergy.
	 * @param alResult
	 */
	public void setResult(ArrayList<Integer> alResult) {
		
//		System.out.println(" [GuesserOverlappingGuesser] returned = " + alResult);
		QueryRound qr = null;
		if(queryRounds.size() == 0){
			qr = new QueryRound(null, alResult);
		} else {
			qr = queryRounds.get(queryRounds.size()-1);
		}
		qr.setResult(alResult);
		// update my KnowledgeBase based on the result.
		updateKnowledgeBase(qr);
		
		if(!knowledgeBase.isInitialKnowledgeBaseComplete()){
			if(alResult.size() == 1){ 	// BEST CASE if all the queries elements are mapped to only 1 element... set the confidenceLevel high
				global_confidenceLevel = mappingLength;
				int start = qr.getQueryParam().getEndIndex() + 1;
				int end = mappingLength-1;
				updateQueryParams(start, end, global_confidenceLevel, false);
			} else if(alResult.size() == qr.getQuery().size() || alResult.size() == qr.getQuery().size()-1) {   // WORST CASE all elements are distinct... set confidanceLevel as 0
				global_confidenceLevel = 0;
			} else { 	// MODERATE STRATEGY 
				int queryLength = qr.getQuery().size();
				int resultLength = qr.getResult().size();
				global_confidenceLevel = queryLength - resultLength;
				int start = qr.getQueryParam().getEndIndex() + 1 - global_confidenceLevel;
				int end = mappingLength-1;
				updateQueryParams(start, end, global_confidenceLevel, false);
			}	
		}
		
//		knowledgeBase.printKnowledgeBase();
		inferFromQueryRound();
//		knowledgeBase.printKnowledgeBase();
	}
	
	/**
	 * Private method returns the List of QueryParams based on the parameters
	 * @param start - start index of the original set(question)
	 * @param end - end index of the original set(question)
	 * @param k - queryLength
	 * @param overlap 
	 * @return List<QueryParams>
	 */
	private List<QueryParams> findQueryParams(int start, int end, int subQueryLength, int overlap){
		int startSubList, endSubList;
		List<QueryParams> queryParamsList = new ArrayList<QueryParams>();
		
		for(int i=start; i<end; ){
			startSubList = i;
			if(i+subQueryLength <= end){
				endSubList = i+subQueryLength;
			} else {
				endSubList = end+1;
			}
			QueryParams queryParameter = new QueryParams();
			queryParameter.setStartIndex(startSubList);
			queryParameter.setEndIndex(endSubList);
			queryParameter.setLength(endSubList-startSubList);
			queryParamsList.add(queryParameter);
			i = i+subQueryLength-overlap;
		}
		
		return queryParamsList;
	}
	
	
	/**
	 * Private method which updates the global varaible queryparamsList
	 * @param start
	 * @param end
	 * @param confidenceLevel
	 */
	private void updateQueryParams(int start, int end, int confidenceLevel, boolean isInital){
		
		if(isInital){
			QueryParams qp = new QueryParams(start+confidenceLevel, end);
			qp.setStartIndex(0);
			qp.setEndIndex(end+1);
			queryparamsList.add(qp);
			return;
		}
		if( start+confidenceLevel > end){
			QueryParams qp = new QueryParams(start, end);
			queryparamsList = new ArrayList<QueryParams>();
//			queryparamsList.add(qp);
//			queryparamsList = findQueryParams(start, end, mappingLength-start, global_overlap);
		} else {
			queryparamsList = findQueryParams(start, end, global_queryLength + confidenceLevel, global_overlap);
		}
		
		System.out.println("------start=" + start + ", end=" + end + ", conf=" + confidenceLevel + ", initial=" + isInital);
		for(QueryParams qp : queryparamsList){
			System.out.println(" --qp-- " + qp);
		}
	}
	
	/**
	 * updates the KnowledgeBase for each element in the QueryRound's(qr) query with the result obtained for that query.
	 * @param qr
	 */
	private void updateKnowledgeBase(QueryRound qr){
		for(Integer i : qr.getQuery()){
			knowledgeBase.updateKnowledgeBase(i, qr.getResult());
		}
	}
	
	/**
	 * Inferres some queries and results based on the previous queryRounds
	 * <i>Takes a LOT of time to run</i> 
	 */
	private void inferFromQueryRound(){
		List<QueryRound> inferred = null;
		int size = queryRounds.size();
		for(int i=0; i<size-1; i++){
			for(int j=i+1; j<size; j++){
				inferred = SetHelper.getInferredQueryRounds(queryRounds.get(i), queryRounds.get(j));
			}
		}
		
		if(inferred != null && inferred.size()>0){
			System.out.println("inferred = " + inferred);
			for(QueryRound inf : inferred){
				updateKnowledgeBase(inf);
			}
			inferredRounds.addAll(inferred);
		}
	}
	
	
	public Boolean isGuess(){
		return this.readyToGuess;
	}
	
	
}
