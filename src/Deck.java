import java.io.*;
import java.util.*;

import org.newdawn.slick.SlickException;

public class Deck {

	int size;
	ArrayList<Card> deck;
	
	Deck() //default
	{
		size = 52;
		
		deck = new ArrayList<Card>();
	}
	
	Deck(int quantity)
	{
		size = quantity;
		
		deck = new ArrayList<Card>();
	}
	
	public void addCardOrdered(int index, int value, String color, String suit) throws SlickException
	{
		try{
			deck.get(index).setAttributes(value, color, suit);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	public void create(int size) throws SlickException
	{
		for(int i = 0; i < size; i++)
		{
			deck.add(new Card());
		}
		
		for(int i = 0; i < 13; i++) //create each suit of cards (13 each)
		{
			addCardOrdered(i, i+2, "black", "spades");
		}
		
		for(int i = 13; i < 26; i++)
		{
			addCardOrdered(i, i-11, "red", "hearts");
		}
		
		for(int i = 26; i < 39; i++)
		{
			addCardOrdered(i, i-24, "black", "clubs");
		}
		
		for(int i = 39; i < 52; i++)
		{
			addCardOrdered(i, i-37, "red", "diamonds");
		}
		
		print();
	}
	
	public void copy(Deck original, int quantity) throws SlickException
	{
		size = quantity;
		
		for(int i = 0; i < quantity; i++)
		{
			deck.add(new Card(original.deck.get(i)));
		}
	}
	
	public void shuffle() //randomly shuffles deck order
	{
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
		
		print();
	}
	
	public void moveCardToField()
	{
		
	}
	
	public void print()
	{
		try {
			for(int count = 0; count < deck.size(); count++)
			{
				deck.get(count).print();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
		for(int k = 0; k <= 10; k++)
		{
			System.out.print("- ");
		}
		System.out.println("\n");
	}
}
