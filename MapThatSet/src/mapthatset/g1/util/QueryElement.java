package mapthatset.g1.util;

import java.util.ArrayList;

public class QueryElement {

	private ArrayList<Integer> listOfPossibleMappings = new ArrayList();
	private int mapping;
	private int value;
	private Boolean isResultConfirmed;

	public ArrayList<Integer> getListOfPossibleMappings() {
		return listOfPossibleMappings;
	}

	public void setListOfPossibleMappings(
			ArrayList<Integer> listOfPossibleMappings) {
		this.listOfPossibleMappings = listOfPossibleMappings;
	}

	public Boolean getIsResultConfirmed() {
		return isResultConfirmed;
	}

	public void setIsResultConfirmed(Boolean isResultConfirmed) {
		this.isResultConfirmed = isResultConfirmed;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMapping() {
		return mapping;
	}

	public void setMapping(int mapping) {
		this.mapping = mapping;
	}

}
