package catan.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import catan.network.Messenger.NumberOf;
import catan.network.UpdateMessage.UpdateType;
import database.Board;
import database.Node;
import database.Tile;

public class CatanNetwork {

	private String nickname;
	private LinkedList<String> addresses = new LinkedList<String>();									// nick i ip z hamachi
	private CatanCommunication game;
	
	public List<ObserverPeers> peersObservers = new LinkedList<ObserverPeers>();
	public List<ObserverInv> invObservers = new LinkedList<ObserverInv>();
		
	public CatanNetwork(String nickname)
	{
		this.nickname = nickname;
	}
	
		
	public void 	readAddresses()
	{
		// TODO 
		addresses.add("127.0.0.1");
	}
	public void 	initNetwork() throws IOException
	{
		Communication com = Communication.getInstance();									// tworzenie komunikacji						
		peersObservers.add(new ObserverPeers(com));			
				
		MessageHandler mh = new CatanMessageHandler();				
		game = new CatanCommunication(com, nickname, addresses, mh);
		invObservers.add(new ObserverInv(game));
		
	}
	public void 	invite(List<String> invited)
	{
		System.out.println("");
		System.out.println("--Sending invitations to: " + invited);							// wyswalenie zaproszen
		System.out.println("");
		game.sendInvitations(invited);
	
	}	
	
	public boolean 	start()
	{
		boolean gameState = game.startGame(1,4);														// start gry
		if(gameState == true)																	// jak sie nie uda to nie usuwa liste zaproszonych
		{
			System.out.println("Welcome in Catan world!");
			game.setOrder();
		}			
		else
			System.out.println("Starting game failed!");
		return gameState;
		
	}
	public void		endTurn()
	{
		game.sendEnd(UpdateType.END_TURN);
	}
	public void		endGame()
	{
		game.sendEnd(UpdateType.END_GAME);
	}
	
	public void 	updateBoard(Board board)
	{
		game.sendUpdate(board);
	}
	public void 	updateTile(Tile tile, int index)
	{
		game.sendUpdate(tile, index);
	}
	public void 	updateNode(Node node, int index)
	{
		game.sendUpdate(node, index);
	}
	public void 	updateDice(int number)
	{
		game.sendUpdate(NumberOf.DICE, number);
	}
	public void		updateCardsNumber(int number)
	{
		game.sendUpdate(NumberOf.RESOURCES, number);
	}
	
	public void		tradeOffert(HashMap<String, Integer> give, HashMap<String, Integer> get)
	{
		game.sendTrade(give, get);
	}
	public void		deal(String nick)
	{
		game.sendTrade(nick);
	}
	public void		cancelTrade()
	{
		game.sendTrade();
	}
	
	public static void main(String[] args) throws IOException 
	{
		CatanNetwork game = new CatanNetwork("Sebastian");
		game.readAddresses();
		game.initNetwork();
		
		Communication.sleep(5000);
		
		LinkedList<String> players = new LinkedList();
		players.add("Sebastian");
		game.invite(players);
		
		Communication.sleep(5000);
		/*
		game.start();
		
		Communication.sleep(5000);
		*/
		
	}

}
