package g1player;



public class Priority {

	/**
	 * Priorities of all the colors calculated based on only the knowledge of
	 * the % of each color.
	 */
	private int[] initialPriority;
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
	
	/**
	 * initialize the proirity arrays.
	 */
	public void initializePriority(int[] aintInHand){
		int numColors = aintInHand.length;
		int totalSkittles = 0;
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
//		System.out.println(" >> totalSkittles=" + totalSkittles);
		
		// calculate the percent of each color.
		for(int i=0; i<numColors; i++){
			percentInHand[i] = (double)aintInHand[i]/totalSkittles;
		}
//		System.out.print(" >>>  percentInHand: ");
//		for(int i=0; i<numColors; i++){
//			System.out.print(""+percentInHand[i]+", ");
//		}
//		System.out.println();
		
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
//			System.out.println(" >> priorityOfColor=" + priorityOfColor);
			int k=0;
			while(true){
				if(initialPriority[priorityOfColor+k] == -1){
					initialPriority[priorityOfColor+k] = i;
					break;
				}
				k++;
			}
		}

		System.out.print(" >>>  initialPriority: ");
		for(int ii=0; ii<numColors; ii++){
			System.out.print(""+initialPriority[ii]+", ");
		}
		System.out.println();
	}
	
	/**
	 * updates the priority of the given color with the given happiness.
	 * @param colorIndex
	 * @param happiness
	 */
	public void updatePriority(int colorIndex, double happiness){

		weightedPercentInHand[colorIndex] = percentInHand[colorIndex] * happiness;
		
//		System.out.print(" >>>  weightedPercentInHand: ");
//		for(int ii=0; ii<weightedPercentInHand.length; ii++){
//			System.out.print(""+weightedPercentInHand[ii]+", ");
//		}
//		System.out.println();
		
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
		
		System.out.print(" >>>  weightedPriority: ");
		for(int ii=0; ii<numColors; ii++){
			System.out.print(""+weightedPriority[ii]+", ");
		}
		System.out.println();
		
		isWeightedPriorityComplete = true;
	}
	
	
	public Boolean isWeightedPriorityComplete(){
		return isWeightedPriorityComplete;
	}
}
