package catan.network;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import representation.View;


import catan.network.GameCommunication.InvStatus;
import catan.network.SystemMessage.SystemType;

public class GameMessageHandler extends MessageHandler implements Serializable{
	
	GameCommunication gameCom;
	
	
	private boolean inGame;
	
	
	@Override
	public void setting() 
	{
		gameCom = (GameCommunication) this.comDecorator; 
	}	
	@Override
	public void handleMsg(Message msg) 
	{
		switch(msg.type)
		{
			case SYSTEM:
				handleMessage((SystemMessage)msg);
				break;
		}
		
	}
	protected void handleMessage(SystemMessage msg)		
	{
		//PEER, INVITATION, ACCEPT, REJECT, PLAY, ABANDON, END_TURN, END_GAME;
		switch (msg.getSubType())
		{
			
			case INVITATION:
				handleMsgInvitation();
				break;
				
			case ACCEPT:
				handleMsgAccept();
				break;
				
			case REJECT:
				handleMsgReject();
				break;
				
			case START_GAME:
				handleMsgStartGame();
				break;
				
			case INV_LIST:
				handleMsgInvList((MsgInvList)msg);
				break;
				
			case ABANDON:
				handleMsgAbandon();
				break;
				
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;		
					
		}
		
	}
	
	
	/*System messeges*/
	synchronized void handleMsgInvitation()													// trzeba to jakos zgrac, wybor:  accept, reject 
	{											
											// ten fragment tylko wystepuje w testowaniu lokalnym
		if(gameCom.getInGame() == true && !this.peer.socketIn.getInetAddress().getHostAddress().equals("127.0.0.1"))		// jezeli dostalem obce zaproszenie 
		{
			Message msg = null;
			try {					
				msg = gameCom.system.getSystemMessage(SystemType.REJECT, null);
			} catch (ContentException e) {
				e.printStackTrace();
			}
			
			try {
				gameCom.sendTo(nick, msg);
			} catch (IOException e) {
				System.err.println("MsgInvitation error, problem with sendTo");
				e.printStackTrace();
			}
			return ;
		}
		
		
		
		System.out.println("Invitation to a game from: " +  this.nick);
		if(!View.invFrom.contains(this.nick))  View.invFrom.add(this.nick);
		if (View.invFrom.size()>0)System.out.println("Added to View invList: " +  View.invFrom.get(0));
		// TODO Wyswietlenie okienka z powiadomieniem i 2 guzikami		
			
	}
	synchronized void handleMsgAccept()					
	{
		
		System.out.println("Player: " +  nick + " - Accepted");				
		gameCom.putInv(nick, InvStatus.ACCEPTED);				
		gameCom.sendInvList();
	}
	synchronized void handleMsgReject()
	{		
		System.out.println("Player: " +  nick + " - Rejected");				
		gameCom.putInv(nick, InvStatus.REJECTED);				
		gameCom.sendInvList();
	}
	synchronized void handleMsgStartGame()				// dodac metode startu gry jako takiej, ustawiæ zmienna inGame czy cos
	{		
		inGame = true;
		gameCom.setInRealGame(true);
		System.out.println("~~	The game starting ...");							
	}
	synchronized void handleMsgAbandon()				// 
	{
		System.out.println("The game has been abandoned ...");
		gameCom.setInGame(false);
	}
	synchronized void handleMsgInvList(MsgInvList msg) {
		
		System.out.println("Received InvList: " + msg.getContent());
		// TODO
				
		Map<String, InvStatus> invPlayers = msg.getContent();
		
		gameCom.invPlayers.clear();
		
		for(String ip : invPlayers.keySet())
		{
			if(gameCom.getStatePeers().contains(gameCom.getNickFromIp(ip)))	// jezeli mam z nim polaczenie to zapisuje go do invplayers
				gameCom.putInv(gameCom.getNickFromIp(ip), invPlayers.get(ip));
			else if(!(("/"+ip).toString().equals(this.peer.socketIn.getLocalAddress().toString()))) 	// jezeli rozny ode mnie
				gameCom.addNodeP2P(ip);										
		}
		// dodanie osoby, ktora mi przeslala invliste
		gameCom.putInv(this.gameCom.getNickFromIp((this.peer.socketOut.getInetAddress().getHostAddress())), InvStatus.WAIT);
		
				
	}

		
	
	
}
