package g1player;

import skittles.sim.Offer;
import skittles.sim.Player;

public class G1Player extends Player {
	
	private int[] aintInHand;
	private int intColorNum;
	double dblHappiness;
	String strClassName;
	int intPlayerIndex;
	
	private double[] adblTastes;
	private int intLastEatIndex;
	private int intLastEatNum;
	
	EatStrategy eatStrategy;
	OfferStrategy offerStrategy;
	PickStrategy pickStrategy;
	Infobase info;

	@Override
	public void eat(int[] aintTempEat) {
		eatStrategy.update(aintTempEat);
		// TODO Auto-generated method stub

	}
	@Override
	public void offer(Offer offTemp) {
		// TODO Auto-generated method stub
		int[] aintOffer = new int[intColorNum];
		int[] aintDesire = new int[intColorNum];
		offerStrategy.getOffer(aintOffer,aintDesire);
		offTemp.setOffer( aintOffer, aintDesire );
	}

	@Override
	public void syncInHand(int[] aintInHand) {
		// TODO Auto-generated method stub

	}

	@Override
	public void happier(double dblHappinessUp) {
		// TODO Auto-generated method stub
		info.updateHappiness(dblHappinessUp);
	}

	@Override
	public Offer pickOffer(Offer[] aoffCurrentOffers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void offerExecuted(Offer offPicked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOfferExe(Offer[] aoffCurrentOffers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(int intPlayerIndex, String strClassName,
			int[] aintInHand) {
		this.intPlayerIndex = intPlayerIndex;
		this.strClassName = strClassName;
		this.aintInHand = aintInHand;
		intColorNum = aintInHand.length;
		dblHappiness = 0;
		adblTastes = new double[ intColorNum ];
		for ( int intColorIndex = 0; intColorIndex < intColorNum; intColorIndex ++ )
		{
			adblTastes[ intColorIndex ] = -1;
		}
		// TODO Auto-generated method stub

	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "G1Player";
	}

	@Override
	public int getPlayerIndex() {
		// TODO Auto-generated method stub
		return intPlayerIndex;
	}

}
