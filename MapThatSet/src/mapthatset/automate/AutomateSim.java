package mapthatset.automate;

import mapthatset.sim.MapThatSet;

public class AutomateSim {
	
	public static int QUERY_LENGTH = 0;
	public static int QUERY_OVERLAP = 0;

	public static void main(String[] args) {
		System.out.println(" Automating ");
		Runtime r = Runtime.getRuntime();
		
		MapThatSet.initialize();
		
		for(int x=2; x<20; x=x+10){
			for(QUERY_LENGTH = 1; QUERY_LENGTH<50; QUERY_LENGTH++){
				MapThatSet.auto_main(""+100, 1, args);
				r.gc();
			}
			System.out.println("--");
		}
		
	}
	
}
