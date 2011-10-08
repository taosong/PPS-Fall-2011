package mapthatset.g1.util;

import java.util.ArrayList;
import java.util.List;

public class SetHelper {

	/**
	 * returns the intersection of 2 sets.
	 * <br/> if NO intersection, returns NULL.
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static List<Integer> setIntersection(List<Integer> set1, List<Integer> set2){
		List<Integer> setIntersection = new ArrayList<Integer>();
		for(Integer i : set1){
			if(set2.contains(i)){
				setIntersection.add(i);
			}
		}
		if(setIntersection.size() == 0){
			return null;
		}
		return setIntersection;
	}
	
	/**
	 * returs the difference. set1-set2
	 * <br/> if NO difference, returns NULL.
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static List<Integer> setDifference(List<Integer> set1, List<Integer> set2){
		List<Integer> setDifference = new ArrayList<Integer>();
		for(Integer i : set1){
			if(set2.contains(i)){
				continue;
			}
			setDifference.add(i);
		}
		if(setDifference.size() == 0){
			return null;
		}
		return setDifference;
	}
	
	/**
	 * return list of inferences, which are 
	 * <br/> q1- (q1 intersect q2) -> r1 - (r1 intersect r2)
	 * <br/> q2 - (q1 intersect q2) -> r2 - (r1 intersect r2)
	 * <br/> <b><i>if NO inferences, returns NULL.</i></b>
	 * @param qr1
	 * @param qr2
	 * @return
	 */
	public static List<QueryRound> inferredQueryRounds(QueryRound qr1, QueryRound qr2){
		List<QueryRound> inferredRounds = new ArrayList<QueryRound>();
		
		List<Integer> queryIntersection = setIntersection(qr1.getQuery(), qr2.getQuery()); 
		List<Integer> resultIntersection = setIntersection(qr1.getResult(), qr2.getResult()); 
		
		// check Disjoint queries and results and return null if anyone is disjoint, because no inference can be made from them.
		if(queryIntersection == null || queryIntersection.size() <= 0){
			// two queries are disjoint
			return null;
		}
		if(resultIntersection == null || resultIntersection.size() <= 0){
			// two results are disjoint
			return null;
		}
		
		if(queryIntersection != null && resultIntersection != null){
			QueryRound inferredQR1 = new QueryRound(queryIntersection, resultIntersection);
			inferredRounds.add(inferredQR1);
		}
		
		List<Integer> qr1QueryDifference = setDifference(qr1.getQuery(), queryIntersection);
		List<Integer> qr2QueryDifference = setDifference(qr2.getQuery(), queryIntersection);
		List<Integer> qr1ResultDifference = setDifference(qr1.getResult(), resultIntersection);
		List<Integer> qr2ResultDifference = setDifference(qr2.getResult(), resultIntersection);
		
		if(qr1QueryDifference != null && qr1ResultDifference != null && qr1QueryDifference.size() == qr1ResultDifference.size()){
			QueryRound inferredQR1 = new QueryRound(qr1QueryDifference, qr1ResultDifference);
			inferredRounds.add(inferredQR1);
		}
		if(qr2QueryDifference != null && qr2ResultDifference != null && qr2QueryDifference.size() == qr2ResultDifference.size()){
			QueryRound inferredQR2 = new QueryRound(qr2QueryDifference, qr2ResultDifference);
			inferredRounds.add(inferredQR2);
		}
		
		if(inferredRounds.size() == 0){
			return null;
		}
		return inferredRounds;
	}
	
	
}