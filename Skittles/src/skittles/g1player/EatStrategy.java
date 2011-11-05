package skittles.g1player;

import java.util.ArrayList;

public class EatStrategy {

	/**
	 * finds the color we want to eat at every round.
	 * @param aintTempEat
	 * @param info
	 */
	public void update(int[] aintTempEat, Infobase info) {

//		aintTempEat = new int[aintTempEat.length];
		info.count++;
		int[] initialPriority = info.getPriority().getPriorityArray();
		int[] initialPriorityForEat = info.getPriority().getInitialPriorityForEat();
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
		complete = info.isPlayerInactive(-1);
		
		//TODO decide if we dont require any  more offers for a particular color 
		boolean isInitial = false;	
		//if all desired colors gathered - eat one by one
		boolean  isWeightedPriorityCompleteTemp =true;
		for(int i=0; i<initialPriorityForEat.length ; i++){
			if(initialPriorityForEat[i] != -1){
				isWeightedPriorityCompleteTemp =false;
				break;
			}
		}
	    if(complete){
			
	    	//System.out.println(" >>[EatStrategy] [update] complete");
	    	for(int i=0 ; i<aintInHand.length; i++){
				if(aintInHand[i] != -1){
					aintTempEat[i] = aintInHand[i];
					aintInHand[i] = 0;
				}
			}
			
		} else if(!isWeightedPriorityCompleteTemp){ //for initial n/2  rounds check for colors not tasted according to priority queue
			
			//System.out.println(" >>[EatStrategy] [update] !complete and !isWeightedPriorityComplete");
			for(int i=0; i<initialPriorityForEat.length ; i++){
				
				if(initialPriorityForEat[i] != -1){
					if(aintInHand[initialPriorityForEat[i]] > 0){
						aintTempEat[initialPriorityForEat[i]] = 1;
						aintInHand[initialPriorityForEat[i]]--;
						initialPriorityForEat[i] = -1;
						isInitial = true;
						break;
					}
					initialPriorityForEat[i] = -1;	
				}
			}
			
		} 
	    
	    if(!isInitial){
			
			//System.out.println(" >>[EatStrategy] [update] complete and isWeightedPriorityComplete");
	    	
			int eatIndex = -1;
			double eatHappiness = 10;
			for(int i=info.getDesiredColorCount(); i<initialPriority.length; i++){
				if(info.getColorHappiness(initialPriority[i]) >=0 && info.getColorHappiness(initialPriority[i]) < eatHappiness){
					if(aintInHand[initialPriority[i]] > 0){
						eatIndex = initialPriority[i];
						eatHappiness = info.getColorHappiness(initialPriority[i]);
					}
						
				}
			}
			
			if(eatIndex !=-1){
				aintTempEat[eatIndex] = 1;
				aintInHand[eatIndex]--;
			} else {
				// changed by Erica
				// I added in the toEat and numToEat check because the loop was running
				// and for every skittle we had left it was adding it to the aintTempEat
				// array, meaning we were trying to eat multiple colors
				// so the toEat keeps track of which one we plan to eat, and the numToEat 
				// is how many of that skittle we have.  The loop updates those, and then
				// only assigns what we will eat at the end.  This will hopefully fix the 
				// trying to eat multiple colors bug.  
				
				// right now it's choosing to eat the skittle we have the least of first.
				// We might want to change this, but I thought it would be an ok way to decide
				// for now at least.
				int toEat = -1;
				int numToEat = Integer.MAX_VALUE;
				for(int  i=info.getDesiredColorCount()-1; i>=0; i--){
					
					if(aintInHand[initialPriority[i]] > 0 && aintInHand[initialPriority[i]] <= numToEat){
						numToEat = aintInHand[initialPriority[i]];
						toEat = initialPriority[i];
					}
				}
				aintTempEat[toEat] = aintInHand[toEat];
				aintInHand[toEat] = 0;

			}
			
			
			
		}
	    info.roundComplete = true;
	    for (int i = 0; i<info.getAintInHand().length; i++){
	    	if(info.getAintInHand()[i] != 0){
	    		info.roundComplete = false;
	    	}
	    }
	}

	
}
