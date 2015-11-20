package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

class Receiver implements Runnable {

	//String nickname;
	Peer peer;
	Communication com;
	
	
	
	Receiver(/*String nickname, */Peer peer, Communication communication)
	{
		//this.nickname = nickname;
		this.peer = peer;
		this.com = communication;
	}
	
	
	
	private void handleMessage(SystemMessage msg) throws IOException 		// OBSLUGA WYJATKU !!!!
	{
		//PEER, INVITATION, ACCEPT, REJECT, PLAY, ABANDON, END_TURN, END_GAME;
		switch (msg.getSubType())
		{
			case PEER:
				com.msgHandler.handleMsgPeer(peer.socketIn, peer.input, (MsgPeer)msg);
				break;
			
			case INVITATION:
				com.msgHandler.handleMsgInvitation(peer);
				break;
				
			case ACCEPT:
				com.msgHandler.handleMsgAccept(peer);
				break;
				
			case REJECT:
				com.msgHandler.handleMsgReject(peer);
				break;
				
			case START_GAME:
				com.msgHandler.handleMsgStartGame(peer);
				break;
				
			case ABANDON:
				com.msgHandler.handleMsgAbandon(peer);
				break;
				
			case END_TURN:
				break;
				
			case END_GAME:
				break;
				
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;					
		}
		
	}
	private void handleMessage(UpdateMessage msg) throws IOException 		// OBSLUGA WYJATKU !!!!
	{
		//DICE;
		switch (msg.getSubType())
		{
			case DICE:
				com.msgHandler.handleMsgDice(peer,(MsgDice)msg);
				break;
						
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;					
		}
		
	}
	
	
	@Override
	public void run() {

		// TODO Auto-generated method stub
		// odczytuje co przyszlo
		// sprawdza co dokldanie
		// odpala funkcje z communication, ktora jest synchronizowana
		/*
		 * robimy tyle tych watkow demonow ile mamy peersow
		 * jezeli jakis nowy dojdzie to i dla niego rowniez
		 * podczas gry potrzebujemy tylko te watki z ktorymi gramy
		 * 
		 * */
		
		try
		{
					
			while(true)
			{
				System.out.println(Thread.currentThread().getName() + " - oczekiwanie na wiadomosc...");
				Message msg = (Message)peer.receive();
			
			
				switch (msg.getType())
				{
					case SYSTEM:
						handleMessage((SystemMessage)msg);
						break;
						
					case UPDATE:
						handleMessage((UpdateMessage)msg);
						break;
					
					case TRADE:
						break;
				
							
					default:
						System.err.println("Otrzymana wiadomosc jest bledna");
						break;					
				}		
			}
		}
		catch (ClassNotFoundException e) {		
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("Utracono polaczenie: " + Thread.currentThread().getName());
		} 
		
	}

}
