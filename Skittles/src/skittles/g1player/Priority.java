package skittles.g1player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import skittles.g1player.main.DummyMain;


public class Priority {

	/**
	 * Priorities of all the colors calculated based on only the knowledge of
	 * the % of each color.
	 */
	private int[] initialPriority;
	private int[] initialPriorityForEat;
	/**
	 * Tao's priority array.
	 */
	private List<Color> positivePriorityTao;
	private List<Color> nonePriorityTao;
	private List<Color> negativePriorityTao;
	/**
	 * Priorities of all the colors calculated based on the knowledge of the %
	 * of each color and the happiness of each color.
	 */
	private int[] weightedPriority;
	/**
	 * the array with the percentages of all colors in hand. 
	 */
	private double[] percentInHand;
	/**
	 * the array with the percentages of all colors in hand times their happiness. 
	 */
	private double[] weightedPercentInHand;
	
	private Boolean isWeightedPriorityComplete;
	
	
	/**
	 * Dummy Constructor.
	 */
	public Priority() {
		isWeightedPriorityComplete = false;
	}
	
	/**
	 * returns the index of the color, that has the highest priority.
	 * @return
	 */
	public int getHighestPriorityColor(){
		if(isWeightedPriorityComplete){
			return weightedPriority[0];
		}
		return initialPriority[0];
	}

	/**
	 * returns the index of the color, that has the highest priority.
	 * @return
	 */
	public int getLestPriorityColor(){
		if(isWeightedPriorityComplete){
			return weightedPriority[weightedPriority.length-1];
		}
		return initialPriority[initialPriority.length-1];
	}
	
	/**
	 * returns the weightedPriority array. <br/>
	 * if weightedPriority is not yet complete, it returns the initialProirity array.
	 * @return
	 */
	public int[] getPriorityArray(){
		if(isWeightedPriorityComplete){
			return weightedPriority;
		}
		return initialPriority;
	}
	
	
	public int[] getPriorityArrayTao(){
		if(isWeightedPriorityComplete){
			return weightedPriority;
		}
		int[] retArray = new int[initialPriority.length];
		int index = 0;
		for(Color c : positivePriorityTao){
			retArray[index] = c.colorIndex;
			index++;
		}
		for(Color c : nonePriorityTao){
			retArray[index] = c.colorIndex;
			index++;
		}
		for(Color c : negativePriorityTao){
			retArray[index] = c.colorIndex;
			index++;
		}
		return retArray;
	}
	
	/**
	 * initialize the proirity arrays.
	 */
	public void initializePriority(int[] aintInHand){
		int numColors = aintInHand.length;
		int totalSkittles = 0;
		nonePriorityTao = new ArrayList<Color>();
		positivePriorityTao = new ArrayList<Color>();
		negativePriorityTao = new ArrayList<Color>();
		initialPriority = new int[numColors];
		weightedPriority  = new int[numColors];
		percentInHand  = new double[numColors];
		weightedPercentInHand = new double[numColors];
		// intialize the arrays to -1 and calculate totolSkitles.
		for(int i=0; i<numColors; i++){
			initialPriority[i] = -1;
 			weightedPriority[i] = -1;
			percentInHand[i] = -1.0;
			weightedPercentInHand[i] = -1.0;
			totalSkittles += aintInHand[i];
		}
		
		// calculate the percent of each color.
		for(int i=0; i<numColors; i++){
			percentInHand[i] = (double)aintInHand[i]/totalSkittles;
		}
		if(G1Player.DEBUG){
//		System.out.print(" >>>  percentInHand: ");
//		for(int i=0; i<numColors; i++){
//			System.out.print(""+percentInHand[i]+", ");
//		}
//		System.out.println();
		}
		
		int priorityOfColor;
		for(int i=0; i<numColors; i++){
			priorityOfColor = 0;
			for(int j=0; j<numColors; j++){
//				System.out.println("comparing " + percentInHand[i] +" , "+ percentInHand[j]
//						+",, "+(percentInHand[i] < percentInHand[j]));
				if(percentInHand[i] < percentInHand[j]) {
					priorityOfColor++;
				}
			}
			if(G1Player.DEBUG){
//				System.out.println(" >> priorityOfColor=" + priorityOfColor);
			}
			int k=0;
			while(true){
				if(initialPriority[priorityOfColor+k] == -1){
					initialPriority[priorityOfColor+k] = i;
					break;
				}
				k++;
			}
		}

		if(G1Player.DEBUG){
//		System.out.print(" >>>  initialPriority: ");
//		for(int ii=0; ii<numColors; ii++){
//			System.out.print(""+initialPriority[ii]+", ");
//		}
//		System.out.println();
		}
		
		initialPriorityForEat = Arrays.copyOf(initialPriority, initialPriority.length);
	
		// initialize nonePriorityTao
		Color c = null;
		for(int i=0; i<numColors; i++){
			c = new Color(i, 1.0, percentInHand[i]);
			nonePriorityTao.add(c);
		}
		Collections.sort(nonePriorityTao);
		
		if(G1Player.DEBUG) {
			System.out.println("initialize nonePriorityTao: " + nonePriorityTao);
		}
	}
	
	/**
	 * updates the priority of the given color with the given happiness.
	 * @param colorIndex
	 * @param happiness
	 */
	public void updatePriority(int colorIndex, double happiness, Infobase info){
		
		updateTaoPriority(colorIndex, happiness);
		
		weightedPercentInHand[colorIndex] = percentInHand[colorIndex] * happiness;
		
		/*
		 * if we have not taseted a particular color yet and if that number
		 * of skittles fo that color is 0, because of some trade that happened 
		 * in the previous round of we start with 0 skittles of any color,
		 * then we would never taste that color and so we give that color 
		 * a happiness value of -100000, just to make sure that it comes last in the 
		 * weighted priority list. 
		 */
		for(int i=0; i<info.getAintInHand().length; i++){
			if(info.getAintInHand()[i] == 0 && weightedPercentInHand[i] == -1.0){
				weightedPercentInHand[i] = -100000;
			}
		}
		
		if(G1Player.DEBUG){
			System.out.print(" >>>  weightedPercentInHand: ");
			for(int ii=0; ii<weightedPercentInHand.length; ii++){
				System.out.print(""+weightedPercentInHand[ii]+", ");
			}
			System.out.println();
		}
		
		for(int i=0; i<weightedPercentInHand.length; i++){
			if(weightedPercentInHand[i] == -1.0){
				return;
			}
		}
		
		calculateWeightedPriorities();
	}
	
	/**
	 * calcuate the weightedPriority based on the values in weightedPercentInHand.
	 */
	private void calculateWeightedPriorities(){
		
		int numColors = weightedPercentInHand.length; 
		int priorityOfColor;
		for(int i=0; i<numColors; i++){
			priorityOfColor = 0;
			for(int j=0; j<numColors; j++){
//				System.out.println("comparing " + weightedPercentInHand[i] +" , "+ weightedPercentInHand[j]
//						+",, "+(weightedPercentInHand[i] < weightedPercentInHand[j]));
				if(weightedPercentInHand[i] < weightedPercentInHand[j]) {
					priorityOfColor++;
				}
			}
//			System.out.println(" >>> priorityOfColor=" + priorityOfColor);
			int k=0;
			while(true){
				if(weightedPriority[priorityOfColor+k] == -1){
					weightedPriority[priorityOfColor+k] = i;
					break;
				}
				k++;
			}
		}
		
		if(G1Player.DEBUG){
			System.out.print(" >>>  weightedPriority: ");
			for(int ii=0; ii<numColors; ii++){
				System.out.print(""+weightedPriority[ii]+", ");
			}
			System.out.println();
		}
		
		isWeightedPriorityComplete = true;
		
		if(G1Player.DEBUG){
			DummyMain.printArray(getPriorityArrayTao(), " getPriorityArrayTao Complete-");
		}
	}
	
	
	/*
     * this will return the all the colors  that we desire to have at the end
     */
     public ArrayList<Integer> getDesiredVector(Infobase info){
            ArrayList<Integer> desiredVector = new ArrayList<Integer>();
            int k = info.getDesiredColorCount();
           
            //if the weight priority array is not complete , then desired vector would be positive array
            if(!isWeightedPriorityComplete){
//                   return desiredVector; // return positive matrix
            	ArrayList<Integer> returnList = new ArrayList<Integer>();
            	for(Color c : positivePriorityTao)
            	{
            		returnList.add(c.colorIndex);
            	}
            	return returnList;
            }else{
                   for(int i = 0; i<k; i++){
                         if(!(info.getColorHappiness(weightedPriority[i])<=0.0)){
                                desiredVector.add(weightedPriority[i]); 
                         }
                         else{
                                info.setDesiredColorCount(info.getDesiredColorCount() - 1);
                         }
                        
                   }
                   return desiredVector;
            }
     }	

     public Boolean isWeightedPriorityComplete(){
		return isWeightedPriorityComplete;
	}
	public int[] getInitialPriorityForEat() {
		return initialPriorityForEat;
	}
	public void setInitialPriorityForEat(int[] initialPriorityForEat) {
		this.initialPriorityForEat = initialPriorityForEat;
	}
	
	
	private void updateTaoPriority(int colorIndex, double happiness){
		Color c = new Color(colorIndex, happiness, percentInHand[colorIndex]);
		if(happiness <= 0){
			negativePriorityTao.add(c);
			nonePriorityTao.remove(c);
		} else {
			positivePriorityTao.add(c);
			nonePriorityTao.remove(c);
		}
		
		Collections.sort(negativePriorityTao);
		Collections.sort(nonePriorityTao);
		Collections.sort(positivePriorityTao);
		
		if(G1Player.DEBUG){
			System.out.println("[updateTaoPriority] colorIndex="+colorIndex+", happiness="+happiness);
			System.out.println("negativePriorityTao" + negativePriorityTao);
			System.out.println("nonePriorityTao" + nonePriorityTao);
			System.out.println("positivePriorityTao" + positivePriorityTao);
			DummyMain.printArray(getPriorityArrayTao(), " getPriorityArrayTao Intermediate -");
		}
	}	
}
