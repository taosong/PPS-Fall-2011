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
	
	protected EatStrategy eatStrategy;
	protected OfferStrategy offerStrategy;
	protected PickStrategy pickStrategy;
	protected Infobase info; 

	@Override
	public void eat(int[] aintTempEat) {
		eatStrategy.update(aintTempEat,info);
		for(int i = 0; i<aintTempEat.length;i++){
			if(aintTempEat[i]!=0)
			{
				intLastEatIndex = i;
				intLastEatNum = aintTempEat[i];
				break;
			}
		}
	}
	
	@Override
	public void offer(Offer offTemp) {
		int[] aintOffer = new int[intColorNum];
		int[] aintDesire = new int[intColorNum];
		offerStrategy.getOffer(aintOffer,aintDesire,info);
		offTemp.setOffer( aintOffer, aintDesire );
	}

	@Override
	public void syncInHand(int[] aintInHand) {

	}

	@Override
	public void happier(double dblHappinessUp) {
		info.updateHappiness(dblHappinessUp,intLastEatIndex, intLastEatNum);
	}

	@Override
	public Offer pickOffer(Offer[] aoffCurrentOffers) {
		return pickStrategy.pick(aoffCurrentOffers,info);
	}

	@Override
	public void offerExecuted(Offer offPicked) {
		info.updateOfferExecute(offPicked);
	}

	@Override
	public void updateOfferExe(Offer[] aoffCurrentOffers) {
		info.updateOfferExe(aoffCurrentOffers);
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
	}

	@Override
	public String getClassName() {
		return "G1Player";
	}

	@Override
	public int getPlayerIndex() {
		return intPlayerIndex;
	}

}
