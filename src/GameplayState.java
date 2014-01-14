import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

public class GameplayState extends BasicGameState {

	Field field;
	
	Deck generalDeck;
	Deck royalsDeck;
	Deck burnDeck;
	
	Player p1, p2;
	int playerTurn; //whose turn it is currently
	
	boolean mouseClicked = false; //check mouse click for full turn
	
	String phase = "null";
	
	//TODO make following variables local, not global
	int step = 0;
	String activeObject = "null"; //can either be "soldier" or "special card"
	//int selectedPiece = -1;
	
	//*****NOTE: change all "30" to variable = tile width
	
	/*PlatformLevel lvl1 = null;
	Character player1 = null;
	Camera cam = null;
	int camLine;
	
	//TiledMap map;*/
	
	int stateID = -1;
	 
    GameplayState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
    	field = new Field();
    	p1 = new Player(1);
    	p2 = new Player(2);
    	
    	playerTurn = 1; //start with player one going first
    	phase = "primary";
    	
    	startGame(); //last to run in init method
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
    	Input input = gc.getInput();
    	//gc.setMaximumLogicUpdateInterval(17); //consistent logic rate
		
    	//input.getMouseX();
    	
    	if(input.isMousePressed(0)) //check based on phase and location of click
    	{
    		mouseClicked = true;
    	}
    	
    	/*if(mouseClicked)
    	{
    		fieldClick(); //test if hidden card is flipped, reveal card
    	}*/
    	
    	if(playerTurn == 1)
    	{
    		if(mouseClicked)
    		{
    			if(phase.equals("primary"))
    				primaryPhase(p1, input);
    			else if(phase.equals("soldierAction")) //if soldier selected, change phase
    				soldierAction(p1, input);
    		}
    	}
    	else if(playerTurn == 2 && mouseClicked)
    	{
    		if(phase.equals("primary"))
    			primaryPhase(p2, input);
    	}
    	p1.update(input);
    	p2.update(input);
    	
    	field.update(input); //do after turn actions?
    	
    	mouseClicked = false; //reset after each update
    }
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	field.draw();
    	
    	drawCardField();
    	
    	p1.draw();
    	p2.draw();
    	
    	//generalDeck.deck.get(0).draw(); //test draw
    	
    	/*lvl1.drawLevel(cam.xShift); //draws Background
    	//map.render(0, 0, 0, 0, 800, 600); //test render of tilemap
    	player1.draw(cam.xShift);
    	
    	g.drawString("Life: " + player1.getLife(), 725, 10); //life counter in upper right*/
    }
    
    public void primaryPhase(Player player, Input input) throws SlickException
    {
    	//boolean primaryPhaseDone = false;
    	
    	/*while(!primaryPhaseDone)
    	{*/
    		if(player.selectPiece(input) > 0
    				&& player.selectPiece(input) <= 3) //not null/king/invalid
    		{
				System.out.println("soldier " + player.selectedPieceIndex + " successfully selected.");
				
				phase = "soldierAction";
				
				//primaryPhaseDone = true;
    		}
    		else
    		{
   				player.selectedPieceIndex = -1; //reset selected piece to null
    			
    			//primaryPhaseDone = true;
    		}
    			
    		if(player.isMouseOverExtraDeck(input))
    		{
    			Card specialCard = attemptSpecialDraw(player);
    			
    			if(specialCard != null) //if draw is successful
    			{
    				specialCard.print();
    				
    				//TODO draw on top of extra deck until special draw phase is over
    				
    				phase = "specialDraw";
    			}
    			//primaryPhaseDone = true;
    		}
    	//}
    }
    
    public void soldierAction(Player player, Input input)
    {
    	if(mouseClicked) //check for movement, attack, teleport
		{
			if(tryMoveSoldier(player, input));
			{
				
				
				phase = "primary"; //TEMPORARY start over
				
				//TODO check for slide
			}
			if(tryAttack(player, input))
			{
				
				phase = "primary";
			}
		}
    }
    
    public boolean tryMoveSoldier(Player player, Input input) //attempts to move soldier to selected space
    {
    	//field.update(input); //check for new mouse location on field
    	
    	if(field.selectedCardIndex != -1) //check for valid location selection
		{
			//check if selected destination is adjacent to selected soldier
			if(field.fieldCards.deck.get(field.selectedCardIndex).getLocation().isAdjacent(player.pieces.get(player.selectedPieceIndex).getLocation()))
			{
				System.out.println("***valid destination selected***");
				//copy new location as selected soldier's new location
				player.pieces.get(player.selectedPieceIndex).setLocation(field.fieldCards.deck.get(field.selectedCardIndex).getLocation());
				
				//fieldClick();//reveal new location card TODO rename fieldClick method?
				
				revealSoldierCards(player, player.selectedPieceIndex);
				
				return true;
			}
			else
			{
				System.out.println("***cannot move to selection***");
				return false;
			}
		}
    	
    	return false; //if unsuccessful
    }
    
    public boolean tryAttack(Player player, Input input)
    {
    	//TODO
    	
    	return false;
    }
    
    public Card attemptSpecialDraw(Player player) //check if special draw conditions are met, if they are then return drawn card
	{
    	String suit;
    	int netSuitCount = 0;
    	
    	if(player.id == 1)
    	{
    		suit = "spades";
    	}
    	else if(player.id == 2)
    	{
    		suit = "hearts";
    	}
    	else
    		suit = null;
    	
		for (int i = 1; i < player.pieces.size(); i++) {
				if (cardOnPieceLocation(player.pieces.get(i)).getSuit().equals(suit))
			{
				netSuitCount++;
			}
			else
				netSuitCount--;
		}
		
		if(netSuitCount > 0) //then can draw TODO delete top card from extra and move to burn
		{
			System.out.println("special draw attempt successful.");
			
			return player.topOfExtraDeck(); //return top card of extra deck
		}
		
		System.out.println("special draw attempt failed.");
		return null;
	}
    
    public Card cardOnPieceLocation(PlayerPiece piece)
    {
    	for(int i = 0; i < field.grid.length; i++)
		{
			if(piece.getLocation().equals(field.grid[i].getLocation()))
			{
				return field.fieldCards.deck.get(i);
			}
		}
    	return null;
    }
    
    public void drawCardField() throws SlickException
    {
    	try {
			for(int i = 0; i < 25; i++)
			{
				field.fieldCards.deck.get(i).draw();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
    }
    
    public void startGame() throws SlickException
    {
    	field.init();
    	
    	makeDeck(52); //init main deck and shuffle
    	
    	separateTwoKings();
    	separateRoyals(); //put all royals except kings in separate pile
    	
    	setField(); //give cards field locations
    	field.fieldCards.print(); //check print
		System.out.println("Should be 25: " + field.fieldCards.deck.size());
		
    	replaceRoyals(); //adds royals back into general deck and shuffles
    	
    	makeExtraDeck(p1, 12); //put half of remaining cards into p1 deck
    	makeExtraDeck(p2, 12); //put remaining cards into p2 deck
    	
    	burnFromDeck(1); //burn last card in deck
    	
    	generalDeck.print();
    	
    	for(int l = 1; l <= 3; l++) //flip all starting location cards
    	{
    		revealSoldierCards(p1, l);
    		revealSoldierCards(p2, l);
    	}
    }
    
    public void revealSoldierCards(Player player, int index) //reveal card on which soldier is located
    {
    	if(cardOnPieceLocation(player.pieces.get(index)).getHidden())
    		cardOnPieceLocation(player.pieces.get(index)).flip();
    }
    
    public void makeDeck(int size) throws SlickException //init general deck and burn deck
    {
    	generalDeck = new Deck();
    	
    	generalDeck.create(52);
    	
    	generalDeck.shuffle();
    	
    	royalsDeck = new Deck(); //create separate royals deck for later
    	
    	burnDeck = new Deck(); //create burn deck for later
    }
    
    public void setField() throws SlickException //give top cards in deck field locations
    {
    	moveCardsToField(25);
    	
    	shieldZoneCopy();
    	
    	//create palace cards
    	
    	/*try {
			for(int i = 0; i < 25; i++)
			{
				moveCardToField(i); //draw 25 cards from deck and move to field
				
				//generalDeck.deck.get(i).moveToField(field.grid[i].location);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}*/
    }
    
    public void moveCardsToField(int amount) throws SlickException //moves card from top of general deck to end of field
    {
    	if(amount <= generalDeck.deck.size()){
    		field.fieldCards.copy(generalDeck, amount);
			
    		for(int i = 0; i < amount; i++)
			{
				field.fieldCards.deck.get(i).moveToField(field.grid[i].getLocation()); //set location on grid
				
				generalDeck.deck.remove(0); //remove from general deck
			}
		} 
    }
    
    public void moveCardToField(int index) throws SlickException //remove card from generalDeck and add to field
    {
    	field.fieldCards.deck.add(new Card(generalDeck.deck.get(index))); //copy card from deck to field
    	
    	generalDeck.deck.remove(index); //delete original card from deck
    	
    	for(int j = 0; j < field.fieldCards.deck.size(); j++) //correct status of all cards on field and give location
    	{
    		field.fieldCards.deck.get(j).moveToField(field.grid[j].getLocation());
    	}
    }
    
    public void shieldZoneCopy() throws SlickException //copy attributes from cards in front of shield zone to shield zone
    {
    	for(int i = 0; i < 3; i++) //check field for card in front of each shield card
    	{
    		for(int j = 0; j < field.fieldCards.deck.size(); j++)
    		{
    			if(field.fieldCards.deck.get(j).hasLocation(0, 1+i)) //p1 shield check
    			{
    				field.p1ShieldCards.deck.add(new Card(field.fieldCards.deck.get(j)));
    				field.p1ShieldCards.deck.get(i).moveToField(field.p1ShieldZone[i].getLocation()); //set location
    			}
    			else if(field.fieldCards.deck.get(j).hasLocation(4, 1+i)) //p2 shield check
    			{
    				field.p2ShieldCards.deck.add(new Card(field.fieldCards.deck.get(j)));
    				field.p2ShieldCards.deck.get(i).moveToField(field.p2ShieldZone[i].getLocation());
    			}
    		}
    	}
    	
    	System.out.println("____________");
    	field.p1ShieldCards.print();
    	field.p2ShieldCards.print();
    }
    
    public void separateTwoKings() //separates two kings from general deck
    {
    	for(int i = 0; i < generalDeck.deck.size(); i++)
    	{
    		if(generalDeck.deck.get(i).getValue() == 13)
    		{
    			if(generalDeck.deck.get(i).getSuit().equals("spades")
    					|| generalDeck.deck.get(i).getSuit().equals("hearts"))
    			{
    				generalDeck.deck.remove(i);
    				i--;
    			}
    		}
    	}
    }
    
    public void separateRoyals() throws SlickException //puts all royals except kings into separate deck 
    {
    	for(int i = 0; i < generalDeck.deck.size(); i++)
    	{
    		if(generalDeck.deck.get(i).getValue() > 10)
    		{
    			if(generalDeck.deck.get(i).getValue() != 13) //filter out kings
    			{
    				royalsDeck.deck.add(new Card(generalDeck.deck.get(i))); //copy into royals pile
    				
    				generalDeck.deck.remove(i); //remove from general deck
    				i--; //follow index shift from remove method
    			}
    		}
    	}
    }
    
    public void replaceRoyals() throws SlickException //puts royals pile back into general deck and reshuffles
    {
    	generalDeck.copy(royalsDeck, royalsDeck.deck.size()); //copy all royals back into general deck
    	
    	royalsDeck.deck.clear();//delete all of royals pile
    	
    	generalDeck.shuffle();
    	
    	generalDeck.print();
    }
    
    public void makeExtraDeck(Player player, int num) throws SlickException //draws extra deck cards from top of deck
	{
    	//System.out.println(generalDeck.deck.size());
		for(int index = 0; index < num; index++)
		{
			player.addToExtraDeck(generalDeck.deck.get(0)); //add from top of deck
			
			generalDeck.deck.remove(0); //remove top card on deck
			//System.out.println(generalDeck.deck.size());
		}
		
		player.initExtraDeck(); //set isOnField true and give null location
	}
    
    public void fieldClick() //test method
    {
    		if(field.selectedCardIndex < 25 && field.selectedCardIndex >= 0)
    		{
    			field.grid[field.selectedCardIndex].reveal(field.fieldCards.deck.get(field.selectedCardIndex));
    		}
    		//System.out.println("-----mouse pressed-----");
    }
    
    public void burnFromDeck(int num)
    {
    	for(int i = 0; i < num; i++)
    	{
    		generalDeck.deck.remove(0); //remove from top of deck
    	}
    }
}