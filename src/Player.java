import java.io.*;
import java.util.*;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {

	int id;
	
	ArrayList<PlayerPiece> pieces;
	
	private Deck extraDeck;
	
	Image extraDeckImage;
	int extraDeckWidth;
	int extraDeckHeight;
	int extraDeckX;
	int extraDeckY;
	boolean extraDeckSelected;
	
	//player turn variables:
	int selectedPieceIndex;
	int step;
	
	Player(int number) throws SlickException
	{
		id = number;
		
		initPlayer(); //initialize upon player object being created
	}
	
	public void initPlayer() throws SlickException //initializes pieces and extra deck
	{
		pieces = new ArrayList<PlayerPiece>();
		
		pieces.add(new King());
		if(id == 1) //set piece locations
		{
			pieces.get(0).setLocation(-2, 2);
		}
		else if(id == 2)
		{
			pieces.get(0).setLocation(6, 2);
		}
		
		for(int i = 1; i <= 3; i++)
		{
			pieces.add(new Soldier());
			
			if(id == 1) //set piece locations
			{
				pieces.get(i).setLocation(0, i);
			}
			else if(id == 2)
			{
				pieces.get(i).setLocation(4, i);
			}
		}
		
		//TODO also init piece locations based on player id
		
		extraDeck = new Deck(); //create empty extra deck for player
		
		extraDeckImage = new Image("data/cards/sidedeck.png");
		extraDeckWidth = extraDeckImage.getWidth();
		extraDeckHeight = extraDeckImage.getHeight();
		extraDeckSelected = false;
	}
	
	public void addToExtraDeck(Card card) throws SlickException
	{
		extraDeck.deck.add(new Card(card));
	}
	
	public void initExtraDeck() //move all cards in extraDeck to field with location (-1, -1)
	{
		for(int i = 0; i < extraDeck.deck.size(); i++)
		{
			extraDeck.deck.get(i).moveToField();
		}
		
		if(id == 1) //set image location based on player id
		{
			extraDeckX = 30; //arbitrary for now
			extraDeckY = 30;
		}
		else if(id == 2) //flips image for p2
		{
			extraDeckX = 800-30-extraDeckWidth;
			extraDeckY = 640-30-extraDeckHeight;
			
			extraDeckImage.rotate(180);
		}
	}
	
	public void update(Input input)
	{
		isMouseOverExtraDeck(input);
	}
	
	public void draw() //draws player's extra deck, soldiers, and king onto field
	{
		for(int j = 0; j < pieces.size(); j++)
		{
			pieces.get(j).draw();
		}
		
		//TODO add soldier and king draw methods here
		extraDeckImage.draw(extraDeckX, extraDeckY);
	}
	
	public int selectPiece(Input input)
	{
		for(int i = 0; i < pieces.size(); i++)
		{
			if(pieces.get(i).isMouseOver(input))
			{
				selectedPieceIndex = i; //fix to update correctly (can run in update)
				return i;
			}
		}
		return -1;
	}
	
	public Card topOfExtraDeck()
	{
		return extraDeck.deck.get(0);
	}
	
	public boolean isMouseOverExtraDeck(Input input)
	{
		if(input.getMouseX() >= extraDeckX && input.getMouseX() <= extraDeckX+extraDeckWidth)
		{
			if(input.getMouseY() >= extraDeckY && input.getMouseY() <= extraDeckY+extraDeckHeight)
			{
				//System.out.println("mouse over p" + id + " extra deck");
				extraDeckSelected = true;
				
				return true;
			}
		}
		
		extraDeckSelected = false;
		return false;
	}
}
