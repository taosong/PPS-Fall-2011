package mapthatset.g1.util;

public class GuessParams {
	
	int startIndex, endIndex, length;
	
	public GuessParams() {
	}
	
	public GuessParams(int start, int end){
		startIndex = start;
		endIndex = end;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getLength() {
		length = endIndex - startIndex;
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	

}
