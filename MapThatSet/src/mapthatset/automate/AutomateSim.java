package mapthatset.automate;

import mapthatset.sim.MapThatSet;

public class AutomateSim {
	
	public static int QUERY_LENGTH = 0;
	public static int QUERY_OVERLAP = 0;

	public static void main(String[] args) {
		System.out.println(" Automating ");
		
		MapThatSet.initialize();
		
		for(int x=20; x<200; x=x+10){
			for(QUERY_LENGTH = x/2; QUERY_LENGTH<2*x/3; QUERY_LENGTH++){
				MapThatSet.auto_main(""+x, 1, args);
			}
		}
		
	}
	
}
