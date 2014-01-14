import java.io.*;
import java.util.*;
import org.newdawn.slick.*;

public class Card {

	private int value;
	
	private String color;
	
	private String suit;
	
	private boolean isOnField;
	
	private boolean isHidden;
	
	private Location location;
	
	private int width;
	private int height;
	private int xDividerSize;
	private int yDividerSize;
	
	private int xOffset;
	private int yOffset;
	
	private int xPx;
	private int yPx;
	
	Image frontImage;
	Image backImage;
	
	Card(Card original) throws SlickException //copy original card if passed card info
	{
		location = new Location();
		
		setLocation(0, 0);
		
		copy(original);
		
		isOnField = false;
		
		isHidden = true;
		
		xDividerSize = 17;
		yDividerSize = 16;
	}
	
	Card() //default at location 0,0
	{
		location = new Location();
		
		location.setLocation(0, 0);
		
		value = 0;
		
		color = "null";
		
		suit = "null";
		
		isOnField = false;
		
		isHidden = true;
		
		xDividerSize = 17;
		yDividerSize = 26;
	}
	
	Card(Location initLoc)
	{
		location = new Location();
		
		location.setLocation(initLoc);
		
		value = 0;
		
		color = "null";
		
		suit = "null";
		
		isOnField = false;
		
		isHidden = true;
		
		xDividerSize = 17;
		yDividerSize = 26;
	}
	
	Card(int x, int y)
	{
		location = new Location();
		
		location.setLocation(x, y);

		value = 0;
		
		color = "null";
		
		suit = "null";
		
		isOnField = false;
	
		isHidden = true;
		
		xDividerSize = 17;
		yDividerSize = 26;
	}
	
	public void loadImage() throws SlickException
	{
		frontImage = new Image("data/cards/" + value + suit + ".png"); //filename must match format (ex. 2spades.png)
		backImage = new Image("data/cards/b1fv.png");
		System.out.println("image: " + value+suit+".png successfully loaded");
		
		width = backImage.getWidth();
		height = backImage.getHeight();
	}
	
	public void flip()
	{
		if(isHidden == true)
		{
			isHidden = false;
		}
		else
		{
			isHidden = true;
		}
	}
	public boolean getHidden()
	{
		return isHidden;
	}
	
	public int getXpx() //get pixel coordinate for card TODO update to fit window
	{
		/*xOffset = (800-(width*5+xDividerSize*4))/2; //adjusted (temporarily?)
		
		return (width+xDividerSize)*location.getX()+xOffset;*/
		
		xOffset = 0;
		
		return location.getCenterX()-width/2;
	}
	public int getYpx()
	{
		/*yOffset = (640-(height*5+yDividerSize*4))/2;
		
		return (height+yDividerSize)*location.getY()+yOffset;*/
		
		yOffset = 0;
		
		return location.getCenterY()-height/2;
	}
	
	public void draw() throws SlickException //takes location and renders on correct tile
	{
		//TODO adjust location to match field spaces
		
		if(!isHidden)
		{
			frontImage.draw(getXpx(), getYpx());
		}
		else
		{
			backImage.draw(getXpx(), getYpx());
		}
	}
	
	public void print()
	{
		System.out.println(getValue() +" - "+ getColor() +" - "+ getSuit());
	}
	
	public void setAttributes(int num, String text1, String text2) throws SlickException
	{
		setValue(num);
		setColor(text1);
		setSuit(text2);
		loadImage();
	}
	
	public void setValue(int num)
	{
		value = num;
	}
	public int getValue()
	{
		return value;
	}
	
	public void setColor(String text)
	{
		color = text;
	}
	public String getColor()
	{
		return color;
	}
	
	public void setSuit(String text)
	{
		suit = text;
	}
	public String getSuit()
	{
		return suit;
	}
	
	public void copy(Card original) throws SlickException //copy other card attributes
	{
		setAttributes(original.getValue(), original.getColor(), original.getSuit());
		loadImage();
	}
	
	public boolean getIsOnField()
	{
		return isOnField;
	}
	public void moveToField(Location loc) //put on field and give fieldspace location
	{
		isOnField = true;
		setLocation(loc);
	}
	public void moveToField() //put on field and give null fieldspace location
	{
		isOnField = true;
		setLocation(-1, -1);
	}
	public void moveFromField()
	{
		isOnField = false;
	}
	
	public void setLocation(Location loc)
	{
		location.setLocation(loc);
	}
	public void setLocation(int x, int y)
	{
		location.setLocation(x, y);
	}
	public Location getLocation()
	{
		return location;
	}
	
	public boolean hasLocation(int x, int y)
	{
		if(location.equals(x, y))
			return true;
		return false;
	}
	public boolean hasLocation(Location loc)
	{
		if(location.equals(loc))
			return true;
		return false;
	}
	
	public boolean isMouseOver(Input input) //checks if mouse location is inside of object's image
	{
		if(input.getMouseX() >= getXpx() && input.getMouseX() <= getXpx()+width)
		{
			if(input.getMouseY() >= getYpx() && input.getMouseY() <= getYpx()+height)
			{
				//System.out.println("mouse over card " + location.getX() + " , " + location.getY());
				return true;
			}
		}
		return false;
	}
}
