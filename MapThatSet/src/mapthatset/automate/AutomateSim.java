package mapthatset.automate;

import mapthatset.sim.MapThatSet;

public class AutomateSim {

	public static int QUERY_LENGTH = 0;
	public static int Z_VALUE = 0;

	public static void main(String[] args) {
		System.out.println(" Automating ");
		Runtime r = Runtime.getRuntime();

		MapThatSet.initialize();

		for (int z = 1; z <= 6; z++) {
			Z_VALUE++;
			if(Z_VALUE == 1){
				System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("Unique");
			}else if(Z_VALUE == 2){
				System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("Random 10");
			}else if(Z_VALUE == 3){
				System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("Random");
			}else if(Z_VALUE == 4){
				System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("binary");
			}else if(Z_VALUE == 5){
				System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("3-ary");
			}else if(Z_VALUE == 6){
				System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("4-ary");
			}
			for (int x = 2; x < 200; x = x + 5) {
				for(int x1 = 1; x1 <= 3; x1++){
					MapThatSet.auto_main("" + x, 1, args);
					r.gc();
				}
			}
			System.out.println("++++++++++++++----------<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

	}

}
