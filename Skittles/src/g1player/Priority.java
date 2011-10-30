package g1player;


public class Priority {

	/**
	 * Priorities of all the colors calculated based on only the knowledge of
	 * the % of each color.
	 */
	int[] initialPriority;
	/**
	 * Priorities of all the colors calculated based on the knowledge of the %
	 * of each color and the happiness of each color.
	 */
	int[] weightedPriority;
	/**
	 * the array with the percentages of all colors in hand. 
	 */
	double[] percentInHand;
	/**
	 * the array with the percentages of all colors in hand times their happiness. 
	 */
	double[] weightedPercentInHand;
	
	/**
	 * Dummy Constructor.
	 */
	public Priority() {
	}
	
	/**
	 * returns the index of the color, that has the highest priority.
	 * @return
	 */
	public int getHighestPriorityColor(){
		
		return -1;
	}

	/**
	 * returns the index of the color, that has the highest priority.
	 * @return
	 */
	public int getLestPriorityColor(){
		
		return -1;
	}
	
	/**
	 * returns the weightedPriority array. <br/>
	 * if weightedPriority is not yet complete, it returns the initialProirity array.
	 * @return
	 */
	public int[] getPriorityArray(){
		
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
		
		// calculate the percent of each color.
		for(int i=0; i<numColors; i++){
			percentInHand[i] = aintInHand[i]/totalSkittles;
		}
		
		int priorityOfColor;
		for(int i=0; i<numColors; i++){
			priorityOfColor = 0;
			for(int j=0; j<numColors; j++){
				if(percentInHand[i] < percentInHand[j]) {
					priorityOfColor++;
				}
			}
			int k=0;
			while(initialPriority[priorityOfColor+1+k] == -1){
				initialPriority[priorityOfColor+1+k] = i;	
			}
		}
		
		System.out.println(" >>>  initialPriority: ");
		for(int i=0; i<numColors; i++){
			System.out.print(""+initialPriority[i]+", ");
		}
	}
	
	/**
	 * updates the priority of the given color with the given happiness.
	 * @param colorIndex
	 * @param happiness
	 */
	public void updatePriority(int colorIndex, double happiness){

		weightedPercentInHand[colorIndex] = percentInHand[colorIndex] * happiness;
		
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
		
		int numColors = initialPriority.length; 
		int priorityOfColor;
		for(int i=0; i<numColors; i++){
			priorityOfColor = 0;
			for(int j=0; j<numColors; j++){
				if(weightedPercentInHand[i] < weightedPercentInHand[j]) {
					priorityOfColor++;
				}
			}
			int k=0;
			while(weightedPriority[priorityOfColor+1+k] == -1){
				weightedPriority[priorityOfColor+1+k] = i;	
			}
		}
		
	}
	
}
