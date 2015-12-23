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
import catan.network.FactoryProducer.FactoryType;
import catan.network.Message.Type;
import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;
import database.Board;
import database.Node;
import database.Tile;

/**
 * Class which provide us P2P architecture and basic communication in an internal network.
 * What is more, this class send messegas, create new threads for every peer and one for itself for listening servport.
 * @author Sebastian
 *
 */
public class Communication implements Runnable, P2P{
	
	private static class CommunicationHolder
	{
		private static final Communication instance = new Communication();
	}
		
		
	private String					nickname;										// my nickname
	private ServerSocket			serv;	
	private int						servport = 8080;
	private MessageHandler			msgHandler;										// to handling system messages
	
	
	public	Collection<String>		addresses;										// all nodes - addresses	
	public	Map<String,Peer>		peers = new HashMap<String,Peer>();				// online nodes, nawi¹zane po³¹czenia
	public	Map<String,String>		ipToNick = new HashMap<String,String>();					
	
	
	
	
	/*Tworzenie instancji*/	 	
	protected Communication(){}
	/**
	 * This is a proper way to get an instance of this class.
	 * @return Instance of this singleton.
	 */
	public static Communication getInstance()
	{
		return CommunicationHolder.instance;
	}
	/**
	 * Method to initialize Communication.
	 * @param myName Our nickname which is propagate into network.
	 * @param players Collection which contain all players in our database.
	 * @param board Board which will be modified during the game.
	 * @throws IOException When creating ServSocket fails.
	 */
	public void initCommunication(String myName, Collection<String> rememberedNodes, MessageHandler msgHandler) throws IOException 
	{
		System.out.println("----Communication initialization----");
		System.out.println("");
				
		addresses = rememberedNodes;		
		nickname = myName;			
		this.msgHandler = msgHandler;		
		
		try			
		{  
			initServPort();		
			System.out.println("ServSocket on port: " + serv.getLocalPort());
		}
		catch(IOException port)
		{
			System.err.println("B³¹d przy tworzeniu server socket.");			
			throw new IOException();
		}
		
		
		System.out.println("My Nickname: " + nickname);
		System.out.println("Nodes: " + addresses);		
																			 	
		System.out.println("@Deamon thread - #Communication");
		Thread deamon = new Thread(this,"#Communication");
		deamon.setDaemon(true);
		deamon.start();														// stworzenie peers, nawiazanie polaczen, nasluchiwanie na nowe polaczenia
		
	}
	
	/*Private*/
	private void 				initServPort() throws IOException							// a co gdy jednak nam wywali blad? obs³uzyæ czy nie?
	{		
		serv = new ServerSocket(servport);
	}
	private void				initPeers() throws IOException, ClassNotFoundException
	{			
		System.out.println();
		System.out.println("--Peers initialization ..."); 
		System.out.println();
		int i = 0;
		for(String p : addresses)
		{	
			try
			{
				Peer newPeer = new Peer(nickname, p, servport);		// proba polaczenia i wyslania wiadomosci z moim nickiem
																			// zapisanie inf o socketOut, Output objectStream
				
				i++;				
				peers.put(Integer.toString(i), newPeer);					// dodanie do mapy, gdzie przechowywane s¹ nawi¹zane po³¹czenia																			
			}
			catch(SocketTimeoutException e)
			{
				System.out.println("Problem z utworzeniem po³aczenia (timeout) z: " + p);					
			}
			catch(IOException e)
			{
				System.out.println("Problem z utworzeniem po³aczenia z: " + p);					
			}
			
			
		}		
		System.out.println("--Peers initialization finished");
	}	
	private void 				listenPort()
	{
		System.out.println("--Listening servport ...");
		System.out.println();
		
		// nasluchiwanie
		while(true)
		{
			try 
			{
				Socket client = serv.accept();
				System.out.println("New connection from: " + client.getInetAddress() + " : " + client.getPort());					
								
				
				// jezeli jest nowe polaczenie to odczytuje imie i tworze nowy watek dla niego
				
				ObjectInputStream input	= new ObjectInputStream(client.getInputStream());								
				Message msg	= (Message)input.readObject();																								
				
				if(msg.getType() == Type.SYSTEM)
					if(((SystemMessage)msg).getSubType()==SystemMessage.SystemType.PEER)
					{
						handleMsgPeer(client, input, (MsgPeer) msg);
																		
						String thread = ipToNick.get(client.getInetAddress().getHostAddress());
						
						System.out.println("@Deamon thread - " + "#" + thread);	
						System.out.println();
						
						
						Thread deamon = new Thread(msgHandler,("#" + thread));				// podmieniaæ na odpowiednie
						//Thread deamon = new Thread(new Receiver(newPeer, this),"#" + thread);				// podmieniaæ na odpowiednie
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

	private void				addAddress(String ip)
	{
		if(!addresses.contains(ip))
			addresses.add(ip);
	}
	private String				updateNickname(String nickname)
	{
		int j = 0;
		while(peers.containsKey(nickname))					
			j++;						

		if(j>0)										// zabezpieczenie jak juz istenieje taki nick
			nickname = nickname + j;
		
		return nickname;
	}
	private synchronized void 	handleMsgPeer(Socket client, ObjectInputStream input, MsgPeer msg)
	{
		// 	Accepting connection and adding to peer socketIn and InputStream		
					
			
		Iterator<String> it = peers.keySet().iterator();										
		while(it.hasNext())			// przejscie po wszystkich udanych nawiazanych polaczeniach
		{
			String nick = it.next();
			Peer peer = peers.get(nick);														// tutaj jeszcze ma stara nazwe
			String ip = peer.socketOut.getInetAddress().getHostAddress();
			
			if(ip.equals(client.getInetAddress().getHostAddress())) 				// jezeli output i input do tego samego ip
			{
					
				// dopisanie input do utworzonego wczesniej peera
				peer.socketIn = client;
				peer.input = input;
				
				
				
				String nickname = msg.getContent();
				
				nickname = this.updateNickname(nickname);	// przypisanie uniwersalnej nazwy w peersach					
				peers.remove(nick);							// aktualizacja peers, wczesniej nick = int
				peers.put(nickname, peer);
					
												
				System.out.println("Player's nickname: " + nickname);
				System.out.println("Adding ip to ipToNick ...");
				ipToNick.put(ip, nickname);								
				
				System.out.println("Peers updated succesfuly");
				
				return;
								
			}	
								
		}								
		
		
		// jezeli nie nawiazalem z nim jeszcze polaczenia, nie ma w peers, nie jest to odpowiedz zwrotna
		try
		{
			Peer newPeer = new Peer(nickname, client.getInetAddress().getHostAddress(), servport);	
			newPeer.socketIn = client;
			newPeer.input = input;
		
			String nickname = msg.getContent();
			nickname = this.updateNickname(nickname);
			String ip = newPeer.socketOut.getInetAddress().getHostAddress();
		
		
			peers.put(nickname , newPeer);							// dodanie do mapy, gdzie przechowywane s¹ nawi¹zane po³¹czenia
						
			System.out.println("Player's nickname: " + nickname);
			System.out.println("Adding ip to ipToNick ...");
			ipToNick.put(ip, nickname);
		
			System.out.println("Updating addresses collection (database)...");
			this.addAddress(ip);						
		
			System.out.println("peers updated succesfuly");
		}
		catch(IOException e)
		{
			System.err.println("Niemoznosc nawiazania polaczenia z nodem, ktory wlasnie do mnie napisal");
		}
		
	}		
	
	private synchronized void 	close() throws IOException
	{	
		System.out.println("closing...");
		serv.close();
		for(String p : peers.keySet())
		{
			peers.get(p).close();
		}
		
	}
	private void 				sleep(long time)
	{
		try 
		{
			Thread.sleep(time);
		} catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}		
	}
	
	
	/*Public*/	
	public String 				getNickname()
	{
		return this.nickname;
	}
	public int 					getServPort()
	{
		return this.servport;
	}
	/**
	 * Adding new NodeP2P to nodesP2P collection with adding nickname, creating new peer
	 * @param n New PlayerIP to add
	 */
	public synchronized void 	addNodeP2P(String address)
	{		
		Iterator<String> it = addresses.iterator();
		while(it.hasNext())
		{
			if(it.next().equals(address))
			{
				System.out.println("PlayerIP exists");
				return;
			}
		}
		
		try
		{
						
			this.addresses.add(address);	
			String nick = updateNickname(Integer.toString(0));
			
			// na wypadek, gdyby dodanie bylo wczesniej niz ich inicjalizacja
			// jesli tak to dodaj tylko do playersIP a initPeers zrobi reszte
			if((peers.size()!=0 && addresses.size()>1) || (addresses.size()==1))
				peers.put(nick , new Peer(nickname, address, servport));		// proba polaczenia i wyslania wiadomosci z nickiem			
			System.out.println("Adding new player finished");
			
		}
		catch(IOException e)
		{
			System.out.println("Problem z utworzeniem po³aczenia z: " + address);	
			addresses.remove(address);
		}
		
	}
	public synchronized void	removeNodeP2P(NodeP2P n)
	{
		// TODO
	}
	public synchronized void 	sendTo(String nick, Message msg) throws IOException		// send to peer not only in my game
	{
		// TODO Obs³uga b³edu
		peers.get(nick).send(msg);		
	}
	public void 				disconnected(String nick)		
	{
		Iterator<String> it = addresses.iterator();
		while(it.hasNext())
		{
			String p = it.next();
			if(p == nick)
			{				
				peers.remove(nick);
			}
		}
	} 	
		
	
	/**
	 * Method which is necessary to run a thread and it is done in initCommunication, so don't run it!
	 */
	public void run() 
	{
				
		// inicjalizacji wszystkich peer. Wysy³anie od razu wiadomoœci o nicku
		try 
		{
			initPeers();		// inicjalizacja z nodesP2P, mam peery w peers z SocketOut, Output, ip i nick pobrane z nodesP2P	
		} 
		catch (Exception e1) {
			System.err.println("Problem z nas³uchiwaniem, odebraniem po³¹czenia  -  initPeers");
			e1.printStackTrace();
		}											
		
			
		System.out.println();
		System.out.println("peers at the moment:");		
		for(String p : peers.keySet())
			System.out.println("Nick: " + p + ", Address: " + peers.get(p).socketOut.getInetAddress().getHostName());							
		System.out.println("");
		
						
		listenPort();																		// Nasluchiwanie portu, nowe polaczenia				
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
			
			
		LinkedList<String> players = new LinkedList();									// nick i ip z hamachi
		players.add("127.0.0.1");
			
//		Board board = Board.getInstance();													// na potrzeby handlu
//		board.loadMatrix();
//		board.setNeighbours();
	
		Communication com = Communication.getInstance();									// tworzenie komunikacji					
		com.initCommunication("Sebastian", players, new MessageHandler(com, null));
		
		com.sleep(5000);
		
		System.out.println("----Communication initialization finished----");
		System.out.println();		
		

		com.sleep(5000);																	// sleep
			
		
		System.out.println();
		System.out.println("peers at the moment:");		
		System.out.println("peers size: " + com.peers.size());								// wyswietl ilosc peers'ow		
		for(String p : com.peers.keySet())
			System.out.println("Nick: " + p + ", Address: " + com.peers.get(p).socketOut.getInetAddress().getHostName());							
		System.out.println();
		
		
		
		System.out.println();
		System.out.println("ipToNick:");														// wyswietl ipToNick
		Set<Entry<String, String>> entrySet = com.ipToNick.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String, String> e = it.next();					
			System.out.print("ip: " + e.getKey() + ", nick: " + e.getValue() + "; ");
		}				
		System.out.println("");
		
					
				
		
		
		/*

		// tworzenie gry
		LinkedList<String> invited = new LinkedList<String>();								// lista graczy zaproszonych
		invited.add("Sebastian");															// docelowo wybor myszka
	
		System.out.println("");
		System.out.println("--Sending invitations to: " + invited);							// wyswalenie zaproszen
		System.out.println("");
		com.sendInvitations(invited);
	
		com.sleep(5000);																	// sleep


		System.out.println();
		System.out.println("--Starting the game ...");
		System.out.println();
		System.out.println(com.invPlayers);	
		
		boolean game = com.startGame();														// start gry
		if(game == true)																	// jak sie nie uda to nie usuwa liste zaproszonych
			System.out.println("Welcome in Catan world!");
		else
			System.out.println("Starting game failed!");				
		
		com.sleep(3000);
		
		System.out.println("Place in the game: " + com.getPlace());							// numer w kolejce graczy 1-4
		
		*/
		/* Testing Update Messages */
		/*
		System.out.println();
		System.out.println("----Testing Update Messages----");
		System.out.println();
		
		Messenger msger = com;																// wysylanie wiadomosci
		msger.sendUpdate(board);															// Board Update
				
		msger.sendUpdate(board.getNode(5), 6);												// Node Update
		msger.sendUpdate(board.getTile(5), 5);												// Tile Update
		msger.sendUpdate(NumberOf.DICE, 5);													// Dice Update
		msger.sendUpdate(NumberOf.RESOURCES, 5);											// Resources Update
		
		com.sleep(5000);
		
		*/
		/* Testing Trade Messages */
		/*
		System.out.println();
		System.out.println("----Testing Trade Messages----");
		System.out.println();
 
		HashMap<String, Integer> give = new HashMap<String, Integer>();
		give.put("ore", 2);		
		HashMap<String, Integer> get = new HashMap<String, Integer>();
		get.put("wood", 2);
		
		
		System.out.println("Sending offert ...");
		System.out.println();
		
		msger.sendTrade(give, get);															// wyslanie oferty handlowej
				
		com.sleep(3000);																	// sleep
		
		msger.sendTrade("Sebastian");		
		
		com.sleep(2000);																	// sleep
		
		msger.sendTrade();																	// zakoncz handel
				
		msger.sendEnd(SystemType.END_GAME);													// Wysylanie wiadomosci typu END
		msger.sendEnd(SystemType.END_TURN);
		
		com.sleep(3000);																	// sleep
		
		System.out.println();
		System.out.println("Players: " + com.invPlayers);									// wyswietlenie statusu graczy w grze									
		System.out.println();
		
		System.out.println("all threads:");
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for(Thread t: threadSet)
		{
			System.out.println(t.getName());
		}
		*/
	}
	
	

}
