package catan.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import catan.network.Messenger.NumberOf;
import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;
import database.Board;
import database.Game;
import database.Node;
import database.Tile;

public class CatanNetwork {

	private String nickname;
	private LinkedList<String> addresses = new LinkedList<String>();									// nick i ip z hamachi
	private CatanCommunication game;
	
	public List<ObserverPeers> peersObservers = new LinkedList<ObserverPeers>();						// They observe Communication
	public List<Observer> invObservers = new LinkedList<Observer>();								// They observe GameCommunication
		
	public CatanNetwork(String nickname)
	{
		this.nickname = nickname;
	}
	

	public void addAddress(String address) 
	{		
		game.addNodeP2P(address);
	}

	
	public void 	readAddresses()
	{
		// TODO read addresses from a file, and saving to a file?
		//addresses.add("127.0.0.1");
		addresses.add("25.111.151.148");
		//addresses.add("25.50.105.234");
		
	}
	public void 	initNetwork(Game game) throws IOException
	{
		Communication com = Communication.getInstance();									// tworzenie komunikacji						
		peersObservers.add(new ObserverPeers(com));			
				
		MessageHandler mh = new CatanMessageHandler(game);				
		this.game = new CatanCommunication(com, nickname, addresses, mh);
		invObservers.add(new ObserverInv(this.game));
		invObservers.add(new ObserverStart(this.game));
		invObservers.add(new ObserverTrade(this.game));		
		
	}
	public void 	invite(List<String> invited)
	{
		System.out.println("");
		System.out.println("--Sending invitations to: " + invited);							// wyswalenie zaproszen
		System.out.println("");
		game.sendInvitations(invited);
	
	}	
	public void 	sendInvitationAnswer(String nick, SystemType answer)
	{
		this.game.sendInvitationAnswer(nick, answer);
	}
	
	public boolean 	start()
	{
		boolean gameState = game.startGame(1,4);														// start gry
		if(gameState == true)																	// jak sie nie uda to nie usuwa liste zaproszonych
		{
			System.out.println();
			System.out.println(" in Catan Welcomeworld!");
			System.out.println();
			game.setOrder();
		}			
		else
			System.out.println("Starting game failed!");
		return gameState;
		
	}
	public void		abandon()
	{
		this.game.abandonGame();
	}	
	public void		endTurn()
	{
		game.sendEnd(UpdateType.END_TURN);
	}
	public void		endGame()
	{
		game.sendEnd(UpdateType.END_GAME);
	}
	
	public List<String>	getQueue()
	{
		return game.queue;
	}
	
	public void 	updateBoard(Board board)
	{
		game.sendUpdate(board);
	}
	public void 	updateTile(Tile tile, int index)
	{
		game.sendUpdate(tile, index);
	}
	public void 	updateNode(Boolean city,int index)
	{
		game.sendUpdate(city, index);
	}
	public void		updateRoad(Integer idNode, int idRoad)
	{
		game.sendUpdate(idNode, idRoad);
	}
	public void 	updateDice(int number)
	{
		game.sendUpdate(NumberOf.DICE, number);
	}
	public void		updateResources(int quantity)
	{
		game.sendUpdate(NumberOf.RESOURCES, quantity);
	}
	public void		updateCardsNumber(int number)
	{
		game.sendUpdate(NumberOf.RESOURCES, number);
	}
	public void		updateGiveThiefLoot(String nick, String resource)
	{
		game.sendUpdate(nick, resource);
	}
	public void		updateThiefSteal(String nick)
	{
		game.sendUpdate(nick);
	}
	
	public void		tradeOffert(HashMap<String, Integer> give, HashMap<String, Integer> get)
	{
		game.sendTrade(give, get);
	}
	public void		tradeAnswer(String nick,TradeType answer)
	{
		this.game.sendTradeAnswer(nick, answer);
	}
	public void		tradeDeal(String nick)
	{
		game.sendTrade(nick);
		Communication.sleep(100);
		game.sendTrade();
	}
	public void		tradeCancel()
	{
		game.sendTrade();
	}
	
	public static void main(String[] args) throws IOException 
	{
		
		CatanNetwork game = new CatanNetwork("Sebastian");
		game.readAddresses();
		game.initNetwork();
		
		Communication.sleep(2000);
		
		System.out.println();
		System.out.println("----Communication initialization finished----");
		System.out.println();		
		
		
		LinkedList<String> players = new LinkedList<String>();
		players.add("Sebastian");
		game.invite(players);
		
		Communication.sleep(2000);		
		game.sendInvitationAnswer("Sebastian", SystemType.ACCEPT);		
		Communication.sleep(2000);
				
		game.start();
		
		Communication.sleep(2000);
				
		
		System.out.println();
		System.out.println("----Testing Update Messages----");
		System.out.println();
		
		Communication.sleep(5000);
		/* Testing Update Messages */
		
		Board board = Board.getInstance();													// na potrzeby handlu
		board.loadMatrix();
		board.setNeighbours();
			
		/*
		game.updateBoard(board);		
		game.updateNode(board.getNode(5), 6);
		game.updateTile(board.getTile(5), 5);
		game.updateDice(5);
		game.updateResources(5);
		game.updateThiefSteal("Sebastian");
		game.updateGiveThiefLoot("Sebastian", "ore");
		*/
		Communication.sleep(2000);
		
		
		System.out.println();
		System.out.println("----Testing Trade Messages----");
		System.out.println();
 
		Communication.sleep(5000);
		
		HashMap<String, Integer> give = new HashMap<String, Integer>();
		give.put("ore", 2);		
		HashMap<String, Integer> get = new HashMap<String, Integer>();
		get.put("wood", 2);
						
		game.tradeOffert(give, get);																			
		Communication.sleep(2000);		
		game.tradeAnswer("Sebastian", TradeType.YES);										
		Communication.sleep(2000);		
		game.tradeDeal("Sebastian");														
		Communication.sleep(2000);		
		game.tradeCancel();													
		Communication.sleep(2000);																																			
		game.endTurn();
		game.endGame();
		Communication.sleep(2000);															
					
												
		System.out.println();				
		System.out.println("all threads:");
		System.out.println();
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for(Thread t: threadSet)
		{
			System.out.println(t.getName());
		}
		/**/

		
		
	
		
	}

}
