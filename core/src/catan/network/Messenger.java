package catan.network;

import java.util.HashMap;

import database.Board;
import database.Node;
import database.Tile;

/**
 * Interface which provides all needed methods during a game.
 * @author Sebastian
 *
 */
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
	
	public void sendTrade(HashMap<String, Integer> give, HashMap<String, Integer> get);
	public void sendTrade(String nick);	// to deal
	public void sendTrade();	// to abbandon

	public void sendEnd(SystemMessage.SystemType type);

}
