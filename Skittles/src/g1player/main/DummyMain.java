package g1player.main;

import g1player.Infobase;

public class DummyMain {

	public static void main(String[] args) {
		int[] aintInHand = new int[]{6,5,50,5,6};
		Infobase.getInfoBase().getPriority().initializePriority(aintInHand);
		Infobase.getInfoBase().getPriority().updatePriority(0, 0.5);
		Infobase.getInfoBase().getPriority().updatePriority(1, 1.0);
		Infobase.getInfoBase().getPriority().updatePriority(2, 0.2);
		Infobase.getInfoBase().getPriority().updatePriority(3, 0.0);
		Infobase.getInfoBase().getPriority().updatePriority(4, 1.0);
	}
	
}
