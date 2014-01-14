import org.newdawn.slick.*;


public class FieldSpace { //square blocks that make up field
	
	Image image = null;
	
	private int index;
	
	private int width;
	private int height;
	
	private int dividerSize;
	
	private int xOffset;
	private int yOffset;
	
	Location location;
	
	public void loadFieldSpace() throws SlickException
	{
		image = new Image("data/fieldspace.png");
		
		width = image.getWidth();
		height = image.getHeight();
		dividerSize = 10;
		
		location = new Location();
	}
	
	public void setIndexLocation(int i) //set index bases on input and set location based on index
	//TODO optimize
	{
		index = i;
		int x = 0;
		int y = 0;
		
		if(index % 5 == 0)
		{
			x = 0;
		}
		else if((index-1) % 5 == 0)
		{
			x = 1;
		}
		else if((index-2) % 5 == 0)
		{
			x = 2;
		}
		else if((index-3) % 5 == 0)
		{
			x = 3;
		}
		else if((index-4) % 5 == 0)
		{
			x = 4;
		}
		
		if(index >= 0 && index <= 4)
		{
			y = 0;
		}
		else if(index >= 5 && index <= 9)
		{
			y = 1;
		}
		else if(index >= 10 && index <= 14)
		{
			y = 2;
		}
		else if(index >= 15 && index <= 19)
		{
			y = 3;
		}
		else if(index >= 20 && index <= 24)
		{
			y = 4;
		}
		
		location.setLocation(x, y); //set based on array placement
	}
	
	public int getXpx() //TODO update to fit window
	{
		/*xOffset = (800-(width*5+dividerSize*4))/2;
		
		return (width+dividerSize)*location.getX();*/
		
		xOffset = 0;
		
		return location.getCenterX()-width/2;
	}
	public int getYpx()
	{
		/*yOffset = (640-(height*5+dividerSize*4))/2;
		
		return (height+dividerSize)*location.getY();*/
		
		yOffset = 0;
		
		return location.getCenterY()-height/2;
	}
	
	public void reveal(Card card) //TODO change isHidden status of card (with same location to false)
	{
		if(card.getHidden())
		{
			card.flip();
		}
	}
	
	public void draw() throws SlickException
	{
		image.draw(getXpx()+xOffset, getYpx()+yOffset);
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
}
