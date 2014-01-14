import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Field {

	Image background = null;
	
	FieldSpace[] grid;
	
	Deck fieldCards;
	
	int fieldSpaceWidth;
	int fieldSpaceHeight;
	
	int selectedCardIndex = -1;
	
	FieldSpace[] p1ShieldZone;
	Deck p1ShieldCards;
	FieldSpace[] p2ShieldZone;
	Deck p2ShieldCards;
	
	FieldSpace p1Palace;
	Card p1PalaceCard;
	FieldSpace p2Palace;
	Card p2PalaceCard;
	
	//TiledMap tileFloor;
	
	public void init() throws SlickException
	{
		loadField("data/mainbackground.png");
	}
	
	private void loadField(String file) throws SlickException //TODO load fieldspaces and init field cards
	{
		background = new Image(file);
		
		p1ShieldZone = new FieldSpace[3];
		p1ShieldCards = new Deck();
		p2ShieldZone = new FieldSpace[3];
		p2ShieldCards = new Deck();
		
		p1Palace = new FieldSpace();
		p1Palace.loadFieldSpace();
		p1Palace.setLocation(-2, 2); //manually set location
		p1PalaceCard = new Card();
		
		p2Palace = new FieldSpace();
		p2Palace.loadFieldSpace();
		p2Palace.setLocation(6, 2);
		p2PalaceCard = new Card();
		
		p1PalaceCard.setAttributes(13, "black", "spades");
		p1PalaceCard.flip();
		p1PalaceCard.setLocation(p1Palace.getLocation());
		
		p2PalaceCard.setAttributes(13, "red", "hearts");
		p2PalaceCard.flip();
		p2PalaceCard.setLocation(p2Palace.getLocation());
		
		grid = new FieldSpace[25];
		fieldCards = new Deck();
		
		for(int j = 0; j < 3; j++) //create shield zone spaces
		{
			p1ShieldZone[j] = new FieldSpace();
			p2ShieldZone[j] = new FieldSpace();
			
			p1ShieldZone[j].loadFieldSpace();
			p2ShieldZone[j].loadFieldSpace();
			
			p1ShieldZone[j].setLocation(-1, 1+j); //set locations manually
			p2ShieldZone[j].setLocation(5, 1+j);
		}
		
		for(int i = 0; i < 25; i++) //init field spaces and load image
		{
			//try {
				grid[i] = new FieldSpace(); //init new fieldspace object in array
				grid[i].loadFieldSpace();
				grid[i].setIndexLocation(i); //sets locations based on indexes
			//} catch (ArrayIndexOutOfBoundsException e) {
			//}
		}
		
		fieldSpaceWidth = grid[0].image.getWidth();
		fieldSpaceHeight = grid[0].image.getHeight();
	}
	
	public void update(Input input) //update card positions each turn?
	{
		selectedCardIndex = -1; //reset each update
		
		for(int i = 0; i < fieldCards.deck.size(); i++) //check mouse over for each card on field
		{
			if(fieldCards.deck.get(i).isMouseOver(input))
			{
				selectedCardIndex = i;
				//System.out.println("card: " + selectedCardIndex + " moused over.");
			}
		}
		
		for(int i = 0; i < p1ShieldCards.deck.size(); i++) //check mouse over for each shield space
		{
			if(p1ShieldCards.deck.get(i).isMouseOver(input))
			{
				selectedCardIndex = 25+i;
				//System.out.println("card: " + selectedCardIndex + " moused over.");
			}
		}
		for(int i = 0; i < p2ShieldCards.deck.size(); i++) //check mouse over for each shield space
		{
			if(p2ShieldCards.deck.get(i).isMouseOver(input))
			{
				selectedCardIndex = 28+i;
				//System.out.println("card: " + selectedCardIndex + " moused over.");
			}
		}
		
		if(p1PalaceCard.isMouseOver(input))
		{
			selectedCardIndex = 31;
			//System.out.println("card: " + selectedCardIndex + " moused over.");
		}
		if(p2PalaceCard.isMouseOver(input))
		{
			selectedCardIndex = 32;
			//System.out.println("card: " + selectedCardIndex + " moused over.");
		}
	}
	
	/*public void loadTiles(String file) throws SlickException
	{
		tileFloor = new TiledMap(file); //lvl1 test
		
		final int SIZE = 30; //TODO get tile size
		
		//floorLength = tileFloor.getWidth()*30; //sets floor length in tiles
	}*/
	
	public void draw() throws SlickException //draw field spaces
	{
		background.draw(0, 0);
		
		//grid[0].draw();//test draw
		p1Palace.draw(); //draw field space for palaces
		p2Palace.draw();
		p1PalaceCard.draw();
		p2PalaceCard.draw();
		
		for(int j = 0; j < p1ShieldZone.length; j++)//draw shield zone spaces
		{
			p1ShieldZone[j].draw();
			p2ShieldZone[j].draw();
		}
		for(int i = 0; i < 25; i++)
		{
			grid[i].draw();
		}
	}
}