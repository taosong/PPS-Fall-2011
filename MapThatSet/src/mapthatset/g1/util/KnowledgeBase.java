package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
	
	Map<Integer, QueryElement> mapQueryElements = null;
	

	public KnowledgeBase(List<Integer> allNumbers) {
		QueryElement qe = null;

		mapQueryElements = new HashMap<Integer, QueryElement>();
	
		for(Integer i : allNumbers){
			qe = new QueryElement(i, new ArrayList<Integer>(allNumbers));
			mapQueryElements.put(i, qe);
		}
	}

	/**
	 * returns true if all the queryElements have their result confirmed, othewise returns false.
	 * @return
	 */
	public Boolean readyForGuessing(){
		Boolean readyForGuess = true;
		try{
		for(Integer i : mapQueryElements.keySet()){
			if(mapQueryElements.get(i).isResultConfirmed()){
				continue;
			}
			readyForGuess = false;
			break;
		}
		}catch(Exception e){
			e.printStackTrace();
			printKnowledgeBase();
			System.exit(1);
		}
		return readyForGuess;
	}
	
	/**
	 * returns true if I have gained someKnowledge about all elements, false otherwise.
	 * @return
	 */
	public Boolean isInitialKnowledgeBaseComplete(){
		Boolean initialKnowledgeBaseComplete = true;
		for(Integer i : mapQueryElements.keySet()){
			if(mapQueryElements.get(i).isAnyKnowledgeGained()){
				continue;
			}
			initialKnowledgeBaseComplete = false;
			break;
		}
		return initialKnowledgeBaseComplete;
	}
	
	
	public QueryElement getQueryElement(int value){
		return mapQueryElements.get(value);
	}
	
	/**
	 * updates the knowledge base for QueryElement whose value is element.
	 * @param element
	 * @param mappingList
	 */
	public void updateKnowledgeBase(int element, List<Integer> mappingList){
		QueryElement qe = getQueryElement(element);
		qe.updateMappingsList(mappingList);
	}
	
	/**
	 * updates the KnowledgeBase(listOfPossibleMappings) for QueryElement qe.
	 * @param qe
	 * @param mappingList
	 */
	public void updateKnowledgeBase(QueryElement qe, List<Integer> mappingList){
		qe.updateMappingsList(mappingList);
	}
	
	public void printKnowledgeBase(){
		QueryElement qe = null;
		System.out.println(" ------------- KNOWLEDGE BASE --------------");
		for(Integer i : mapQueryElements.keySet()){
			qe = mapQueryElements.get(i);
			System.out.println(" " + qe.getValue() + " -- " + qe.getListOfPossibleMappings());
		}
		System.out.println(" ------------- KNOWLEDGE BASE ENDS--------------");
	}
	
	public List<DistinctQueryElement> getDistinctElements(){
		List<DistinctQueryElement> listOfDistinctQueryElements = new ArrayList<DistinctQueryElement>();
		for( int i : mapQueryElements.keySet()){
			if(!mapQueryElements.get(i).isResultConfirmed()){
				listOfDistinctQueryElements=SetHelper.addDistinctElement(listOfDistinctQueryElements, (QueryElement) mapQueryElements.get(i));
			}					
		}
		return listOfDistinctQueryElements;
	}
}
