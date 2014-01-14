import org.newdawn.slick.*;


public class PlayerPiece {

	public boolean isAlive;
	
	public int player; //p1 or p2, etc...
	
	int index;
	
	Image image;
	int width;
	int height;
	
	private Location location;
	
	public void init()
	{
		location = new Location();
	}
	
	public void loadImage(String fileName) throws SlickException
	{
		image = new Image("data/" + fileName);
		
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public int getXpx()
	{
		//return (400+(600/7)*(location.getX()-2)); //test draw
		return location.getCenterX()-(width/2);
	}
	public int getYpx()
	{
		//return (100)*(location.getY()+1);
		return location.getCenterY()-(height/2);
	}
	
	public void draw()
	{
		image.draw(getXpx(), getYpx()); //TODO fix locations
	}
	
	public void setLocation(Location initLoc)
	{
		location.setLocation(initLoc);
	}
	public void setLocation(int x, int y)
	{
		location.setLocation(x, y);
	}
	public Location getLocation()
	{
		return location;
	}
	
	public boolean isMouseOver(Input input) //checks if mouse location is inside of object's image
	{
		if(input.getMouseX() >= getXpx() && input.getMouseX() <= getXpx()+width)
		{
			if(input.getMouseY() >= getYpx() && input.getMouseY() <= getYpx()+height)
			{
				System.out.println("mouse over card " + location.getX() + " , " + location.getY());
				return true;
			}
		}
		return false;
	}
}
