package catan.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import catan.network.GameCommunication.InvStatus;
import catan.network.TradeMessage.TradeType;
import database.Board;

public class CatanMessageHandler extends GameMessageHandler {

	CatanCommunication catanCom;
	
	private Board board;
	private HashMap<String, Integer> give;
	private HashMap<String, Integer> get;

	private static int msgdice = 0;			// how many msgDice
	
	
	
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
				
			case RESOURCES:
				handleMsgResources((MsgResources)msg);
				break;
				
			case END_TURN:
				handleMsgEndTurn();
				break;
				
			case END_GAME:
				handleMsgEndGame();
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
	
	
	/* SystemMessage */
	
	/* UpdateMessage */

	synchronized void handleMsgEndTurn()
	{				
		System.out.println("Player: " + nick + " has finished turn.");
	}
	synchronized void handleMsgEndGame()
	{	
		System.out.println("Player: " + nick + " has won the game.");
	}	
	synchronized void handleMsgDice(MsgDice msg)
	{					
		System.out.print("From: " + nick);
		System.out.println(" -- Dice result:" + msg.getContent());
		
		if(msgdice < this.gameCom.invPlayers.size())									// tylko do ustalenia kolejnosci
		{
			msgdice++;			
			gameCom.invPlayers.put(nick, InvStatus.WAIT);							// jezeli wszyscy sa wait, to kolejnosc ustalona
			
			if(msg.getContent() > catanCom.myNumber)
			{
				catanCom.inQueue++;
			}else if(msg.getContent() == catanCom.myNumber)
			{
				if(this.comDecorator.getNickname().compareToIgnoreCase(nick) > 0)
				{
					catanCom.inQueue++;
				}
			}
		}
	}
	synchronized void handleMsgBoard(MsgBoard msg)			// podmienic board tam gdzie jest ona przechowywana
	{
		System.out.println("Board actualization ...");
		//this.board = msg.getContent();
	}	
	synchronized void handleMsgNode(MsgNode msg)			// problem z aktualizacja
	{
		System.out.println("Node actualization ...");
		
	//	Node n = msg.getContent();			
	//	int i = msg.getIndex();
	//	System.out.println("Node: " + n + " on index: " + i);
	//	board.setNode(n, i);
	//	System.out.println(n.getNodeNumber() + ", " + board.getNode(i));
		
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
	
	/* TradeMessage */
	
	synchronized void handleMsgOffert(MsgOffert msg)								// podobnie jak z zaproszeniami 
	{					
		System.out.println();
		System.out.println("--Offert from: " + nick);
		
		get = msg.getGive();
		give = msg.getGet();
		//HashMap<String, Integer> icanget = msg.getGive();
		//HashMap<String, Integer> ihavetogive = msg.getGet();						
		
		System.out.println("What he wants: " + give);
		System.out.println("What i would get: " + get);
		System.out.println();
		
		// TODO wybor czy sie zgadzamy czy nie na ta propozycje
		// Sprawdzenie czy mogê siê zgodziæ. Jeœli nie to wysy³am od razu no.
		//Message ms = trade.getTradeMessage(TradeType.NO);
		Message ms = catanCom.trade.getTradeMessage(TradeType.YES);
		
		try {
			catanCom.sendTo(nick, ms);
		} catch (IOException e) {
			System.err.println("Utracono polaczenie z: " + nick);
			catanCom.disconnected(nick);				
		}
		
	}
	synchronized void handleMsgYes(MsgYes msg)
	{		
		System.out.println("Player: " + nick + " has accepted your offert");						
		catanCom.invPlayers.put(nick, InvStatus.ACCEPTED);		
		System.out.println("Players" + catanCom.invPlayers);			
	}
	synchronized void handleMsgNo(MsgNo msg)
	{				
		System.out.println("Player " + nick + " has rejected your offert");									
		catanCom.invPlayers.put(nick, InvStatus.REJECTED);
		System.out.println(catanCom.invPlayers);
		
	}
	synchronized void handleMsgDeal(MsgDeal msg)
	{			
		System.out.println("Deal with: " + nick);	
		
		//	aktualizacja surowcow gracza. Dodajac surowce z get, Odejmujac surowce z give	
	}
	synchronized void handleMsgEndTrade(MsgEndTrade msg)
	{			
		Set<String> s = catanCom.invPlayers.keySet();
		for(String nick : s)
		{			
			catanCom.invPlayers.put(nick, InvStatus.WAIT);
		}
	}
	
						

	
}
