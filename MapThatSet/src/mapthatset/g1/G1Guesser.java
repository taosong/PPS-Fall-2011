package mapthatset.g1;

import java.util.ArrayList;
import java.util.List;

import mapthatset.g1.util.GuessStrategy;
import mapthatset.g1.util.GuesserOverlappingGuesser;
import mapthatset.g1.util.QueryElement;
import mapthatset.sim.Guesser;
import mapthatset.sim.GuesserAction;

public class G1Guesser extends Guesser {

	GuesserOverlappingGuesser overlappingGuesser = null;
	int mappingLength = 0;

	ArrayList<Integer> nextGuessList = null;
	ArrayList<Integer> queryContent = null;
	int queryIndex = 0;
	int counter = 0;
	boolean isQuery = true;
	ArrayList<Integer> queryResultList = new ArrayList<Integer>();
	ArrayList<Integer> previousQueryList = new ArrayList<Integer>();
	ArrayList<Integer> nextQueryList = new ArrayList<Integer>();
	ArrayList<QueryElement> listOfQueryElements = new ArrayList<QueryElement>();

	@Override
	public void startNewMapping(int mappingLength) {
		this.mappingLength = mappingLength;
		overlappingGuesser = new GuesserOverlappingGuesser(mappingLength);
		queryIndex = 0;
		isQuery = true;
		queryResultList = new ArrayList<Integer>();
		previousQueryList = new ArrayList<Integer>();
		nextQueryList = new ArrayList<Integer>();
		listOfQueryElements = new ArrayList<QueryElement>();
	}

	@Override
	public GuesserAction nextAction() {
		queryIndex++;
		GuessStrategy guessStrategy = new GuessStrategy();
		if (listOfQueryElements != null && listOfQueryElements.size() > 0) {
			if (mappingLength == listOfQueryElements.size()) {
				for (int j = 0; j < listOfQueryElements.size(); j++) {
					if (listOfQueryElements.get(j).getIsResultConfirmed() == true) {
						isQuery = false;
					} else {
						isQuery = true;
						break;
					}
				}
			}
		}

		if (isQuery) {
			nextQueryList = guessStrategy.deviseStrategy(queryIndex,
					mappingLength, queryResultList, previousQueryList,
					listOfQueryElements);
			this.previousQueryList=nextQueryList;
			return new GuesserAction("q", nextQueryList);
		} else {
			nextGuessList = new ArrayList<Integer>();

			for (int i = 0; i < mappingLength; i++) {
				for (int j = 0; j < listOfQueryElements.size(); j++) {
					if (i == listOfQueryElements.get(j).getValue()) {
						nextGuessList.add(listOfQueryElements.get(j)
								.getMapping());
					}
				}
			}
			return new GuesserAction("g", nextGuessList);
		}

	}

	@Override
	public void setResult(ArrayList<Integer> queryResult) {
		this.queryResultList = queryResult;
		//overlappingGuesser.setResult(queryResult);
	}

	@Override
	public String getID() {
		return overlappingGuesser.getClass().toString();
	}

}