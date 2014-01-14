import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Soldier extends PlayerPiece {

	boolean hasMoved;
	boolean hasSlid;
	boolean hasTeleported;
	boolean isAttacking;
	
	Image image;
	
	Soldier() throws SlickException
	{
		isAlive = true;
		
		init();
		
		loadImage("soldier.png");
	}
	
	public void move(Location loc)
	{
		
	}
	public void slide(Location loc)
	{
		
	}
	public void teleport(Location loc)
	{
		
	}
	public void attack(Soldier enemy)
	{
		
	}
	public void capture(King enemy)
	{
		
	}
	public void rescue(King king)
	{
		
	}
}
