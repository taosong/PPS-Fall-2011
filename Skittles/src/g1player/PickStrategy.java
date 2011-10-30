package g1player;

import skittles.sim.Offer;

public class PickStrategy {

	public Offer pick(Offer[] aoffCurrentOffers, Infobase info) {
		/* make an array to store the 'scores' of the offers, which is how beneficial they are to us */
		double[] offerScores = new double[aoffCurrentOffers.length];		

		for (Offer o : aoffCurrentOffers)
		{
			double score = 0;
			int[] weReceive = o.getOffer();
			int[] weGiveUp = o.getDesire();
			for (int i = 0; i < weReceive.length; ++i)
			{
				//add up scores to get weight
			}
			
		}
		
		// get best offer and take it
		// or if no good offers, pass
		
		
		return null;
	}

}
