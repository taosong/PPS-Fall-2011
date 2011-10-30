package g1player;

import skittles.sim.Offer;

public class Infobase {
	
	/**
	 * made Infobase as a singleton class.
	 */
	public static Infobase INFO_BASE = null;
	
	int[][] playerPreferences;
	int[][] estimatedSkittles;
	int numPlayers;
	int numColors;
	
	//private Priority priority;
	private Priority priority;
	private int desiredColorCount = 0; //'c' as per discussion terminology
	private int[] colorHappinessArray;//happiness matrix
	private int[] aintInHand;
	
	private int intColorNum;
	double dblHappiness;
	String strClassName;
	int intPlayerIndex;
	
	private double[] adblTastes;
	private int intLastEatIndex;
	private int intLastEatNum;


	/**
	 * get the infobase object using this static method.
	 * @return
	 */
	public static Infobase getInfoBase(){
		if(INFO_BASE == null){
			INFO_BASE = new Infobase();
		}
		return INFO_BASE;
	}
	
	private Infobase() {
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
			return true;
		else
			return false;
	}
		
	public void createTable(int numPlayers, int numColors, int skittlesPerPlayer)
	{
		playerPreferences = new int[numPlayers][numColors];
		estimatedSkittles = new int[numPlayers][numColors];
		this.numPlayers = numPlayers;
		this.numColors = numColors;
		
		int estSkittlesPerColor = skittlesPerPlayer/numColors;
		
		for (int i = 0; i < numPlayers; ++i)
		{
			for (int j = 0; j <numColors ; ++j)
			{
				estimatedSkittles[i][j] = estSkittlesPerColor;
			}
			// calculate desired number of colors for this round
			if (numPlayers >= numColors) {
				desiredColorCount = 1;
			} else {
				desiredColorCount = Math.round(numColors / numPlayers);
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
		// TODO Auto-generated method stub
		
	}

	public void updateOfferExecute(Offer offPicked) {
		// TODO Auto-generated method stub
		
	}

	public void updateOfferExe(Offer[] aoffCurrentOffers) {
		for (Offer o : aoffCurrentOffers)
		{
			int offeredBy = o.getOfferedByIndex();
			int tookOffer = o.getPickedByIndex();
			int[] desired = o.getDesire();
			int[] offered = o.getOffer();
			for (int i = 0; i < desired.length; ++i)
			{
				if (!o.getOfferLive())
				{
					INFO_BASE.playerPreferences[i][offeredBy] += desired[i];
					INFO_BASE.playerPreferences[i][offeredBy] -= offered[i];
					INFO_BASE.playerPreferences[i][tookOffer] -= desired[i];
					INFO_BASE.playerPreferences[i][tookOffer] += offered[i];
				}
			}
		}
	}
	public int getDesiredColorCount() {
		return desiredColorCount;
	}

	public void setDesiredColorCount(int desiredColorCount) {
		this.desiredColorCount = desiredColorCount;
	}

	public int[] getColorHappinessArray() {
		return colorHappinessArray;
	}

	public void setColorHappinessArray(int[] colorHappiness) {
		this.colorHappinessArray = colorHappiness;
	}

	public int getColorHappiness(int index) {
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


}
