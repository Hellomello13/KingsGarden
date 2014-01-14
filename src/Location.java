
public class Location {

	private int x; // x coordinate on grid
	
	private int y; // y coordinate
	
	public void setLocation(int xPos, int yPos) //set coordinates
	{
		if(checkValid(xPos, yPos))
		{
			x = xPos;
			y = yPos;
		}
	}
	
	public void setLocation(Location loc) //copy location
	{
		if(checkValid(loc.getX(), loc.getY()))
		{
			x = loc.getX();
			y = loc.getY();
		}
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	public int getCenterX()
	{
		return (x+3)*(800/10); //TODO +3 is grid displacement
	}
	public int getCenterY()
	{
		return (y+1)*(640/6); //+1 is grid displacement
	}
	
	/*public int getXpx()
	{
		return x; //to be pixel conversion of array location
	}
	public int getYpx()
	{
		return y; //to be pixel conversion of array location
	}*/
	
	public boolean checkValid(int x, int y) //TODO check, allow -1 for in deck
	{
		
		return true;
	}
	
	public boolean equals(int xTest, int yTest)
	{
		if(x == xTest && y == yTest)
		{
			return true;
		}
		return false;
	}
	public boolean equals(Location loc)
	{
		if(x == loc.getX() && y == loc.getY())
		{
			return true;
		}
		return false;
	}
	
	public boolean isAdjacent(Location loc)
	{
		if(Math.abs(x-loc.getX()) == 1 && y == loc.getY())
		{
			return true;
		}
		if(Math.abs(y-loc.getY()) == 1 && x == loc.getX())
		{
			return true;
		}
		return false;
	}
	
	public void print()
	{
		System.out.println(getX() + " , " + getY());
	}
}
