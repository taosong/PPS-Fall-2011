package mapthatset.g1.util;

/**
 * Keeps the prameters for the guess together in a object.
 * 
 * @author Kanna
 * 
 */
public class QueryParams {

	int startIndex = -1, endIndex = -1, length = -1;

	public QueryParams() {
	}

	public QueryParams(int start, int end) {
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

	@Override
	public String toString() {
		return " [QueryParams]  start = " + startIndex + "\t end = " + endIndex
				+ "\t length = " + length;
	}

}
