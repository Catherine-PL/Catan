package catan.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import catan.network.Messenger.NumberOf;
import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;
import database.Board;
import database.Node;
import database.Tile;

public class CatanCommunication extends GameCommunication implements Messenger {

	public CatanCommunication(P2P decoratedP2P) {
		super(decoratedP2P);
	}
	

	/**
	 * After starting a game, all players send dice result.
	 * If we have all results, we know  sequence of players. 
	 * @return number is positive or equal 0.
	 * Number says which is our place, but when number is equal 0 that means,
	 * not everyone has sent me a result and it was impossible to create sequence. 
	 */
	
	public int getPlace()																// zwraca 0 jezeli jeszcze nie ustalono kolejnosci
	{
		Set<String> s = invPlayers.keySet();
		int i = 0;
		for(String nick : s)
		{
			if(invPlayers.get(nick) == InvStatus.WAIT)
				i++;
		}
		if(invPlayers.size() == i)								// wszyscy WAIT -> znam kolejnosc
			return (msgHandler.inQueue + 1);
		else
			return 0;
	}
	/**
	 * It should be a first message for a host of this game.
	 * This method propagate our board to all players.
	 * @param board Board to propagate.
	 */
	public void sendUpdate(Board board)
	{	
		Message msg = null;
		try {
			msg = this.update.getUpdateMessage(UpdateType.BOARD, board);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);				
	}	
	/**
	 * Method useful to update a tile. It will be necessary in case of thief moving.  
	 * @param tile Tile object which has been modified. 
	 * @param index Index of this tile in our board.
	 */
	public void sendUpdate(Tile tile, int index) 
	{
		Message msg = null;
		try {			
			msg = this.update.getUpdateMessage(UpdateType.TILE, tile, index);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);		
	}	
	/**
	 * Sends update message to all players. Needed whenever some node of our graph has been changed. 
	 * Useful after building new city, road etc. 
	 * @param node Node object which has been modified.
	 * @param index Index of this node in our board.
	 */
	public void sendUpdate(Node node, int index) 
	{
		Message msg = null;
		try {			
			msg = this.update.getUpdateMessage(UpdateType.NODE, node, index);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);	
	}
	/**
	 * Everyone has to know how many resource card i have. (Thief) 
	 * @param what It might be DICE or RESOURCE
	 * @param quantity Number of what parameter.
	 */
	public void sendUpdate(NumberOf what, int quantity)			// DICE, RESOURCE 
	{
		Message msg = null;
		try {			
			if(what.toString().equals(UpdateType.DICE.toString()))
				msg = this.update.getUpdateMessage(UpdateType.DICE, quantity);
			else
				msg = this.update.getUpdateMessage(UpdateType.RESOURCES, quantity);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);	
	}
	
	/**
	 * Sends to all players my offert.
	 * @param give HashMap which contains names of resources and their quantity. This resources we are giving to somebody.
	 * @param give HashMap which contains names of resources and their quantity. This resources we are getting from somebody.
	 */
	public void sendTrade(HashMap<String, Integer> give, HashMap<String, Integer> get) 
	{
		for(String name : invPlayers.keySet())
		{
			invPlayers.remove(name);
			invPlayers.put(name, InvStatus.WAIT);
		}		
		System.out.println("Players: " + invPlayers);													
		
		
		Message msg = null;
		msg = this.trade.getTradeMessage(TradeType.OFFERT, give, get);			
			
		
		Set<Entry<String, InvStatus>> entrySet = invPlayers.entrySet();
		Iterator<Entry<String, InvStatus>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String, InvStatus> e = it.next();		
			try
			{
				if(e.getValue() != InvStatus.REJECTED)
					sendTo(e.getKey(), msg);
			}
			catch (IOException ex) 
			{
				System.err.println("Utracono polaczenie z: " + e.getKey());
				ex.printStackTrace();
				disconnected(e.getKey());
				invPlayers.remove(e.getKey());
			}
			
		}
		
		
	}		
	/**
	 * Whenever a player accepts our offer, then we can make this transaction true by sending deal message.
	 * @param nick Player's nickname who has accepted our offer. 
	 */
	public void sendTrade(String nick) 
	{
		if(invPlayers.get(nick) != InvStatus.ACCEPTED)
		{
			System.out.println("Player: " + nick + " hasn't accepted your offert.");
			return;
		}
		Message ms = trade.getTradeMessage(TradeType.DEAL);
		
		try {
			sendTo(nick, ms);
		} catch (IOException e) {
			System.err.println("Utracono polaczenie z: " + nick);			
			disconnected(nick);
			invPlayers.remove(nick);
			e.printStackTrace();
		}
		
		Set<String> s = invPlayers.keySet();
		s.remove(nick);
		invPlayers.put(nick, InvStatus.WAIT);
		
		ms = trade.getTradeMessage(TradeType.END_TRADE);
		for(String name : s)
		{
			try {
				sendTo(name, ms);
				invPlayers.remove(name);
				invPlayers.put(name, InvStatus.WAIT);
			} catch (IOException e) {
				System.err.println("Utracono polaczenie z: " + name);			
				disconnected(name);
				invPlayers.remove(name);
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Closes trade.
	 */
	public void sendTrade()
	{
		System.out.println("--Closing trade--");
		Set<String> s = invPlayers.keySet();		
		Message ms = trade.getTradeMessage(TradeType.END_TRADE);
		for(String name : s)
		{
			try {
				sendTo(name, ms);
				invPlayers.remove(name);
				invPlayers.put(name, InvStatus.WAIT);
			} catch (IOException e) {
				System.err.println("Utracono polaczenie z: " + name);			
				disconnected(name);
				invPlayers.remove(name);
				e.printStackTrace();
			}
		}		
	}
	/**
	 * Sends END_GAME or END_TURN message to all players.
	 * @param type Type of our ending. 
	 */
	public void sendEnd(SystemType type) 
	{
		Message ms = null;
		if(type == SystemType.END_GAME)
			try {
				ms = system.getSystemMessage(SystemType.END_GAME, null);				
			} catch (ContentException e) {
				e.printStackTrace();
			}
		else if(type == SystemType.END_TURN)
			try {
				ms = system.getSystemMessage(SystemType.END_TURN, null);
			} catch (ContentException e) {
				e.printStackTrace();
			}
		else
			System.err.println("Wrong type of end message");
		
		sendToAll(ms);
		
	}
	

}
