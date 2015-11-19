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
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import catan.network.FactoryProducer.FactoryType;
import catan.network.Message.Type;
import catan.network.SystemMessage.SystemType;

/**
 * Class which provide us P2P architecture and basic communication in an internal network.
 * @author Sebastian
 *
 */
public class Communication implements Runnable{			// jako singleton?
	
	
	
	class MessageHandler 
	{				
		// Communication.this
		Peer handleMsgPeer(Socket client, ObjectInputStream input, MsgPeer msg) throws IOException
		{
			Peer peer = null;
			synchronized(Communication.this)
			{
				// 	Accepting connection and adding to peer socketIn and InputStream		
				int i = 0;
				System.out.println("MessageHandler... MsgPeer");
			
				Iterator<PlayerIP> it = playersIP.iterator();
					
				while(it.hasNext())
				{
					PlayerIP p = it.next();
					// 	dopasowanie do moich peersow
					if(p.address.equals(client.getInetAddress().getHostAddress()))
					{
						peer = peers.get(p.nickname);		// tutaj jeszcze ma tymczasowa nazwe
						peer.socketIn = client;
						peer.input = input;
						String nick = msg.getContent();
						System.out.println("Otrzymana wiadomosc: " + nick);
						int j = 0;
						while(peers.containsKey(nick))					
							j++;						
					
						if(j>0)											// zabezpieczenie jak juz istenieje taki nick
							nick = nick + j;
						
						ipToNick.put(client.getInetAddress().getHostAddress(), nick);
						peers.remove(p.nickname);
						peers.put(nick, peer);
						System.out.println("Do peers dodano peer o kluczu: " + nick);
						
						//	send(nick, new MsgPeer(nickname));			// IOException
					
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
					
					peer = new Peer(nickname, ip, servport);
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
			return peer;
		}
		void handleMsgInvitation(Peer peer)
		{
			System.out.println("Dostales zaproszenie do gry od:" + ipToNick.get(peer.socketIn.getInetAddress().getHostAddress()) );
		}
		void handleMsgDice(Peer peer, MsgDice msg)
		{
			System.out.print("From: " + ipToNick.get(peer.socketIn.getInetAddress().getHostAddress()));
			System.out.println(" -- Wynik kosci:" + msg.getContent());
		}
	}						
	
	
	
	private Collection<PlayerIP>	playersIP;			
	private final String			nickname;
	private Map<String,Peer>		peers;				// nie sa zsynchronizowane
	private Map<String,String>		ipToNick;
	private ServerSocket			serv;
	final int						servport = 8080;	
	MessageHandler					msgHandler;
	//private ExecutorService 		exec = Executors.newCachedThreadPool();
	 	
	
			
	private void initServPort() throws IOException							// a co gdy jednak nam wywali blad? obs³uzyæ czy nie?
	{		
		serv = new ServerSocket(servport);
	}
	private synchronized void initPeers() throws IOException, ClassNotFoundException		// moze ustawic jakis timeout?
	{			
		System.out.println("inicjalizacja Peers ... (synchronized)");
		int i = 0;
		for(PlayerIP p : playersIP)
		{	
			try
			{
				p.nickname = Integer.toString(i);	// przypisane tymczasowej nazwy
				Peer newPeer = new Peer(nickname, p.getIp(), servport);
				peers.put(p.nickname , newPeer);		// proba polaczenia i wyslania wiadomosci z nickiem												
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
		ipToNick = new HashMap<String,String>();
		msgHandler = new MessageHandler();
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
	
		
		// stworzenie peers, nawiazanie polaczen, nasluchiwanie na nowe polaczenia 	
		System.out.println("Tworzenie nowego watka demona");
		Thread deamon = new Thread(this,"#Communication");
		deamon.setDaemon(true);
		deamon.start();
		
	}
	private void listenPort()
	{
		// nasluchiwanie
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
				if(msg.getType() == Type.SYSTEM)
					if(((SystemMessage)msg).getSubType()==SystemMessage.SystemType.PEER)
					{
						Peer newPeer = this.msgHandler.handleMsgPeer(client, input, (MsgPeer) msg);
												
						System.out.println("Tworzenie nowego watka demona receiver dla: " + ((MsgPeer)msg).getContent());						
						Thread deamon = new Thread(new Receiver(newPeer, this),"#" + ((MsgPeer)msg).getContent());
						deamon.setDaemon(true);
						deamon.start();
					}						
					else
						System.err.println("Przy palaczeniu zostal wyslany zly podtyp wiadomosci");
				else
					System.err.println("Przy palaczeniu zostal wyslany zly typ wiadomosci");
				
					
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
		Iterator<PlayerIP> it = playersIP.iterator();
		while(it.hasNext())
		{
			if(it.next().address.equals(p.address))
			{
				System.out.println("Istenieje juz taki PlayerIP");
				return;
			}
		}
		
		try
		{
			
			p.nickname = Integer.toString(-1);
			this.playersIP.add(p);	
			
			// na wypadek, gdyby dodanie bylo wczesniej niz ich inicjalizacja
			// jesli tak to dodaj tylko do playersIP a initPeers zrobi reszte
			if((peers.size()!=0 && playersIP.size()>1) || (playersIP.size()==1))
				peers.put(p.nickname , new Peer(nickname, p.getIp(), servport));		// proba polaczenia i wyslania wiadomosci z nickiem			
			System.out.println("dodano nowego gracza");
			
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
	public void sendTo(String nick, Message msg) throws IOException
	{
		peers.get(nick).send(msg);		
	}
	public void sendToAll(Message msg) throws IOException
	{
		Set<Entry<String, Peer>> entrySet = peers.entrySet();
		Iterator<Entry<String, Peer>> it = entrySet.iterator();
		while(it.hasNext())
		{
			sendTo(it.next().getKey(), msg);
		}
	}
	/**
	 * Method for reading messages from network, blocks if there is nothing
	 * @param nick Defines a node from which we want receive a message  
	 * @return Return a message
	 * @throws ClassNotFoundException 
	 * @throws IOException Whenever reading from an InputStream fails
	 */
	
	public void createGame(Collection<String> names) throws IOException
	{		
		AbstractMessageFactory sm = FactoryProducer.getFactory(FactoryType.SYSTEM);
		Message inv = null;
		try {
			inv = sm.getSystemMessage(SystemType.INVITATION, null);
		} catch (ContentException e) {
			e.printStackTrace();
		}
		
		for(String name : names)
		{
			sendTo(name, inv);
		}
	}
	
	
	
	/**
	 * Method which is necessary to run a thread and it is done in constructor, so don't run it!
	 */
	public void run() 
	{
		System.out.println(Thread.currentThread().getName() + ", playersIP.size: " + playersIP.size());		
		// inicjalizacji wszystkich peer. Wysy³anie od razu wiadomoœci o nicku
		try 
		{
			initPeers();								
		} 
		catch (Exception e1) {
			System.err.println("Problem z nas³uchiwaniem, odebraniem po³¹czenia");
			e1.printStackTrace();
		}											
		
		
		
		// Wypisanie diagnostyczne na standardowe wyjscie
		System.out.println("Wys³anie wiadomoœci online(PEER) do wszystkich graczy.");		
		System.out.println("");
		System.out.println("Collection PlayerIP");
		for(PlayerIP p : playersIP)
			System.out.print(p.nickname + ": " + p.address + "; ");							
		System.out.println("");
		
		
		// Nasluchiwanie portu, nowe polaczenia		
		listenPort();
		
		
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
			
		/*
		ExecutorService exec = Executors.newSingleThreadExecutor();
	
		exec.execute(com);		
		exec.shutdown();
		*/
		
		LinkedList<PlayerIP> players = new LinkedList();	// nick i ip z hamachi
		players.add(new PlayerIP("127.0.0.1"));
		//players.add(new PlayerIP("25.155.87.12"));			
		
		
		// inicjalizacja portu, wyslanie wiadomosci
		Communication com = new Communication("Sebastian",players);		
		
		// od razu odpalenie watka demona
		System.out.println("dodawanie nowego gracza ... (synchronized)");
		com.addPlayerIP(new PlayerIP("127.0.0.1"));	
		
						
		Thread.yield();
			
					
		// Tutaj musi coœ siê dziaæ by tamten w¹tek mia³ mo¿liwoœc zaktualizowania danych		
		Thread.yield();
		try 
		{
			System.out.println("");
			System.out.println("---" + Thread.currentThread().getName() + " sleep for 5 sec.");
			System.out.println("");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		System.out.println("---" + Thread.currentThread().getName() + " is awake.");
		System.out.println("");
		
		System.out.println("");
		
		System.out.println("Collection PlayerIP:");
		for(PlayerIP p : players)
		{
			System.out.println(p);
		}
		
		System.out.println("");
		System.out.println("ipToNick");		
		Set<Entry<String, String>> entrySet = com.ipToNick.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String, String> e = it.next();					
			System.out.print("ip: " + e.getKey() + ", nick: " + e.getValue() + "; ");
		}				
		System.out.println("");
		
		
				
		System.out.println("");
		System.out.println("peers size: " + com.peers.size());
		
						
		// Sending msg
		AbstractMessageFactory mf = FactoryProducer.getFactory(FactoryType.UPDATE);
		Message msg = null;
		try {
			msg = mf.getUpdateMessage(UpdateMessage.UpdateType.DICE, 6);
			System.out.println("Wysylanie wiadomosci DICE=6");
			com.sendTo("Sebastian", msg);			
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		
		
		//Thread.yield();
		try {
			System.out.println("");
			System.out.println("---" + Thread.currentThread().getName() + " sleep for 5 sec.");
			System.out.println("");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		System.out.println("---" + Thread.currentThread().getName() + " is awake.");
		System.out.println("");
		
	}
	
	

}
