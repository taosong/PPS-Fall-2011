package mapthatset.g1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import mapthatset.sim.GuesserAction;
import mapthatset.sim.Mapper;

public class G1Mapper extends Mapper {

	int intMappingLength;
	String strID = "G1Mapper";

	private ArrayList<Integer> getNewMapping() {
		ArrayList<Integer> alNewMapping = new ArrayList<Integer>();
		Random r = new Random(System.currentTimeMillis());
		for (int intIndex = 0; intIndex < intMappingLength; intIndex++) {
//			alNewMapping.add( 1 );
			alNewMapping.add( intIndex + 1 );
//			alNewMapping.add(1 + (intIndex % 2));
//			alNewMapping.add(1 + r.nextInt(2));
//			alNewMapping.add(1+(int)(Math.random()*(intMappingLength-1)));
//			alNewMapping.add(1 + r.nextInt(intMappingLength));
		}
		Collections.shuffle(alNewMapping);
		System.out.println("The mapping is: " + alNewMapping);
		return alNewMapping;
	}

	@Override
	public ArrayList<Integer> startNewMapping(int intMappingLength) {
		this.intMappingLength = intMappingLength;
		return getNewMapping();
	}

	@Override
	public void updateGuesserAction(GuesserAction gsaGA) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getID() {
		return strID;
	}

}
