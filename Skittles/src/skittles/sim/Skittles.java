package skittles.sim;

public class Skittles 
{
	public static void main( String[] args )
	{		
		while(true){
		Game gamNew = new Game( "GameConfig.xml" );
		gamNew.runGame();
		}
	}
}
