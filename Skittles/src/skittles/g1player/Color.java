package skittles.g1player;

public class Color implements Comparable<Color>{

	int colorIndex;
	Double happinessValue;
	Double percentInHand;
	Double compValue;
	
	public Color() {
	}
	
	public Color(int index, double happ, double percentInHnd) {
		this.colorIndex = index;
		this.happinessValue = new Double(happ);
		this.percentInHand = new Double(percentInHnd);
		this.compValue = new Double(happ * percentInHnd);
	}

	@Override
	public int compareTo(Color c) {
		if(this.compValue < (c.compValue)){
			return 1;
		} else if (this.compValue > (c.compValue)) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object c) {
		return this.colorIndex==((Color)c).colorIndex;
	}
	
	@Override
	public String toString() {
		return ""+colorIndex;
	}
}
