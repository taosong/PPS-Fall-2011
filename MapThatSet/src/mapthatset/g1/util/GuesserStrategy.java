package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 * 
 */
public class GuesserStrategy {

	private int mappingLength;
	private int global_queryLength = 0, global_overlap = 0,global_confidenceLevel = 0;
	private Boolean readyToGuess = null;
	private Boolean isMapBinary = false;
	List<Integer> allNumbers = null;
	List<Integer> allNumbers1 = null;
	List<QueryParams> queryparamsList = null;
	List<QueryRound> queryRounds = null;
	List<QueryRound> inferredRounds = null;
	List<DistinctQueryElement> listOfDistinctQueryElements = null;
	public Integer queryIndex;
	Integer counter;
	Boolean isQuery;
	KnowledgeBase knowledgeBase = null;
	Integer mappingIndex = null;
	Boolean hasMappingIndex = null;

	public GuesserStrategy(int mappingLength) {
		this.readyToGuess = false;
		this.mappingLength = mappingLength;
		allNumbers = new ArrayList<Integer>();
		allNumbers1 = new ArrayList<Integer>();
		for (int i = 0; i < mappingLength; i++) {
			allNumbers.add(i + 1);
			allNumbers1.add(i + 1);
		}
//		Collections.shuffle(allNumbers1);
		queryparamsList = new ArrayList<QueryParams>();
		queryRounds = new ArrayList<QueryRound>();
		inferredRounds = new ArrayList<QueryRound>();
		listOfDistinctQueryElements = new ArrayList<DistinctQueryElement>();
		if (mappingLength < 3) {
			global_queryLength = 1;
		} else {
			global_queryLength = (int)Math.sqrt(mappingLength);
		}
		
		global_overlap = 2; // as of now k can be 5 and overlap can be 2 --- but
							// k should be a function of n
		if (global_overlap <= mappingLength) {
			global_overlap = 0;
		}
		global_confidenceLevel = 0;
		updateQueryParams(0, mappingLength - 1, global_confidenceLevel, true);
		queryIndex = -1;
		isQuery = true;
		mappingIndex = -1;
		hasMappingIndex = false;
		knowledgeBase = new KnowledgeBase(allNumbers);
	}

	/**
	 * called from G1Guesser.nextAction(). All Logic goes here...
	 * 
	 * @return
	 */
	public ArrayList<Integer> nextGuess() {
		queryIndex++;
		// decide if you want to query or guess.
		if (knowledgeBase.readyForGuessing()) {
			isQuery = false;
		} else {
			isQuery = true;
		}
		// see if youk knowledgebase is complete.
		boolean isKnowledgebaseComplete = knowledgeBase.isInitialKnowledgeBaseComplete();
//		System.out.println("isQuery=" + isQuery + ", isKnowledgebaseComplete=" + isKnowledgebaseComplete);
		if (isQuery) {
			// querying
			List<Integer> toBeQueried = null;

			if (isMapBinary && mappingLength!=2) {
				QueryParams qp = queryparamsList.get(0);
				try {
					toBeQueried = allNumbers1.subList(qp.getStartIndex(),
							qp.getEndIndex() + 1);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				QueryRound qr = new QueryRound(qp, queryIndex, toBeQueried);
				queryRounds.add(qr);
			} else {
				if (!isKnowledgebaseComplete) {
					// retrieve form list of guess params...
					if (!queryparamsList.isEmpty()) {
						QueryParams qp = queryparamsList.get(0);
						queryparamsList.remove(0);
						try {
							toBeQueried = allNumbers.subList(qp.getStartIndex(), qp.getEndIndex());
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
						QueryRound qr = new QueryRound(qp, queryIndex,toBeQueried);
						queryRounds.add(qr);
					} else {
						updateQueryParams(0, mappingLength - 1,global_confidenceLevel, false);
						QueryParams qp = queryparamsList.get(0);
						queryparamsList.remove(0);
						try {
							toBeQueried = allNumbers.subList(qp.getStartIndex(), qp.getEndIndex());
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
						QueryRound qr = new QueryRound(qp, queryIndex,toBeQueried);
						queryRounds.add(qr);
					}
				} else {
					// use Strategy.
					if (listOfDistinctQueryElements != null && listOfDistinctQueryElements.size() > 0) {
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
			this.readyToGuess = true;
			return new ArrayList<Integer>(toBeGuessed);
		}
	}

	/*
	 * Form Queries for n-ary Guessers.
	 */
	public void generateListOfQueriesForNaryStrategy(int nary) {
		nary = 2;
//		System.out.println("The shuffled list is : " + allNumbers1.toString());
//		System.out.println("The list of queries for the n-ary mapping strategy is");
		QueryParams qp;
		// Iterate over all the elements.
		for (int i = 0; i < allNumbers1.size(); i += nary) {
			qp = new QueryParams();
			qp.setStartIndex(i);
			if (i + nary < allNumbers1.size()) {
				qp.setEndIndex(i + nary - 1);
			} else {
				qp.setEndIndex(allNumbers1.size() - 1);
			}
			queryparamsList.add(qp);
			/*
			 * for ( int j = nary; j>0 ; j--){
			 * 
			 * if ( (i+j)<=allNumbers1.size()){
			 * System.out.println(allNumbers1.subList(i,(i+j)));
			 * qp.setEndIndex(i+j); queryparamsList.add(qp); } else{
			 * System.out.println(allNumbers1.subList(i, allNumbers1.size()));
			 * qp.setEndIndex(allNumbers1.size()); queryparamsList.add(qp);
			 * break; } }
			 */

		}
//		System.out.println("The entire query Params List is  : " + queryparamsList);
	}

	

	/**
	 * called from G1Guesser.setResult(). Decide based on the result recieved
	 * for the query. Choose weather to use the BEST, WORST or MODERATE
	 * Stratergy.
	 * 
	 * @param alResult
	 */
	public void setResult(ArrayList<Integer> alResult) {
		QueryRound qr = null;
		if (queryRounds.size() == 0) {
			qr = new QueryRound(null, alResult);
		} else {
			qr = queryRounds.get(queryRounds.size() - 1);
		}
		qr.setResult(alResult);
		// update my KnowledgeBase based on the result.
		updateKnowledgeBase(qr);
		if (queryIndex == 0) {
			if (knowledgeBase.getIndex()<9){
				generateListOfQueriesForNaryStrategy(knowledgeBase.getIndex());
				isMapBinary = true;
			}
			mappingIndex = knowledgeBase.getIndex();
		}
		
		if (isMapBinary) {
			if (queryIndex != 0 && queryparamsList.size() > 0) {
				if (alResult.size() == 1) {
					queryparamsList.remove(0);
				} else {
					QueryParams queryParams = queryparamsList.get(0);
					queryParams.setEndIndex(queryParams.getEndIndex() - 1);
					queryparamsList.remove(0);
					queryparamsList.add(0, queryParams);
				}
			}
		} 
//		else {
//			if (!knowledgeBase.isInitialKnowledgeBaseComplete()) {
//				// BEST CASE if all the queries elements are mapped to only 1
//				// element... set the confidenceLevel high
//				if (alResult.size() == 1) {
//					global_confidenceLevel = mappingLength;
//					int start = qr.getQueryParam().getEndIndex() + 1;
//					int end = mappingLength - 1;
////					updateQueryParams(start, end, global_confidenceLevel, false);
//				}
//				// WORST CASE all elements are distinct...set confidanceLevel as
//				// 0
//				else if (alResult.size() == qr.getQuery().size()
//						|| alResult.size() == qr.getQuery().size() - 1) {
//					global_confidenceLevel = 0;
//
//				}
//				// MODERATE STRATEGY
//				else {
////					int queryLength = qr.getQuery().size();
////					int resultLength = qr.getResult().size();
////					global_confidenceLevel = queryLength - resultLength;
//					global_confidenceLevel = 0;
//					int start = qr.getQueryParam().getEndIndex() + 1 - global_confidenceLevel;
//					int end = mappingLength - 1;
////					updateQueryParams(start, end, global_confidenceLevel, false);
//				}
//			}
//		}
		inferFromQueryRound();
		
		if(mappingLength==2){
			queryparamsList = new ArrayList<QueryParams>();
			QueryParams qp1 = new QueryParams();
			qp1.setStartIndex(0);
			qp1.setEndIndex(1);
			queryparamsList.add(qp1);
		}
	}

	/**
	 * Private method returns the List of QueryParams based on the parameters
	 * 
	 * @param start
	 *            - start index of the original set(question)
	 * @param end
	 *            - end index of the original set(question)
	 * @param k
	 *            - queryLength
	 * @param overlap
	 * @return List<QueryParams>
	 */
	private List<QueryParams> findQueryParams(int start, int end, int subQueryLength, int overlap) {
		int startSubList, endSubList;
		List<QueryParams> queryParamsList = new ArrayList<QueryParams>();
		for (int i = start; i <= end;) {
			startSubList = i;
			if (i + subQueryLength <= end) {
				endSubList = i + subQueryLength;
			} else {
				endSubList = end + 1;
			}
			QueryParams queryParameter = new QueryParams();
			queryParameter.setStartIndex(startSubList);
			queryParameter.setEndIndex(endSubList);
			queryParameter.setLength(endSubList - startSubList);
			queryParamsList.add(queryParameter);
			i = i + subQueryLength - overlap;
		}
		return queryParamsList;
	}

	/**
	 * Private method which updates the global varaible queryparamsList
	 * 
	 * @param start
	 * @param end
	 * @param confidenceLevel
	 */
	private void updateQueryParams(int start, int end, int confidenceLevel,
			boolean isInital) {
		if (isInital) {
			QueryParams qp = new QueryParams(start + confidenceLevel, end);
			qp.setStartIndex(0);
			qp.setEndIndex(end + 1);
			queryparamsList.add(qp);
			return;
		}
		if (start + confidenceLevel > end) {
			QueryParams qp = new QueryParams(start, end);
			queryparamsList = new ArrayList<QueryParams>();
			queryparamsList.add(qp);
			queryparamsList = findQueryParams(start, end, mappingLength-start, global_overlap);
			/*queryparamsList = findQueryParams(start, end, global_queryLength
					, global_overlap);*/
			 
		} else {
			queryparamsList = findQueryParams(start, end, global_queryLength
					+ confidenceLevel, global_overlap);
		}

		// System.out.println("------start=" + start + ", end=" + end +
		// ", conf=" + confidenceLevel + ", initial=" + isInital);
//		for (QueryParams qp : queryparamsList) {
//			System.out.println(" --qp-- " + qp);
//		}
	}

	/**
	 * updates the KnowledgeBase for each element in the QueryRound's(qr) query
	 * with the result obtained for that query.
	 * 
	 * @param qr
	 */
	private void updateKnowledgeBase(QueryRound qr) {
		for (Integer i : qr.getQuery()) {
			knowledgeBase.updateKnowledgeBase(i, qr.getResult());
		}
	}

	/**
	 * Inferres some queries and results based on the previous queryRounds
	 * <i>Takes a LOT of time to run</i>
	 */
	private void inferFromQueryRound() {
		List<QueryRound> inferred = null;
		int size = queryRounds.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				inferred = SetHelper.getInferredQueryRounds(queryRounds.get(i),
						queryRounds.get(j));
			}
		}
		if (inferred != null && inferred.size() > 0) {
			for (QueryRound inf : inferred) {
				updateKnowledgeBase(inf);
			}
			inferredRounds.addAll(inferred);
		}
	}

	public Boolean isGuess() {
		return this.readyToGuess;
	}

}
