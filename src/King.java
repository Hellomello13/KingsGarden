import org.newdawn.slick.SlickException;


public class King extends PlayerPiece {

	boolean isCaptured;
	boolean isInGarden;
	boolean isRescued;
	
	King() throws SlickException
	{
		isAlive = true;
		isCaptured = false;
		isInGarden = true;
		isRescued = true;
		
		init(); //init location
		
		loadImage("king.png");
	}
}
