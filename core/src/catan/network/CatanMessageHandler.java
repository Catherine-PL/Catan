package catan.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import catan.network.GameCommunication.InvStatus;
import catan.network.TradeMessage.TradeType;
import database.Board;
import database.Building;
import database.Game;

public class CatanMessageHandler extends GameMessageHandler {

	CatanCommunication catanCom;
	
	static transient Game game;
	
			
	CatanMessageHandler(Game game)
	{
		CatanMessageHandler.game = game;
	}
	
	@Override
	public void setting() 
	{
		super.setting();	
		catanCom = (CatanCommunication) this.comDecorator;
	}	
	@Override
	public void handleMsg(Message msg) 
	{
		switch(msg.type)
		{
			case SYSTEM:
				handleMessage((SystemMessage)msg);
				break;
			case UPDATE:
				handleMessage((UpdateMessage)msg);
				break;
			case TRADE:
				handleMessage((TradeMessage)msg);
				break;				
		}
		
	}
	
	protected void handleMessage(UpdateMessage msg)		
	{
		//DICE;
		switch (msg.getSubType())
		{
			case DICE:
				handleMsgDice((MsgDice)msg);
				break;
				
			case BOARD:
				handleMsgBoard((MsgBoard)msg);
				break;
				
			case TILE:
				handleMsgTile((MsgTile)msg);
				break;
				
			case NODE:
				handleMsgNode((MsgNode)msg);
				break;
				
			case ROAD:
				handleMsgRoad((MsgRoad)msg);
				break;
				
			case RESOURCES:
				handleMsgResources((MsgResources)msg);
				break;
				
			case THIEF_TARGET:
				handleMsgThiefTarget((MsgThiefTarget)msg);
				break;
				
			case THIEF_LOOT:
				handleMsgThiefLoot((MsgThiefLoot)msg);
				break;
				
			case END_TURN:
				handleMsgEndTurn();
				break;
				
			case END_GAME:
				handleMsgEndGame();
				break;
			
			case ORDER:
				handleMsgOrder((MsgOrder)msg);
				break;
										
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;					
		}
		
	}	
	protected void handleMessage(TradeMessage msg)
	{
		switch (msg.getSubType())
		{
			case OFFERT:
				handleMsgOffert((MsgOffert)msg);
				break;
				
			case YES:
				handleMsgYes((MsgYes)msg);
				break;
				
			case NO:
				handleMsgNo((MsgNo)msg);
				break;
				
			case DEAL:
				handleMsgDeal((MsgDeal)msg);
				break;
				
			case END_TRADE:
				handleMsgEndTrade((MsgEndTrade)msg);
				break;
						
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;					
		}
	}
			
	
	/* UpdateMessage */

	synchronized void handleMsgEndTurn()
	{				
		System.out.println("Player: " + nick + " has finished turn.");		
		game.endTurn();
	}
	synchronized void handleMsgEndGame()
	{	
		System.out.println("Player: " + nick + " has won the game.");
	}	
	synchronized void handleMsgDice(MsgDice msg)
	{					
		System.out.print("From: " + nick);
		System.out.println(" -- Dice result:" + msg.getContent());
		
	}
	synchronized void handleMsgBoard(MsgBoard msg)			// podmienic board tam gdzie jest ona przechowywana
	{
		System.out.println("Board actualization ...");
		Board board = msg.getContent();
		Board.setInstance(board);
		//game.setBoard(board);
		//TODO nie wiem czy to dobrze zadzia³a
		//game.getBoard().setNeighbours();
		//game.getBoard().setRoadsy();
		//game.getBoard().setNoRoads();
		
	}	
	synchronized void handleMsgNode(MsgNode msg)			// problem z aktualizacja
	{
		System.out.println("Node actualization ...");
				
		Boolean city = msg.getContent();			
		int i = msg.getIndex();
		
		if(!city)
			Building.buildSettlement(game.getActualPlayer(), game.getBoard().getNode(i));
		else
			Building.buildCity(game.getActualPlayer(), game.getBoard().getNode(i));
	}
	synchronized void handleMsgRoad(MsgRoad msg) 
	{
		System.out.println("Road actualization ...");		
		Integer idNode = msg.getContent();
		int idRoad = msg.getContent2();		
		System.out.println("idNode: " + idNode);
		System.out.println("idRoad: " + idRoad);
		System.out.println("idRoad: " + game.getBoard().boardRoads);
		game.getBoard().getNode(idNode).buildRoad(game.getActualPlayer(),idRoad);
	}
	synchronized void handleMsgTile(MsgTile msg)
	{
		System.out.println("Tail actualization ...");
		
	//	Tile t = msg.getContent();
	//	int i = msg.getIndex();
	//	board.setTile(t, i);
		
		
	}
	synchronized void handleMsgResources(MsgResources msg)
	{
		System.out.println("Resources number actualization...");
		
		int n = msg.getContent();			
		System.out.println("Player: " + nick + ", number of resources : " + n);
		
	}
	synchronized void handleMsgThiefTarget(MsgThiefTarget msg) {
		// TODO wylosowanie i wys³anie odpowiedniego surowca
		System.out.println("I am Thief's target.... I will lose a resource ....");
		System.out.println("It is a wood ....");
		this.catanCom.sendUpdate(nick, "wood");
	}
	synchronized void handleMsgThiefLoot(MsgThiefLoot msg) {
		// TODO 
		String resource = msg.getContent();
		System.out.println("I stole a " + resource + " from: " + this.nick);
	}
	synchronized void handleMsgOrder(MsgOrder msg) 
	{
		List<String> ip = msg.getContent();
		
		catanCom.queue.clear();
		
		System.out.println("Received ip, Order: " + ip);		
		
		for(String i : ip)
		{
			if(!i.equals("1.1.1.1"))
			{					
				if(!(("/"+i).toString().equals(this.peer.socketIn.getLocalAddress().toString()))) // rozny ode mnie
					catanCom.queue.add(catanCom.getNickFromIp(i));
				else
					catanCom.queue.add("Me"); //TODO
			}
			else
				catanCom.queue.add(catanCom.getNickFromIp(this.peer.socketOut.getInetAddress().getHostAddress()));
				
		}
		
		System.out.println("Order: " + catanCom.queue);
	}
	
	
	/* TradeMessage */
	
	synchronized void handleMsgOffert(MsgOffert msg)								// podobnie jak z zaproszeniami 
	{					
		System.out.println();
		System.out.println("--Offert from: " + nick);
		
		Map get = msg.getGive();
		Map give = msg.getGet();
		
		System.out.println("What he wants: " + give);
		System.out.println("What i would get: " + get);
		System.out.println();
				
		((GameCommunication)this.catanCom).setTradeState(TradeMessage.TradeType.OFFERT,get,give);					
		
	}
	synchronized void handleMsgYes(MsgYes msg)
	{		
		System.out.println("Player: " + nick + " has accepted your offert");						
		catanCom.putInv(nick, InvStatus.ACCEPTED);				
	}
	synchronized void handleMsgNo(MsgNo msg)
	{				
		System.out.println("Player " + nick + " has rejected your offert");									
		catanCom.putInv(nick, InvStatus.REJECTED);		
	}
	synchronized void handleMsgDeal(MsgDeal msg)
	{			
		System.out.println("Deal with: " + nick);	
		((GameCommunication)this.catanCom).setTradeState(TradeMessage.TradeType.DEAL);
		Communication.sleep(100);
		//	TODO aktualizacja surowcow gracza. Dodajac surowce z get, Odejmujac surowce z give	
	}
	synchronized void handleMsgEndTrade(MsgEndTrade msg)
	{			
		System.out.println("Trade has been ended");
		Set<String> s = catanCom.getStateInv().keySet();
		for(String nick : s)
		{			
			catanCom.putInv(nick, InvStatus.WAIT);
		}
		((GameCommunication)this.catanCom).setTradeState(TradeType.END_TRADE);
		Communication.sleep(100);
		((GameCommunication)this.catanCom).setTradeState(null);
		
	}
	
						

	
}
