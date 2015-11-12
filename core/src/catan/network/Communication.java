package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class which provide us P2P architecture and basic communication in an internal network.
 * @author Sebastian
 *
 */
public class Communication{			// jako singleton?

	private Map<String,Peer>	peers;
	private ServerSocket		serv;
	private int					servport = 8080;
	private int					port = 8080;
	
		
	private void initServPort() throws IOException
	{
		serv = new ServerSocket(servport);
	}
	private void initPeers(String nickname, Collection<PlayerIP> p2) throws IOException, ClassNotFoundException
	{
		LinkedList<Peer> l = new LinkedList<Peer>(); 
		
		for(PlayerIP p : p2)
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
		
		
		for(int i=0; i<p2.size(); i++)
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
					for(PlayerIP p : p2)
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
	private void initCommunication(String nickname, Collection<PlayerIP> p2)
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
			initPeers(nickname, p2);
		} catch (Exception e1) {
			System.err.println("Niepowodzenie z utworzeniem Peers");
			e1.printStackTrace();
		}		
		
			
		System.out.println("Inicjalizacja po³¹czeñ zosta³a zakoñczona.");
		
		System.out.println("Collection PlayerIP");
		for(PlayerIP p : p2)
			System.out.print(p.nickname + ": " + p.address + "; ");
				
		System.out.println("");
	}
	
	/**
	 * Constructor
	 * @param myName My nickname which is propagated to every node in P2P network. 
	 * @param p Array of PlayerIP class which contains only IP. However, nicknames are added to this collection. 
	 */
	public Communication(String myName, Collection<PlayerIP> p)
	{
		initCommunication(myName, p);				
	}	
	/**
	 * Adding new PlayerIP to PlayerIP collection with adding nickname, creating new peer
	 * @param players Collection for PlayerIP
	 * @param p New PlayerIP 
	 * @param nickname Nickname to propagate
	 * @throws IOException Sometimes heppens
	 * @throws ClassNotFoundException Nothing to read, or it wasn't Object.
	 */
	public void addPlayerIP(Collection<PlayerIP> players, PlayerIP p, String nickname) throws IOException, ClassNotFoundException
	{
				
		Peer n = null;
		
		// 5 krotna proba polaczenia sie
		for(int i=0; i<5; i++)
		{
			try
			{
				n = new Peer(nickname, p.getIp(), port+i);					
				break;
			}
			catch(IOException e)
			{
				System.out.println("Problem z utworzeniem po³aczenia");
				if(i==4)
					throw new IOException();
				System.out.println("Kolejna próba z portem o 1 wiêkszym");								
			}
		}
		
		
						
		Socket client = serv.accept();	// nowe polaczenie
		
		System.out.println("zakaceptowano polaczenie, które przysz³o z: " + client.getInetAddress() + " : " + client.getPort());
		System.out.println("Aktualnie przypisany mu port: " + client.getLocalPort());			
		
			
			// jezeli moj zapisany peer socket = ten ktory do mnie napisal
		if(n.socketOut.getInetAddress().getHostAddress().equals(client.getInetAddress().getHostAddress()))
		{	
			n.socketIn = client;
			n.input = new ObjectInputStream(client.getInputStream());
			
			String nick = (String)n.input.readObject();					
			System.out.println("Wiadomosc: " + nick);		

			peers.put(nick, n);				// wpisanie do mapy peera z jego nickname
			
			p.nickname = nick;
			players.add(p);				
							
		}									
		
		
	}
	/**
	 * Close is a function to close every connection between nodes, and to close servSocket.
	 * @throws IOException happens during closing streams and servSocket
	 */
	public void close() throws IOException
	{
		serv.close();
		for(String p : peers.keySet())
		{
			peers.get(p).close();
		}
	}
	/**
	 * Method which sends a massage to another node.
	 * @param nick Nickname of a node 
	 * @param msg Message to send
	 * @throws IOException Whenever writing to an OutputStream fails
	 */
	public void send(String nick, Message msg) throws IOException
	{
		peers.get(nick).send(msg);
	}
	/**
	 * Method for reading messages from network
	 * @param nick Defines a node from which we want receive a message  
	 * @return Return a message
	 * @throws ClassNotFoundException Nothing no read?
	 * @throws IOException Whenever reading from an InputStream fails
	 */
	public Message receive(String nick) throws ClassNotFoundException, IOException
	{
		return (Message)peers.get(nick).receive();
	}	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		LinkedList<PlayerIP> players = new LinkedList();	// nick i ip z hamachi
		players.add(new PlayerIP("127.0.0.1"));
		players.add(new PlayerIP("127.0.0.1"));
		
		//ExecutorService exec = Executors.newSingleThreadExecutor();
		//exec.execute(com);
		//exec.shutdown();
		
		
		Communication com = new Communication("Sebastian",players);		
							
		Message m;
		
		try 
		{
			m = MessageFactory.createMessage(Message.Type.DICE, 6);
			com.send("Sebastian", m);
			m = com.receive("Sebastian");
			System.out.println(m.getType());
			
			if(m.getType()==Message.Type.DICE)
				System.out.println("Wynik rzutu: " + ((MsgDice)m).getContent() );
						
			
		} catch (ContentException e) 
		{
			System.out.println("Blednie ustawiona zawortosc wiadomosci!");		
		}	
		
		com.addPlayerIP(players, new PlayerIP("127.0.0.1"), "Sebastian");
		
		System.out.println("Collection PlayerIP");
		for(PlayerIP p : players)
			System.out.print(p.nickname + ": " + p.address + "; ");
				
		System.out.println("");
	
				
		com.close();		

	}







}
