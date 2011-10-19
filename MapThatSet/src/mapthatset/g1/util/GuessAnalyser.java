package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.Iterator;

import mapthatset.sim.GuesserAction;

public class GuessAnalyser {

	public static int numOfTimesElementCountAppearsInAllQueries(
			ArrayList<GuesserAction> temp, int elementCount) {

		if (temp.isEmpty())
			return 0;
		int count = 0;

		Iterator<GuesserAction> it = temp.iterator();
		while (it.hasNext()) {

			GuesserAction tempList = it.next();
			if (tempList.getContent().size() == elementCount)
				count++;
		}
		System.out.println("I DONT CARE THIS IS WHAT I HAVE BEEN GETTING Number of times set of "+ elementCount+ " elements appears in the list of queries is :"+ count);
		return count;
	}

	public static int numOfTimesConsecutiveElementCountAppearsInAllQueries(
			ArrayList<GuesserAction> temp, int count, int elementCount) {

		int consecCount = 0;
		Iterator<GuesserAction> it = temp.iterator();
		while (it.hasNext()) {
			GuesserAction tempList = it.next();
			ArrayList<Integer> tempArray = tempList.getContent();
			if (tempArray.size() == elementCount) {
				count--;
				boolean check = true;
				for (int i = elementCount - 1; i >= 0; i--) {
					if (!(tempArray.get(i).compareTo(tempArray.get(i - 1)) == 1))
						check = false;
				}

				if (check == true)
					consecCount++;
			}
		}

		return consecCount;
	}

	public static int numOfTimesScrambledElementCountAppearsInAllQueries(
			ArrayList<GuesserAction> temp, int elementCount, int mappingLength) {
		int count = 0;
		Iterator<GuesserAction> it = temp.iterator();

		ArrayList<Integer> tempComparer = new ArrayList<Integer>();
		while (it.hasNext()) {
			GuesserAction tempList = it.next();
			ArrayList<Integer> tempArray = tempList.getContent();
			for (int i = 0; i < mappingLength - elementCount; i++) {

				/*
				 * Add the elements that need to be compared, with the query
				 * list.
				 */
				for (int j = 0; j < elementCount; j++)
					tempComparer.add(j + 1);

				/*
				 * Iterate over each element and see if the element is contained
				 * in tempArray
				 */
				int compareCount = 0;
				for (int k = 0; k < tempArray.size(); k++) {
					for (int iterateOverTempComparer = 0; iterateOverTempComparer < tempComparer
							.size(); iterateOverTempComparer++)
						if (tempArray.contains(tempComparer
								.get(iterateOverTempComparer)))
							compareCount++;
				}

				if (compareCount == tempComparer.size())
					count++;

			}
		}
		return count;

	}

}
