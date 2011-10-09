package mapthatset.automate;

import mapthatset.sim.MapThatSet;

public class AutomateSim {
	
	MapThatSet mapThatSet = new MapThatSet();
	
	public static int QUERY_LENGTH = 0;

	public static void main(String[] args) {
		System.out.println(" Automating ");
		
//		MapThatSet.auto_main("500", args);
		
		for(int x=20; x<200; ){
			System.out.println(" for x = " + x);
			MapThatSet.auto_main(""+x, args);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x=x+50;
		}
		
	}
	
}
