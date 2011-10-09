package mapthatset.automate;

import mapthatset.sim.MapThatSet;

public class AutomateSim {
	
	public static int QUERY_LENGTH = 0;
	public static int QUERY_OVERLAP = 0;

	public static void main(String[] args) {
		System.out.println(" Automating ");
		Runtime r = Runtime.getRuntime();
		
		MapThatSet.initialize();
		
		for(int x=100; x<1000; x=x+100){
			for(QUERY_LENGTH = x/30; QUERY_LENGTH<x/5; QUERY_LENGTH=QUERY_LENGTH+5){
				MapThatSet.auto_main(""+x, 1, args);
				r.gc();
			}
			System.out.println("--");
		}
		
	}
	
}
