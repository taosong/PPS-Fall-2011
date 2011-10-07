package mapthatset.g1.util;

import java.util.ArrayList;

import javax.management.Query;

public class GuessStrategy {

	private ArrayList<Integer> nextQueryList = new ArrayList<Integer>();
	private int numberOfQueryElements;
	private int overlappingFactor;
	private int highConfidenceLevel;
	private int lowConfidenceLevel;
	private int moderateConfidenceLevel;
	private int numberOfSubQueries;

	/*
	 * This method devises the main strategy as in if its the first query or subsequent query
	 */
	public ArrayList<Integer> deviseStrategy(int queryIndex, int mappingLength,
			ArrayList<Integer> queryResultList,
			ArrayList<Integer> previousQueryList,
			ArrayList<QueryElement> listOfQueryElements) {

		//this method is used to create the list of elements , each having a list of possible mappings 
		
		if (queryResultList != null && queryResultList.size() > 0) {
			listOfQueryElements = preProcessQueryList(listOfQueryElements,
					queryResultList, previousQueryList);
		}

		if (queryIndex == 1) {
			nextQueryList = getFirstQuery(mappingLength);

		} else {
			nextQueryList = getSubsequentQuery(mappingLength, queryResultList,
					previousQueryList, listOfQueryElements);
		}

		listOfQueryElements = postProcessQueryList(listOfQueryElements);
		return nextQueryList;
	}

	/*
	 * random ..replace this
	 */
	public ArrayList<Integer> getFirstQuery(int mapppingLength) {
		ArrayList<Integer> firstQueryList = new ArrayList<Integer>();
		numberOfQueryElements = Math.abs(((mapppingLength + (int) (Math
				.sqrt(mapppingLength))) / 2));
		for (int i = 1; i <= numberOfQueryElements; i++) {
			firstQueryList.add(i);
		}

		return firstQueryList;
	}

	/*
	 * again replace this with your methods
	 */
	public ArrayList<Integer> getSubsequentQuery(int mappingLength,
			ArrayList<Integer> queryResultList,
			ArrayList<Integer> previousQueryList,
			ArrayList<QueryElement> listOfQueryElements) {
		ArrayList<Integer> subsequenetQueryList = new ArrayList<Integer>();

		if (queryResultList.size() == 1) {
			int newLengthOfQuerySet = ((mappingLength - previousQueryList
					.size()) / previousQueryList.size());
			if (newLengthOfQuerySet < 1) {
				newLengthOfQuerySet = 1;
			}
			for (int i = previousQueryList.size(); i < newLengthOfQuerySet; i++) {
				subsequenetQueryList.add(i);
			}

		} else /* if (queryResultList.size() < previousQueryList.size()) */{

		/*	ArrayList<Integer> tempArray = new ArrayList<Integer>();
			for (int i = 0; i < mappingLength; i++) {
				for (int j = 0; j < listOfQueryElements.size(); j++) {
					if (i + 1 == listOfQueryElements.get(j).getValue()) {
						if (listOfQueryElements != null
								&& listOfQueryElements.get(j) != null
								&& listOfQueryElements.get(j)
										.getIsResultConfirmed() != null
								&& listOfQueryElements.get(j)
										.getIsResultConfirmed() == true) {
							continue;
						} else {
							tempArray
									.add(listOfQueryElements.get(j).getValue());
						}
					}
				}
			}*/
			int tempLength = mappingLength - previousQueryList.size();
			for (int i = previousQueryList.size(); i < previousQueryList.size()
					+ tempLength; i++) {
				subsequenetQueryList.add(i);
			}
		}

		return subsequenetQueryList;

	}

	/*
	 * dummy method
	 */
	public void checkIfElementConfirmed(
			ArrayList<QueryElement> listOfQueryElements) {

	}

	/*
	 * this method is used to create the list ..
	 */
	public ArrayList<QueryElement> preProcessQueryList(
			ArrayList<QueryElement> listOfQueryElements,
			ArrayList<Integer> resultList, ArrayList<Integer> queryList) {
		System.out.println("size of the query element list : "
				+ listOfQueryElements.size());
		if (listOfQueryElements.size() == 0) {
			System.out.println("adding when size is 0");
			for (int i = 0; i < queryList.size(); i++) {
				QueryElement queryElement = new QueryElement();
				queryElement.setValue(queryList.get(i));
				ArrayList<Integer> listOfPossibleMappings = new ArrayList<Integer>();
				for (int j = 0; j < resultList.size(); j++) {
					listOfPossibleMappings.add(resultList.get(j));
				}
				queryElement.setListOfPossibleMappings(listOfPossibleMappings);
				queryElement.setIsResultConfirmed(false);
				listOfQueryElements.add(queryElement);
			}
		} else {
			System.out.println("adding when size isn't zero");
			for (int x = 0; x < queryList.size(); x++) {
				boolean isPresent = false;
				for (int y = 0; y < listOfQueryElements.size(); y++) {
					if (listOfQueryElements.get(y).getValue() == queryList
							.get(x)) {
						isPresent = true;
					}
				}
					if (!isPresent) {
						System.out.println("adding"+queryList.get(x) );
						QueryElement queryElementNew = new QueryElement();
						queryElementNew.setValue(queryList.get(x));
						queryElementNew.setListOfPossibleMappings(resultList);
						queryElementNew.setIsResultConfirmed(false);
						listOfQueryElements.add(queryElementNew);
					}
			}
			for (int p = 0; p < listOfQueryElements.size(); p++) {
				QueryElement queryElement = listOfQueryElements.get(p);
				for (int q = 0; q < queryList.size(); q++) {
					if (queryElement.getValue() == queryList.get(q))
						queryElement.setListOfPossibleMappings(resultList);
				}
				listOfQueryElements.set(p, queryElement);
			}
		}
		return listOfQueryElements;
	}

	//i ll explain it to u later
	public ArrayList<QueryElement> postProcessQueryList(
			ArrayList<QueryElement> listOfQueryElements) {
		if (listOfQueryElements != null && listOfQueryElements.size() > 0) {
			for (int i = 0; i < listOfQueryElements.size(); i++) {
				QueryElement queryElement = listOfQueryElements.get(i);
				ArrayList<Integer> listOfMappings = queryElement
						.getListOfPossibleMappings();
				if (listOfMappings.size() == 1) {
					if (queryElement.getIsResultConfirmed() == false) {
						queryElement.setIsResultConfirmed(true);
					}
				}
			}
		}
		return listOfQueryElements;
	}
}
