package g1player;

import skittles.sim.Offer;

public class Infobase {
	int[][] playerPreferences;
	int[][] estimatedSkittles;
	
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
		
		int estSkittlesPerColor = skittlesPerPlayer/numColors;
		
		for (int i = 0; i < numColors; ++i)
		{
			for (int j = 0; j < numPlayers; ++j)
			{
				estimatedSkittles[i][j] = estSkittlesPerColor;
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
		// TODO Auto-generated method stub
		
	}

}
