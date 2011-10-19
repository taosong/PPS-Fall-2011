package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.List;

public class DistinctQueryElement {
	private int value;
	private List<Integer> listOfDistinctElements = new ArrayList<Integer>();
	private List<Integer> listOfAllMappedValues = new ArrayList<Integer>();

	public List<Integer> getListOfAllMappedValues() {
		return listOfAllMappedValues;
	}

	public void setListOfAllMappedValues(List<Integer> listOfAllMappedValues) {
		this.listOfAllMappedValues = listOfAllMappedValues;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public List<Integer> getListOfDistinctElements() {
		return listOfDistinctElements;
	}

	public void setListOfDistinctElements(List<Integer> listOfDistinctElements) {
		this.listOfDistinctElements = listOfDistinctElements;
	}

	public boolean checkIfElementPresent() {
		return (Boolean) null;
	}

	public void addElement() {

	}

	@Override
	public String toString() {
		String ret = "[DistinctQueryElement] elements - "
				+ listOfDistinctElements + ",, mappings - "
				+ listOfAllMappedValues;
		return ret;
	}

}
