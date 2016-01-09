package catan.network;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import catan.network.GameCommunication.InvStatus;
import catan.network.SystemMessage.SystemType;

public class GameMessageHandler extends MessageHandler implements Serializable{
	
	GameCommunication gameCom;
	
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
				handleMsgStartGame((MsgStartGame)msg);
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
	
	private void sendInvList()
	{
		Set<String> invPlayers = gameCom.invPlayers.keySet();
		Map<String, InvStatus> invPlayersSend = new HashMap<String, InvStatus>();
		for(String name : invPlayers)
		{
			invPlayersSend.put(gameCom.getIpFromNick(name),gameCom.invPlayers.get(name));
		}
		
		
		Message msg = null;
		try {
			msg = gameCom.system.getSystemMessage(SystemType.INV_LIST, invPlayersSend);
		} catch (ContentException e) { 
			e.printStackTrace();
		}
		
		for(String name : invPlayers)
		{
			if(gameCom.invPlayers.get(name)==InvStatus.ACCEPTED)
				try {
					gameCom.sendTo(name, msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		
		// TODO Wyswietlenie okienka z powiadomieniem i 2 guzikami		
			
	}
	synchronized void handleMsgAccept()					
	{
		
		System.out.println("Player: " +  nick + " - Accepted");				
		gameCom.putInv(nick, InvStatus.ACCEPTED);				
		this.sendInvList();
	}
	synchronized void handleMsgReject()
	{		
		System.out.println("Player: " +  nick + " - Rejected");				
		gameCom.putInv(nick, InvStatus.REJECTED);				
		this.sendInvList();
	}
	synchronized void handleMsgStartGame(MsgStartGame msg)				// dodac metode startu gry jako takiej, ustawiæ zmienna inGame czy cos
	{		
		System.out.println("~~	The game starting ...");		
		System.out.println("Player's ip: " + msg.getContent());		// wszyscy gracze w grze sa w msg

		gameCom.invPlayers.clear();					// usuniecie wszystkich zbednych					
		for(String ip : msg.getContent())
		{			
			if(gameCom.invPlayers.containsKey(gameCom.getNickFromIp(ip)))	// jezeli mam go w invPlayers to aktualizuje
				gameCom.putInv(gameCom.getNickFromIp(ip), InvStatus.WAIT);
			else if(gameCom.getStatePeers().contains(gameCom.getNickFromIp(ip)))	// jezeli mam z nim polaczenie to zapisuje go do invplayers
				gameCom.putInv(gameCom.getNickFromIp(ip), InvStatus.WAIT);
			else
				System.err.println("Cos zle pomyslane w handleMsgStartGame, GameMessageHandler");
		
		}
		System.out.println("-All players have been set");
		// TODO change view of game, adding new ips
			
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
		for(String ip : invPlayers.keySet())
		{
			if(gameCom.invPlayers.containsKey(gameCom.getNickFromIp(ip)))	// jezeli mam go w invPlayers to aktualizuje
					gameCom.putInv(gameCom.getNickFromIp(ip), invPlayers.get(ip));	
			else if(gameCom.getStatePeers().contains(gameCom.getNickFromIp(ip)))	// jezeli mam z nim polaczenie to zapisuje go do invplayers
					gameCom.putInv(gameCom.getNickFromIp(ip), invPlayers.get(ip));
			else																	// jezeli nie mam polaczenia to je nawiazuje			
				gameCom.addNodeP2P(ip);							
			
		}
		
				
	}

		
	
	
}
