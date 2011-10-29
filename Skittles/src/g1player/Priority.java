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
	public void initializePriority(){
		
	}
	
	/**
	 * updates the priority of the given color with the given happiness.
	 * @param colorIndex
	 * @param happiness
	 */
	public void updatePriority(int colorIndex, int happiness){
		
	}
	
	
}
