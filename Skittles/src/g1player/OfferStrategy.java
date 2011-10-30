package g1player;

import java.rmi.dgc.Lease;
import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class OfferStrategy {
	private Infobase info;
	int c = 3 ; 	//how many colors we are going to have last round.N/k or 1.
	int count = 0;
	Random rand = new Random();
	
	public OfferStrategy(int c) {
		this.c=c;
	}
	
	/*
	 * We look for the most number of skittles we can get this term, in {C}, we don't care what kind of 
	 * skittles we are going to give away as long as it is not in C.
	 */
	public void getOffer(int[] aintOffer, int[] aintDesire, Infobase infoUpdate) {
		
		/*
		 * TODO:at the beginning of the game, we have little info about what other's like of dislike
		 * so the beginning rounds will have special strategy
		 */
			
		this.info = infoUpdate;
		int[] priorityArray = info.getPriority().getPriorityArray(); 
		 
		int[] maxOffers = new int[c]; //# of skittles others able to give us
		int[] colorOffers = new int[c]; //what should we offer
		int colorOffer=0;
		int colorGet=0;
		
		for (int i = 0; i < c; i++){
			maxOffers[i] = 0;
			for (int j = c+1;j < priorityArray.length;j++) 
			{
				int tempOffer = this.calculateOffer(priorityArray[i],priorityArray[j]);
				if (tempOffer<info.aintInHand[priorityArray[j]]) //check if we have that many of skittles?
				{
					tempOffer = info.aintInHand[priorityArray[j]];
				}
				if (tempOffer> maxOffers[i] ){ 
					maxOffers[i] = tempOffer;
					colorOffers[i]=priorityArray[j];
				}
			}
		}
		int maxQuantity = 0;
		for(int i=0;i<c;i++){
			if(maxQuantity<maxOffers[i]){
				maxQuantity=maxOffers[i];
				colorGet = priorityArray[i];
				colorOffer = colorOffers[i];
			}			
		}
		aintOffer[colorOffer]=maxQuantity;
		aintDesire[colorGet]=maxQuantity;
		
		//if we can't find perfect trade, propose some other trade.
		if(maxQuantity==0){   
			//TODO: take other's like/dislike into consideration
			int leastLike = info.getPriority().getLestPriorityColor();
			int quantity = rand.nextInt(info.aintInHand[leastLike]-1)+1;
			aintOffer[leastLike] = quantity;
			aintDesire[info.getPriority().getHighestPriorityColor()] = quantity;
		}
	}

	// this functions calculates the max number of colorGet we can get from trading colorOffer
	private int calculateOffer(int colorGet, int colorOffer) {
		int max = 0;
		for(int i = 0; i<info.numPlayers; i++){
			if(info.playerPreferences[i][colorOffer]>0 && info.playerPreferences[i][colorGet]<0)
			{
				if(max<info.estimatedSkittles[i][colorGet])
				{
					max=info.estimatedSkittles[i][colorGet];
				}
			}
		}
		return max;
	}

}
