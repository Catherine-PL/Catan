package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
public class Communication implements Runnable{			// jako singleton?
	
	private Collection<PlayerIP>	playersIP;			// operacje nie sa zsynchronizowane
	private String 					nickname;
	private Map<String,Peer>		peers;				// nie sa zsynchronizowane
	private ServerSocket			serv;
	private final int				servport = 8081;	
	
			
	private void initServPort() throws IOException							// a co gdy jednak nam wywali blad? obs³uzyæ czy nie?
	{
		serv = new ServerSocket(servport);
	}
	private void initPeers() throws IOException, ClassNotFoundException		// moze ustawic jakis timeout?
	{			
		int i = 0;
		for(PlayerIP p : playersIP)
		{	
			try
			{
				p.nickname = Integer.toString(i);	// przypisane tymczasowej nazwy
				peers.put(p.nickname , new Peer(nickname, p.getIp(), servport));		// proba polaczenia i wyslania wiadomosci z nickiem		
			}
			catch(SocketTimeoutException e)
			{
				System.out.println("Problem z utworzeniem po³aczenia (timeout) z: " + p.getIp());					
			}
			catch(IOException e)
			{
				System.out.println("Problem z utworzeniem po³aczenia z: " + p.getIp());					
				//playersIP.remove(p);
				//continue;
			}
			
			i++;
		}			
	}
	private void initCommunication() throws IOException
	{
		System.out.println("Tworzenie po³¹czenia z graczami ...");
		peers = new HashMap<String,Peer>();
		
		// inicjalizacja portu servera		
		
		try			// moze wystapic problem z utworzeniem, wtedy trzeba zmienic port i wyslac o tym wiadomosc
		{  
			initServPort();		
			System.out.println("Utworzono ServSocket, port: " + serv.getLocalPort());
		}
		catch(IOException port)
		{
			System.err.println("B³¹d przy tworzeniu server socket.");			
			throw new IOException();
		}
	
					
		// inicjalizacji wszystkich peer. Wysy³anie od razu wiadomoœci o nicku
		try 
		{
			initPeers();								
		} 
		catch (Exception e1) {
			System.err.println("Problem z nas³uchiwaniem, odebraniem po³¹czenia");
			e1.printStackTrace();
		}											
		
		
		System.out.println("Wys³anie wiadomoœci online(PEER) do wszystkich graczy.");
		
		System.out.println("Collection PlayerIP");
		for(PlayerIP p : playersIP)
			System.out.print(p.nickname + ": " + p.address + "; ");
				
				
		System.out.println("");
		
		
		// odbieranie nowych polaczen -- watek demon
		Thread deamon = new Thread(this);
		deamon.setDaemon(true);
		deamon.start();
		
	}
	
	private void handleMsgPeer(Socket client, ObjectInputStream input, MsgPeer msg) throws IOException
	{
		// Accepting connection and adding to peer socketIn and InputStream		
		int i = 0;
		System.out.println("MsgPeer");
		Iterator<PlayerIP> it = playersIP.iterator();
				
		while(it.hasNext())
		{
			PlayerIP p = it.next();
			// dopasowanie do moich peersow
			if(p.address.equals(client.getInetAddress().getHostAddress()))
			{
				Peer peer = peers.get(p.nickname);
				peer.socketIn = client;
				peer.input = input;
				String nick = msg.getContent();
				System.out.println("Otrzymana wiadomosc: " + nick);
				
				peers.remove(p.nickname);
				peers.put(nick, peer);
				
				//send(nick, new MsgPeer(nickname));			// IOException
				
				p.nickname = nick;
				p.online = true;
				it.remove();
				playersIP.add(p);
				break;
			}	
			i++;						
		}// Jezeli obcy dla mnie gosc
		if(i == playersIP.size())
		{			
			String ip = client.getInetAddress().getHostAddress();
			PlayerIP pip = new PlayerIP(ip);
			
			Peer peer = new Peer(nickname, ip, servport);
			peer.input = input;
			peer.socketIn = client;
			
			String nick = msg.getContent();
			System.out.println("Otrzymana wiadomosc: " + nick);
			
			peers.put(nick, peer);
			pip.nickname = nick;
			pip.online = true;
			playersIP.add(pip);				
		}
	}									
	private void handleSYSTEM(Socket client, ObjectInputStream input, SystemMessage msg) throws IOException 		// OBSLUGA WYJATKU !!!!
	{
		
		switch (msg.getSubType())
		{
			case PEER:
				handleMsgPeer(client, input, (MsgPeer) msg);
				break;
				
			default:
				System.err.println("Otrzymana wiadomosc jest bledna");
				break;					
		}
		
	}
	
	/**
	 * Constructor which tries to connect with Players and send them a message with a nickname. 
	 * It updates PlayerIP's boolean attribute online and creates a demon thread, which accept new connections.  
	 * @param myName My nickname which is propagated to every node in P2P network. 
	 * @param p Array of PlayerIP class which contains only IP. However, nicknames are added to this collection. 
	 */
	public Communication(String myName, Collection<PlayerIP> p)
	{			
		// dostaje moj nick jak i graczy (nick, ip, online)
		playersIP = p;
		nickname = myName;
		try 
		{
			initCommunication();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}				
	}	
	/**
	 * Adding new PlayerIP to PlayerIP collection with adding nickname, creating new peer
	 * @param players Collection for PlayerIP
	 * @param p New PlayerIP 
	 * @param nickname Nickname to propagate
	 * @throws IOException Sometimes heppens
	 * @throws ClassNotFoundException Nothing to read, or it wasn't Object.
	 */
	public synchronized void addPlayerIP(PlayerIP p)
	{								
		try
		{
			
			p.nickname = Integer.toString(-1);
			this.playersIP.add(p);
			peers.put(p.nickname , new Peer(nickname, p.getIp(), servport));		// proba polaczenia i wyslania wiadomosci z nickiem
			
		}
		catch(IOException e)
		{
			System.out.println("Problem z utworzeniem po³aczenia z: " + p.getIp());	
			playersIP.remove(p);
		}												
		
	}
	/**
	 * Close is a function to close every connection between nodes, and to close servSocket.
	 * @throws IOException happens during closing streams and servSocket
	 */
	public synchronized void close() throws IOException
	{	
		System.out.println("closing...");
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
	/**
	 * Method which is necessary to run a thread and it is done in constructor, so don't run it!
	 */
	public void run() 
	{
		while(true)
		{
			try 
			{
				Socket client = serv.accept();
				System.out.println("zakaceptowano nowe polaczenie, które przysz³o z: " + client.getInetAddress() + " : " + client.getPort());
				System.out.println("Aktualnie przypisany mu port: " + client.getLocalPort());		
								
				ObjectInputStream input = new ObjectInputStream(client.getInputStream());								
				Message msg	= (Message)input.readObject();								
				
				// W sumie zawsze bêdzie tylko MsgPeer albo b³¹d
				switch (msg.getType())
				{
					case SYSTEM:
						handleSYSTEM(client, input, (SystemMessage)msg);
						break;
								
						
					default:
						System.err.println("Otrzymana wiadomosc jest bledna");
						break;					
				}				
				
			} 
			catch (IOException e) 
			{
				System.err.println("method run, serv.accept(), closing server before cleaning or problem with sending");
				try {
					this.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
					break;
				}
				
			}
			catch (ClassNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (Exception e) 
			{
				break;
			}						
						
		}	// while
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		LinkedList<PlayerIP> players = new LinkedList();	// nick i ip z hamachi
		players.add(new PlayerIP("127.0.0.1"));
		players.add(new PlayerIP("25.155.87.12"));			
		
		//ExecutorService exec = Executors.newSingleThreadExecutor();
		//exec.execute(com);		
		//exec.shutdown();
		
		
		// inicjalizacja portu, wyslanie wiadomosci
		Communication com = new Communication("Sebastian",players);		
		
		// od razu odpalenie watka demona
		com.addPlayerIP(new PlayerIP("127.0.0.1"));		
						
		Thread.yield();
			
		
		/*
		Message m;
		
		try 
		{
			m = MessageFactory.createMessage(Message.Type.DICE, 6);
			com.send("Dominik", m);
			m = com.receive("Dominik");
			System.out.println(m.getType());
			
			if(m.getType()==Message.Type.DICE)
				System.out.println("Wynik rzutu: " + ((MsgDice)m).getContent() );
						
			
		} catch (ContentException e) 
		{
			System.out.println("Blednie ustawiona zawortosc wiadomosci!");		
		}	
		
		
		*/		
		
		
		
		
		
		
		// Tutaj musi coœ siê dziaæ by tamten w¹tek mia³ mo¿liwoœc zaktualizowania danych
		
		Thread.yield();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		System.out.println("");
		
		System.out.println("Collection PlayerIP:");
		for(PlayerIP p : players)
		{
			System.out.println(p.nickname + ": " + p.address + ", " + p.online);
		}
				
		System.out.println("");
		System.out.println("peers size: " + com.peers.size());
		
		
		
		

	}
	







}
