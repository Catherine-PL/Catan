package catan.network;

import database.Board;
import database.Node;
import database.Tile;

public interface Messenger 
{
	public enum NumberOf
	{
		RESOURCES, DICE;
	}
	
	// wyslanie do wszystkich graczy
	public void sendUpdate(Board board);				// dla startu gry
	public void sendUpdate(Tile tile, int index);		// dla zlodzieja
	public void sendUpdate(Node node, int index);		// dla wybudowania osady, miasta, moze drogi
	public void sendUpdate(NumberOf what, int quantity);	// kazdy musi wiedziec jaka mam liczbe surowcow razem, wynik kosci	
	

}
