package mapthatset.g1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import mapthatset.automate.AutomateSim;
import mapthatset.g1.util.GuessAnalyser;
import mapthatset.sim.GuesserAction;
import mapthatset.sim.Mapper;

public class AshMapper extends Mapper
{
	int intMappingLength;
	String strID = "UniqueDumbMapper";
	ArrayList<Integer> allGuess =  new ArrayList<Integer>();
	ArrayList<GuesserAction> allQueries = new ArrayList<GuesserAction>();
	ArrayList<GuesserAction> allQueriesFromPreviousRound = new ArrayList<GuesserAction>();
	boolean firstRound = true;

//	private ArrayList< Integer > getNewMapping() {
//		ArrayList< Integer > alNewMapping = new ArrayList< Integer >();
//		for ( int intIndex = 0; intIndex < intMappingLength; intIndex ++ )
//		{
//			
////			alNewMapping.add( 1 );
////			alNewMapping.add( intIndex + 1 );
////			alNewMapping.add( 1 + (intIndex%2) );
////			alNewMapping.add(1+(int)(Math.random()*(intMappingLength-1)));
//		}
//		Collections.shuffle(alNewMapping);
////		System.out.println( "The mapping is: " + alNewMapping );
//		return alNewMapping;
//	}

	private ArrayList< Integer > getNewMapping() {

		long seed = System.currentTimeMillis();
		Random rand = new Random(seed);
//		int n = rand.nextInt(15)+1;
//		if(n < 5){
//			return naryMapping(n, rand);
//		} else if(n<10){
//			return uniqueMapping();
//		} else {
//			return randomMapping(rand);
//		}
		
		int z = AutomateSim.Z_VALUE;
		switch(z){
			case 1: //Permutation
				return uniqueMapping();
			case 2: //mix mapper
				return naryMapping(2, rand);
			case 3: //more random
				return randomMapping(rand);
			case 4: //binary
				return naryMapping(2, rand);
			case 5: //ternary
				return naryMapping(3, rand);
			case 6: //four-ary
				return naryMapping(4, rand);
			default:
				return naryMapping(1, rand);
		}

	}
	
	 public ArrayList<Integer> naryMixMapping (int nary,Random r) {
         ArrayList<Integer> alMapping = new ArrayList<Integer>();
         
         for (int i=0; i< intMappingLength/2 ; i++){
                 alMapping.add( r.nextInt( nary ) + 1 );
         }
         for ( int i=intMappingLength/2 ; i<intMappingLength ; i++){
                 alMapping.add(r.nextInt(intMappingLength)+1);
         }
         System.out.println(alMapping);
         return alMapping;
	 }
	
	/*
	 * This function returns only unique mapping.
	 * i.e. Each element - maps to a unique element.
	 */
	public ArrayList<Integer> uniqueMapping() {
		ArrayList<Integer> alMapping = new ArrayList<Integer>();

		for (int i=0 ; i<intMappingLength;i++){
			alMapping.add(i+1);
		}
		Collections.shuffle(alMapping);
//		System.out.println( "The mapping is:<UNIQUE> " + alMapping );
		return alMapping;
	}

	/*
	 * This function returns only unique mapping.
	 * i.e. Each element - maps to a unique element.
	 */
	public ArrayList<Integer> randomMapping(Random r) {
		ArrayList<Integer> alMapping = new ArrayList<Integer>();

		for (int i=0 ; i<intMappingLength;i++){
			alMapping.add(r.nextInt(intMappingLength) + 1);
		}
		Collections.shuffle(alMapping);
//		System.out.println( "The mapping is:<RANDOM> " + alMapping );
		return alMapping;
	}

	@Override
	public void updateGuesserAction(GuesserAction gsaGA) 
	{
		// dumb mapper do nothing here

		/*
		 * Add the new guesser query to the ArrayList <GuesserAction> allQueries
		 */
//		System.out.println("The elements that the guesser asked this time are :"+ gsaGA.getContent().toString());
		allQueries.add(gsaGA);

	}

	public ArrayList<Integer> naryMapping(int n, Random rand){
		ArrayList<Integer> alMapping = new ArrayList<Integer>();
		for ( int intIndex = 0; intIndex < intMappingLength; intIndex ++ )
		{
			alMapping.add( rand.nextInt( n ) + 1 );
		}
//		System.out.println( "The mapping is:<MOD("+n+")> " + alMapping );
		return alMapping;
	}

	/*
	 * This function analyses the Guesser Action, and then returns 
	 * an integer value, that helps to generate the next mapping.
	 */ 
	public int analyseGuesserAction (){

		int metric = 0;

		/*
		 * If its the first match between Mapper and Guesser.
		 */
		if (allQueriesFromPreviousRound.isEmpty() && firstRound){
			return metric;
		}

		/*
		 * Subsequent Mapping
		 */
		if (!allQueriesFromPreviousRound.isEmpty() && !firstRound){

			/*for (Iterator<GuesserAction> it = allQueries.iterator(); it.hasNext();){
				//GuesserAction tempGuesserAction = it.next();
				//ArrayList<Integer> tempContent = tempGuesserAction.getContent();
			
				
			}*/
			metric = GuessAnalyser.numOfTimesElementCountAppearsInAllQueries(allQueriesFromPreviousRound, 2);

		}

		return metric;
	}

	@Override
	public ArrayList<Integer> startNewMapping(int intMappingLength){
		// TODO Auto-generated method stub
		this.intMappingLength = intMappingLength;
		return getNewMapping();
	}

	@Override
	public String getID(){
		// TODO Auto-generated method stub
		return strID;
	}

	public void notFirstRound(){
		/*
		 * Change the flag value of the 
		 * first round to false.
		 */
		firstRound = false;
	}

	public void prepareForNextRound (){

		//allQueriesFromPreviousRound = allQueries;
		allQueriesFromPreviousRound.clear();
		for (int i=0;i<allQueries.size();i++){
			allQueriesFromPreviousRound.add(allQueries.get(i));
		}
		allQueries.clear();
	}
}
