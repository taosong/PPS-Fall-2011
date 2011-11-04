package skittles.g1player;

import java.util.Random;

import skittles.g1player.main.DummyMain;

public class OfferStrategy {
	private Infobase info;
	private int c = 2;
	// how many colors we are going to have last round.N/k or 1.

	protected int count = 0;
	private Random rand = new Random();
	private int colorNum;
	protected int[] offerTracker;
	private int lastGet, lastOffer;
	private boolean validOffer = false;

	protected OfferStrategy(Infobase infoUpdate) {
		this.info = infoUpdate;
		this.colorNum = info.getIntColorNum();
		this.offerTracker = new int[colorNum];
	}

	/*
	 * We look for the most number of skittles we can get this term, in {C}, we
	 * don't care what kind of skittles we are going to give away as long as it
	 * is not in C.
	 */
	public void getOffer(int[] aintOffer, int[] aintDesire, Infobase infoUpdate) {
		this.info = infoUpdate;
		this.c = info.getDesiredColorCount();
		count++;
		DummyMain.printArray(info.getAintInHand(), "in hand");
		DummyMain.printArray(this.offerTracker, "Offer Tracker");
		DummyMain.printArray(info.ourselves, "Ourself");
		if (info.denied && this.validOffer) {
			offerTracker[this.lastGet]++;
			System.out.println("update ++" + String.valueOf(this.lastGet));
		}

		int[] priorityArray = info.getPriority().getPriorityArray();
		DummyMain.printArray(priorityArray, "OfferStrategy priorityArray:");
		colorNum = priorityArray.length;

		int[] maxOffers = new int[c]; // # of skittles others able to give us
		int[] colorOffers = new int[c]; // what should we offer
		int colorOffer = 0;
		int colorGet = 0;

		if (count == 1) {
			/*
			 * at the beginning of the game, we have little info about what
			 * other's like of dislike so the beginning rounds will have special
			 * strategy
			 */
			int tempLeast = colorNum;
			int leastLike = priorityArray[tempLeast - 1];
			// System.out.println("leastLike<<<<   ".concat(String.valueOf(leastLike)));
			while (info.getAintInHand()[leastLike] == 0) {
				leastLike = priorityArray[--tempLeast];
				// System.out.println("tempLeast<<<<   ".concat(String.valueOf(leastLike)));
			}
			int quantity = info.getAintInHand()[leastLike];
			quantity = Math.min(info.getAintInHand()[leastLike], 1);

			aintOffer[leastLike] = quantity;
			aintDesire[priorityArray[rand.nextInt(c)]] = quantity;
			DummyMain.printArray(aintOffer, "Offer: ");
			this.validOffer = false;
			/*
			 *  The following lines try to identify ourselves in the first round
			 *  It will be more complex in the tournament
			 */
			for(int i=0;i<colorNum;i++){
				aintDesire[i]=aintOffer[i]=info.getAintInHand()[i];
			}

			return;
		}

		for (int i = 0; i < c; i++) {
			maxOffers[i] = 0;
			for (int j = c; j < priorityArray.length; j++) {
				int tempOffer = this.calculateOffer(priorityArray[i],
						priorityArray[j]);
				if (tempOffer > info.getAintInHand()[priorityArray[j]])
				// check if we have that many of skittles?
				{
					tempOffer = info.getAintInHand()[priorityArray[j]];
				}
				if (tempOffer > maxOffers[i]) {
					maxOffers[i] = tempOffer;
					colorOffers[i] = priorityArray[j];
				}
			}
		}
		int maxQuantity = 0;
		for (int i = 0; i < c; i++) {
			if (maxQuantity < maxOffers[i]) {
				maxQuantity = maxOffers[i];
				colorGet = priorityArray[i];
				colorOffer = colorOffers[i];
			}
		}
		aintOffer[colorOffer] = maxQuantity;
		aintDesire[colorGet] = maxQuantity;

		// if we can't find perfect trade, propose some other trade.
		if (maxQuantity == 0) {
			int quantity = 0;
			int tempLeast = colorNum;
			int leastLike = priorityArray[--tempLeast]; // dhaval modified for
														// array index out of
														// bound exception
			quantity = (int) (info.getAintInHand()[leastLike] / Math.pow(2,
					offerTracker[colorGet]));
			while (info.getAintInHand()[leastLike] == 0 || quantity == 0) {
				if (tempLeast == 0) {
					//TODO: give eating strategy a signal
					return;
				}
				leastLike = priorityArray[--tempLeast];
				System.out.println("RAND: tempLeast<<<<   ".concat(String
						.valueOf(tempLeast)));
				quantity = (int) (info.getAintInHand()[leastLike] / Math.pow(2,
						offerTracker[colorGet]));
			}

			aintOffer[leastLike] = quantity;
			aintDesire[priorityArray[rand.nextInt(c)]] = quantity;
			this.validOffer = true;
		} else {
			this.lastGet = colorGet;
			this.lastOffer = colorOffer;
			this.validOffer = true;
		}
		DummyMain.printArray(aintOffer, "Offer: ");
	}

	// this functions calculates the max number of colorGet we can get from
	// trading colorOffer
	private int calculateOffer(int colorGet, int colorOffer) {
		int max = 0;
		for (int i = 0; i < info.numPlayers; i++) {
			if (info.playerPreferences[i][colorOffer] > 0
					&& info.playerPreferences[i][colorGet] < 0) {
				if (max < info.estimatedSkittles[i][colorGet]) {
					if (info.isPlayerInactive(i)) {
						System.out.println("Inactive Player  "
								+ String.valueOf(i));
					} else
						max = info.estimatedSkittles[i][colorGet];
				}
			}
		}
		return (int) (max / Math.pow(2, offerTracker[colorGet]));
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
