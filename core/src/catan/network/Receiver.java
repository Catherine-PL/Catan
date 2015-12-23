package catan.network;

import java.io.IOException;

class Receiver implements Runnable{								//	ObjectStreamException <-- UWAGA

	//String nickname;
	Peer peer;
	Communication com;
	
	
	
	Receiver(/*String nickname, */Peer peer, Communication communication)
	{
		//this.nickname = nickname;
		this.peer = peer;
		this.com = communication;
	}
	
	
	
	private void handleMessage(SystemMessage msg)		
	{
		//PEER, INVITATION, ACCEPT, REJECT, PLAY, ABANDON, END_TURN, END_GAME;
		switch (msg.getSubType())
		{
			
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
				com.msgHandler.handleMsgEndTurn(peer);
				break;
				
			case END_GAME:
				com.msgHandler.handleMsgEndGame(peer);
				break;
				
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;					
		}
		
	}
	private void handleMessage(UpdateMessage msg)		
	{
		//DICE;
		switch (msg.getSubType())
		{
			case DICE:
				com.msgHandler.handleMsgDice(peer,(MsgDice)msg);
				break;
				
			case BOARD:
				com.msgHandler.handleMsgBoard((MsgBoard)msg);
				break;
				
			case TILE:
				com.msgHandler.handleMsgTile((MsgTile)msg);
				break;
				
			case NODE:
				com.msgHandler.handleMsgNode((MsgNode)msg);
				break;
				
			case RESOURCES:
				com.msgHandler.handleMsgResources(peer, (MsgResources)msg);
				break;
										
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;					
		}
		
	}
	private void handleMessage(TradeMessage msg)
	{
		switch (msg.getSubType())
		{
			case OFFERT:
				com.msgHandler.handleMsgOffert(peer,(MsgOffert)msg);
				break;
				
			case YES:
				com.msgHandler.handleMsgYes(peer, (MsgYes)msg);
				break;
				
			case NO:
				com.msgHandler.handleMsgNo(peer, (MsgNo)msg);
				break;
				
			case DEAL:
				com.msgHandler.handleMsgDeal(peer, (MsgDeal)msg);
				break;
				
			case END_TRADE:
				com.msgHandler.handleMsgEndTrade((MsgEndTrade)msg);
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
				//System.out.println(Thread.currentThread().getName() + " - oczekiwanie na wiadomosc...");
				Message msg = (Message)peer.receive();				// Problem z czytaniem czesci objektow
																	// OptionalDataException albo CastExcepotion
				switch (msg.getType())
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
			System.err.println("Utracono polaczenie: " + Thread.currentThread().getName());
			e.printStackTrace();
		} 
		
	}



}
