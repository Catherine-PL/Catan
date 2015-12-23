package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Iterator;

public /*abstract*/ class MessageHandler implements Runnable{

	private Communication com;
	private Peer peer; 
	
	public MessageHandler(Communication communication, Peer peer)
	{
		this.com = communication;
		this.peer = peer;
	}
	
	
	public void	handleMsg(Message msg)
	{
		
	}

		
	public final void run()
	{
		
		try
		{
					
			while(true)
			{
				//System.out.println(Thread.currentThread().getName() + " - oczekiwanie na wiadomosc...");
				Message msg = (Message)peer.receive();				// Problem z czytaniem czesci objektow	
				if(msg.getType() != Message.Type.SYSTEM)
					handleMsg(msg);
				else
				{
					
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
