package mapthatset.automate;

import mapthatset.sim.MapThatSet;

public class AutomateSim {
	
	public static int QUERY_LENGTH = 0;

	public static void main(String[] args) {
		System.out.println(" Automating ");
		
		MapThatSet.initialize();
		for(int x=20; x<200; ){
			MapThatSet.auto_main(""+x, 1, args);
			x=x+10;
		}
		
	}
	
}
