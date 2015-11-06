package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Communication{			// jako singleton?

	private Map<String,Peer>	peers;
	private ServerSocket		serv;
	private int					servport = 8080;
	private int					port = 8080;
	
		
	private void initServPort() throws IOException
	{
		serv = new ServerSocket(servport);
	}
	private void initPeers(String nickname, PlayerIP[] players) throws IOException, ClassNotFoundException
	{
		LinkedList<Peer> l = new LinkedList<Peer>(); 
		
		for(PlayerIP p : players)
		{			
			for(int i=0; i<5; i++)
			{
				try
				{
					l.add(new Peer(nickname, p.getIp(), port+i));					
					break;
				}
				catch(IOException e)
				{
					System.out.println("Problem z utworzeniem po³aczenia");
					System.out.println("Kolejna próba z portem o 1 wiêkszym");
					
				}
			}
		}	
		
		
		
		// Accepting connection and adding to peer socketIn and InputStream
		
		Iterator it = l.iterator();
		
		
		for(int i=0; i<players.length; i++)
		{
			Socket client = serv.accept();
					
			System.out.println("zakaceptowano polaczenie, które przysz³o z: " + client.getInetAddress() + " : " + client.getPort());
			System.out.println("Aktualnie przypisany mu port: " + client.getLocalPort());			
			
			
			while(it.hasNext())
			{
				Peer peer = (Peer)it.next(); 
															
				// jezeli moj zapisany peer socket = ten ktory do mnie napisal
				if(peer.socketOut.getInetAddress().getHostAddress().equals(client.getInetAddress().getHostAddress()))
				{	
					peer.socketIn = client;
					peer.input = new ObjectInputStream(client.getInputStream());
					
					String nick = (String)peer.input.readObject();					
					System.out.println("Wiadomosc: " + nickname);		
					
					peers.put(nick, peer);				// wpisanie do mapy peera z jego nickname	
					for(PlayerIP p : players)
					{						
						if(p.address.equals(peer.socketOut.getInetAddress().getHostAddress())) 
						{
							if(p.nickname!=null) continue;				// potrzebne tylko dla localhosta
								p.nickname = nick;
							break;
						}
					}
					
					
					break;
				}
			}		
						
		}
		
	}
	private void initCommunication(String nickname, PlayerIP[] players)
	{
		System.out.println("Tworzenie po³¹czenia z graczami ...");
		peers = new HashMap<String,Peer>();
		
		// inicjalizacja portu servera		
		for(int i=0; i<5; i++)		// a¿ 5 razy spróbuje utwozyæ ServerSocket
		{
			try
			{  
				initServPort();		
				System.out.println("Utworzono ServSocket, port: " + serv.getLocalPort());
				break;
			}
			catch(IOException port)
			{
				System.out.println("B³¹d przy tworzeniu server socket.");
				System.out.println("Ponowna próba na innym porcie o 1 wiêkszym");
				servport++;
			}
		}
		
					
		// inicjalizacji wszystkich peer, ich socketow i streamow. Wysy³anie od razu wiadomoœci o porcie lokalnym
		try {
			initPeers(nickname, players);
		} catch (Exception e1) {
			System.err.println("Niepowodzenie z utworzeniem Peers");
			e1.printStackTrace();
		}		
		
			
		System.out.println("Inicjalizacja po³¹czeñ zosta³a zakoñczona.");
		
		for(PlayerIP p : players)
			System.out.println(p.nickname + ", ");
				
	}
		
	public Communication(String myName, PlayerIP[] p)
	{
		initCommunication(myName, p);				
	}
	public void close() throws IOException
	{
		serv.close();
		for(String p : peers.keySet())
		{
			peers.get(p).close();
		}
	}
	public void send(String nick, Message msg) throws IOException
	{
		peers.get(nick).send(msg);
	}
	public Message receive(String nick) throws ClassNotFoundException, IOException
	{
		return (Message)peers.get(nick).receive();
	}	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		PlayerIP[] players = {new PlayerIP("127.0.0.1"), new PlayerIP("127.0.0.1")};	// nick i ip z hamachi
		
		//ExecutorService exec = Executors.newSingleThreadExecutor();
		//exec.execute(com);
		//exec.shutdown();
		
		
		Communication com = new Communication("Sebastian",players);		
							
		Message m;
		
		try {
			m = MessageFactory.createMessage(Message.Type.DICE, 6);
			com.send("Sebastian", m);
			m = com.receive("Sebastian");
			System.out.println(m.getType() + ": " + m.getContent());
			
			Integer i =(Integer)m.getContent();
			
			if(i.equals(6))
				System.out.println("TAK");

		} catch (ContentException e) {
			System.out.println("Blednie ustawiona zawortosc wiadomosci!");		
		}
		
		
		//if(m.getType()==Message.Type.DICE)
		
				
		com.close();		

	}







}
