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
	Priority priority;

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
		
		for (int i = 0; i < numColors; ++i)
		{
			for (int j = 0; j < numPlayers; ++j)
			{
				estimatedSkittles[i][j] = estSkittlesPerColor;
			}
		}
		
		priority.initializePriority();
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
		// TODO Auto-generated method stub
		
	}

}
