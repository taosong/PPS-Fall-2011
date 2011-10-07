package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.List;

public class QueryElement {

	private List<Integer> listOfPossibleMappings = new ArrayList<Integer>();
	private int mapping;
	private int value;
	private int initialMappingSize;
	
	public QueryElement(int value, List<Integer> possibleMappings) {
		this.value = value;
		this.listOfPossibleMappings = possibleMappings;
		this.mapping = -1;
		this.initialMappingSize = possibleMappings.size();
	}

	public List<Integer> getListOfPossibleMappings() {
		return listOfPossibleMappings;
	}
	public void setListOfPossibleMappings(List<Integer> listOfPossibleMappings) {
		this.listOfPossibleMappings = listOfPossibleMappings;
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
	
	public int getMappingListSize(){
		if(this.listOfPossibleMappings == null){
			return -1;
		}
		return this.listOfPossibleMappings.size();
	}
	
	/**
	 * returns true if the element has been fixed to one mapping.. i.e. List of possibleMapping's size == 1
	 * <br/>otherwise returns false
	 * @return
	 */
	public Boolean isResultConfirmed() {
		if(this.listOfPossibleMappings == null || this.listOfPossibleMappings.size()==0){
			return null;
		}
		return (this.listOfPossibleMappings.size()==1?true:false);
	}

	/**
	 * reurns true if current possibleMapping's size is less than initial possibleMapping's size.
	 * i.e. we have gained some knowledge for this element.
	 * @return
	 */
	public Boolean isAnyKnowledgeGained(){
		return (initialMappingSize-this.getMappingListSize() > 0?true:false);
	}

	/**
	 * removes the values that are in the possible mapping but not in the parameter(mappings).
	 * 
	 * @param mappings
	 */
	public void updateMappingsList(List<Integer> mappings){
		System.out.println(" [QueryElement] - mappings=null? " + mappings==null + "," + listOfPossibleMappings + "," + mappings);
		for(Integer i : listOfPossibleMappings){
			if(mappings.contains(i)){
				continue;
			}else{
				listOfPossibleMappings.remove(i);
			}
		}
	}
	
}
