package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Iterator;

public abstract class MessageHandler implements Runnable{
	
	protected CommunicationDecorator comDecorator;
	protected Peer peer; 
	protected String nick;
		
	public void 			setDecorator(CommunicationDecorator communicationDecorator)
	{
		this.comDecorator = communicationDecorator;
	}
	public void 			setPeer(Peer peer)
	{
		this.peer = peer;				
	}
	public void				setNick(String nick)
	{
		this.nick = nick;
	}
	public abstract void	handleMsg(Message msg);
	public abstract void	setting();

		
	public final void run()
	{
		
		try
		{
					
			setting();
			while(true)
			{
				//System.out.println("~" + Thread.currentThread().getName() + " - oczekiwanie na wiadomosc...");
				Message msg = (Message)peer.receive();				// Problem z czytaniem czesci objektow
				System.out.println("~" + "Received message from: " + nick);
				handleMsg(msg);
				
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
