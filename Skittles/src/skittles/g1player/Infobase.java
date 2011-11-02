package skittles.g1player;

import skittles.sim.Offer;

public class Infobase {
	
	/**
	 * made Infobase as a singleton class.
	 */
	
	int[][] playerPreferences = null;
	int[][] estimatedSkittles = null;
	public int[] roundsInactive = null;
	int numPlayers;
	
	//private Priority priority;
	private Priority priority;
	private int desiredColorCount = 0; //'c' as per discussion terminology
	private double[] colorHappinessArray;//happiness matrix
	private int[] aintInHand;
	private int initialSkittlesPerPlayer;
	
	private int intColorNum;
	double dblHappiness;
	String strClassName;
	int intPlayerIndex;
	public boolean roundComplete = false;
	
	private double[] adblTastes;
	private int intLastEatIndex;
	private int intLastEatNum;
	protected boolean denied; // if last offer was denied

	
	public Infobase() {
		priority = new Priority();
	}
	
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public boolean tablesExist()
	{
		if (playerPreferences == null || estimatedSkittles == null)
			return false;
		else
			return true;
	}
		
	public void createTable(int numPlayers)
	{
		playerPreferences = new int[numPlayers][intColorNum];
		estimatedSkittles = new int[numPlayers][intColorNum];
		roundsInactive = new int[numPlayers];
		this.numPlayers = numPlayers;
		
		int estSkittlesPerColor = initialSkittlesPerPlayer/intColorNum;
		
		for (int i = 0; i < numPlayers; ++i)
		{
			for (int j = 0; j <intColorNum ; ++j)
			{
				estimatedSkittles[i][j] = estSkittlesPerColor;
			}
			// calculate desired number of colors for this round
			if (numPlayers >= intColorNum) {
				desiredColorCount = 1;
			} else {
				desiredColorCount = Math.round(intColorNum / numPlayers);
			}
		}

	}
	
	public void updatePlayerPreferences(int playerIndex, Offer playerOffer)
	{
		
	}
	
	public void updateEstSkittles(int playerIndex, Offer playerOffer)
	{
		
	}
	public void updateHappiness(double dblHappinessUp, int intLastEatIndex, int intLastEatNum) {
		if (intLastEatNum == 1)
		{
			this.colorHappinessArray[intLastEatIndex] = dblHappinessUp;
		}
	}

	public void updateOfferExecute(Offer offPicked) {
		
		int[] skittlesWeHave = this.getAintInHand();
		int[] giving = offPicked.getOffer();
		int[] getting= offPicked.getDesire();
		for (int j = 0; j < getting.length; ++j)
		{
			skittlesWeHave[j] -= giving[j];
			skittlesWeHave[j] += getting[j];
		}	
		this.setAintInHand(skittlesWeHave);		
	}
	
	/**
	 * This method takes in a player index and returns whether or not that
	 * player is considered inactive.<br />
	 * Passing a negative number for the player index will signal the function
	 * to check if EVERY player is inactive, and it will return true if all 
	 * other players are considered inactive.<br />
	 * 
	 * Right now the check for whether a player is inactive is that they
	 * have offered a null offer for the last three consecutive rounds.
	 * @param playerIndex index of player, or negative number to indicate all players
	 * 						besides this player
	 * @return true if the player(s) is considered inactive
	 * 			false if the player(s) is considered active
	 */
	public boolean isPlayerInactive(int playerIndex)
	{
		if (playerIndex < 0)
		{
			for (int i = 0; i < numPlayers; ++i)
			{
				if (roundsInactive[i] < 3 && i != intPlayerIndex)
				{
					return false;
				}
			}
			return true;
		}
		else if (playerIndex < numPlayers)
		{
			if (roundsInactive[playerIndex] >= 3)
			{
				return true;
			}
		}
		return false;
	}

	public void updateOfferExe(Offer[] aoffCurrentOffers) {
		for (Offer o : aoffCurrentOffers)
		{
			int offeredBy = o.getOfferedByIndex();
			int tookOffer = o.getPickedByIndex();
			if (isNullOffer(o))
			{
				roundsInactive[offeredBy] += 1;
			}
			else
			{
				roundsInactive[offeredBy] = 0;
			}
//			if(tookOffer == -1 || offeredBy == 1 || offeredBy == intPlayerIndex ){ //dhaval, dont update for our player
			if(tookOffer == -1 && offeredBy == intPlayerIndex ){ //i dont' know why offeredBy == 1 is included.  It doesn't seem to make sense
				this.denied = true;
				System.out.println("offer denied");
			}
			int[] desired = o.getDesire();
			int[] offered = o.getOffer();

			if (!o.getOfferLive() && tookOffer != -1) // add tookOffer != -1 to avoid Exception ArrayIndexOutOfBoundsException: -1
			{										  // fixed, which means sometimes when !offeralive, tookOffer still can be -1
				updatePlayerSkittles(o);
				for (int i = 0; i < desired.length; ++i)
				{
					this.playerPreferences[offeredBy][i] += desired[i];
					this.playerPreferences[offeredBy][i] -= offered[i];
					this.playerPreferences[tookOffer][i] -= desired[i]; // why sometimes Exception ArrayIndexOutOfBoundsException: -1?
					this.playerPreferences[tookOffer][i] += offered[i]; // tookOffer == -1? Not possible....
				}	
			}
		}
	}
	
	private boolean isNullOffer(Offer off)
	{
		int[] desired = off.getDesire();
		int[] offered = off.getOffer();
		for (int i = 0; i < desired.length; ++i)
		{
			if (desired[i] != 0 || offered[i] != 0)
				return false;
		}
		return true;
	}
	
	private void updatePlayerSkittles(Offer off)
	{
		// do something here to update estimated skittles
	}
	
	public int getDesiredColorCount() {
		return desiredColorCount;
	}

	public void setDesiredColorCount(int desiredColorCount) {
		this.desiredColorCount = desiredColorCount;
	}

	public double[] getColorHappinessArray() {
		return colorHappinessArray;
	}

	public void setColorHappinessArray(double[] colorHappiness) {
		this.colorHappinessArray = colorHappiness;
	}

	public double getColorHappiness(int index) {
		return colorHappinessArray[index];
	}

	public int[] getAintInHand() {
		return aintInHand;
	}

	public void setAintInHand(int[] aintInHand) {
		this.aintInHand = aintInHand;
	}

	public int getIntColorNum() {
		return intColorNum;
	}

	public void setIntColorNum(int intColorNum) {
		this.intColorNum = intColorNum;
	}

	public double getDblHappiness() {
		return dblHappiness;
	}

	public void setDblHappiness(double dblHappiness) {
		this.dblHappiness = dblHappiness;
	}

	public String getStrClassName() {
		return strClassName;
	}

	public void setStrClassName(String strClassName) {
		this.strClassName = strClassName;
	}

	public int getIntPlayerIndex() {
		return intPlayerIndex;
	}

	public void setIntPlayerIndex(int intPlayerIndex) {
		this.intPlayerIndex = intPlayerIndex;
	}

	public double[] getAdblTastes() {
		return adblTastes;
	}

	public void setAdblTastes(double[] adblTastes) {
		this.adblTastes = adblTastes;
	}
	
	public void setAdblTasteElement(int index , double  value){
		this.adblTastes[index] = value;
	}

	public int getIntLastEatIndex() {
		return intLastEatIndex;
	}

	public void setIntLastEatIndex(int intLastEatIndex) {
		this.intLastEatIndex = intLastEatIndex;
	}

	public int getIntLastEatNum() {
		return intLastEatNum;
	}

	public void setIntLastEatNum(int intLastEatNum) {
		this.intLastEatNum = intLastEatNum;
	}

	public int getInitialSkittlesPerPlayer() {
		return initialSkittlesPerPlayer;
	}

	public void setInitialSkittlesPerPlayer(int initialSkittlesPerPlayer) {
		this.initialSkittlesPerPlayer = initialSkittlesPerPlayer;
	}


}
