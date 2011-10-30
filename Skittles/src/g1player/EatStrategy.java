package g1player;

import java.util.ArrayList;

public class EatStrategy {

	/**
	 * finds the color we want to eat at every round.
	 * @param aintTempEat
	 * @param info
	 */
	public void update(int[] aintTempEat, Infobase info) {

//		aintTempEat = new int[aintTempEat.length];
		int[] initialPriority = info.getPriority().getPriorityArray();
		boolean isWeightedPriorityComplete = info.getPriority().isWeightedPriorityComplete();
		boolean complete = true;
		
		// decide if we do not require any more offers for all the colors - eat all 
		// u only have the skittles u desired  to have and u do not have any other skittle
		int[] aintInHand = info.getAintInHand();
		ArrayList<Integer>  desiredVector = info.getPriority().getDesiredVector(info);
		for(int i=0 ; i<aintInHand.length; i++){
			if(!desiredVector.contains(i) && aintInHand[i]!=-1){ // except for the desired color  check if ny other left
				complete = false;
			}
		}
		//TODO decide if we dont require any  more offers for a particular color 
			
		//if all desired colors gathered - eat one by one
	    if(complete){
			
	    	for(int i=0 ; i<aintInHand.length; i++){
				if(aintInHand[i] != -1){
					aintTempEat[i] = aintInHand[i];
					aintInHand[i] = 0;
				}
			}
			
		} else if(!isWeightedPriorityComplete){ //for initial n/2  rounds check for colors not tasted according to priority queue
			
			for(int i=0; i<initialPriority.length ; i++){
				
				if(initialPriority[i] != -1){
					//TODO check with kana
					aintTempEat[initialPriority[i]] = 1;
					aintInHand[initialPriority[i]]--;
					initialPriority[i] = -1;
					break;
				}
			}
			
		} else {
			
			int eatIndex = -1;
			double eatHappiness = -1;
			for(int i=info.getDesiredColorCount(); i<initialPriority.length; i++){
				if(info.getColorHappiness(initialPriority[i]) >=0 && info.getColorHappiness(initialPriority[i]) < eatHappiness){
						eatIndex = initialPriority[i];
						eatHappiness = info.getColorHappiness(initialPriority[i]);
				}
			}
			if(eatIndex !=-1){
				aintTempEat[eatIndex] = 1;
				aintInHand[eatIndex]--;
			} else {
				//if no positive color- keep looking here until that code is added
			}
			
		}
	}

	
}
