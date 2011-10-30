package g1player.main;

import g1player.Infobase;

public class DummyMain {

	public static void main(String[] args) {
		int[] aintInHand = new int[]{3,2,5,1,2};
		Infobase.getInfoBase().getPriority().initializePriority(aintInHand);
		Infobase.getInfoBase().getPriority().updatePriority(1, 0.1);
	}
	
}
