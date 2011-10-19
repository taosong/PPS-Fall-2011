package mapthatset.g1.util;

import java.util.List;

/**
 * Utility function that keeps track of paramenter for each rounds. Will be
 * useful - I will explain
 * 
 * @author Kanna
 * 
 */
public class QueryRound {

	QueryParams queryParam = null; // for retriving start and end index of the query
	List<Integer> query = null; // query used
	List<Integer> result = null; // result of the query
	int queryIndex = -1; // the number of the Query
	int confidenceLevel = -1; // current confidance level... not used...
	Boolean isInferred = null;

	public QueryRound(QueryParams queryParam, int queryIndex,
			List<Integer> query) {
		this.queryParam = queryParam;
		this.queryIndex = queryIndex;
		this.query = query;
		this.isInferred = false;
	}

	public QueryRound(List<Integer> query, List<Integer> result) {
		this.query = query;
		this.result = result;
		this.isInferred = true;
	}

	public QueryParams getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(QueryParams queryParams) {
		this.queryParam = queryParams;
	}

	public List<Integer> getQuery() {
		return query;
	}

	public void setQuery(List<Integer> query) {
		this.query = query;
	}

	public List<Integer> getResult() {
		return result;
	}

	public void setResult(List<Integer> result) {
		this.result = result;
	}

	public int getQueryIndex() {
		return queryIndex;
	}

	public void setQueryIndex(int queryIndex) {
		this.queryIndex = queryIndex;
	}

	@Override
	public String toString() {
		return " [QueryRound] for round=" + queryIndex + " - query = " + query
				+ "\t\t result = " + result;
	}
}
