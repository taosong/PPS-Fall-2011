package g1player;

public class OfferStrategy {
	private Infobase info;
	int c; //how many colors we are going to have last round, as we discussed.

	public void getOffer(int[] aintOffer, int[] aintDesire, Infobase infoUpdate) {
		// TODO Auto-generated method stub
		this.info = infoUpdate;
		int[] priorityArray = info.priority.getPriorityArray();
		int[] maxOffers = new int[c];
		int[] colorOffers = new int[c];
		int colorOffer=0;
		int colorGet=0;
		for (int i = 0; i < c; i++){
			priorityArray[i]=0;
			maxOffers[i] = 0;
			for (int j = c+1;j < priorityArray.length;j++) 
			{
				int tempOffer = this.calculateOffer(priorityArray[i],priorityArray[j]);
				if (tempOffer> maxOffers[i]){
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
		if(maxQuantity==0){
			
		}
	}

	// this functions calculates the max number of color i we can get from trading j
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
