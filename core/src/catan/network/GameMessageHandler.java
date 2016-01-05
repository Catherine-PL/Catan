package catan.network;

import java.io.IOException;
import java.io.Serializable;

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
				handleMsgStartGame();
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
		
		// TODO Wyswietlenie okienka z powiadomieniem i 2 guzikami		
			
	}
	synchronized void handleMsgAccept()					// dodac zmienna, ze j
	{
		
		System.out.println("Player: " +  nick + " - Accepted");				
		gameCom.putInv(nick, InvStatus.ACCEPTED);		
				
	}
	synchronized void handleMsgReject()
	{		
		System.out.println("Player: " +  nick + " - Rejected");				
		gameCom.putInv(nick, InvStatus.REJECTED);				
	}
	synchronized void handleMsgStartGame()				// dodac metode startu gry jako takiej, ustawiæ zmienna inGame czy cos
	{		
		System.out.println("~~	The game starting ...");	
		// TODO change view of game
			
	}
	synchronized void handleMsgAbandon()				// 
	{
		System.out.println("The game has been abandoned ...");
		gameCom.setInGame(false);
	}
	

		
	
	
}
