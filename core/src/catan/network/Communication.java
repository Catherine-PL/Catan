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
public class Communication implements Runnable, Messenger{
	
	private static class CommunicationHolder
	{
		private static final Communication instance = new Communication();
	}
	enum InvStatus
	{
		WAIT, ACCEPTED, REJECTED;
	}
	
	class MessageHandler 									/* Dodac metody ktore beda wywolywac podmiane informacji na nowe */
	{														/* Tylko jeden receiver na raz moze wykonac jakas metode */
		private Board board;
		private HashMap<String, Integer> give;
		private HashMap<String, Integer> get;
		private int myNumber;
		private int inQueue = 0;		// ilosc ludzi przede mna w grze
		private int msgdice = 0;
		
		MessageHandler(Board board)
		{
			this.board = board;
		}
		
		/* SystemMessage */
		synchronized Peer handleMsgPeer(Socket client, ObjectInputStream input, MsgPeer msg)
		{
			Peer peer = null;
			synchronized(Communication.this)
			{
				// 	Accepting connection and adding to peer socketIn and InputStream		
				int i = 0;			
				Iterator<PlayerIP> it = playersIP.iterator();
					
				while(it.hasNext())
				{
					PlayerIP p = it.next();
																											// 	dopasowanie do moich peersow
					if(p.address.equals(client.getInetAddress().getHostAddress()))
					{
						peer = peers.get(p.nickname);														// tutaj jeszcze ma tymczasowa nazwe
						peer.socketIn = client;
						peer.input = input;
						String nick = msg.getContent();						
						int j = 0;
						while(peers.containsKey(nick))					
							j++;						
					
						if(j>0)																				// zabezpieczenie jak juz istenieje taki nick
							nick = nick + j;
						
						System.out.println("Player's nickname: " + nick);
						System.out.println("Adding ip to ipToNick ...");
						System.out.println("Adding to peers HashMap ...");						
						ipToNick.put(client.getInetAddress().getHostAddress(), nick);
						peers.remove(p.nickname);
						peers.put(nick, peer);
												
					
						System.out.println("Updating playersIP collection ...");
						p.nickname = nick;
						p.online = true;
						it.remove();
						playersIP.add(p);
						break;
					}	
					i++;						
				}																							// Jezeli obcy dla mnie gosc
				if(i == playersIP.size())
				{			
					String ip = client.getInetAddress().getHostAddress();
					PlayerIP pip = new PlayerIP(ip);
					
					try {
						peer = new Peer(nickname, ip, servport);
					} catch (IOException e) {
						System.out.println("Niemoznosc utworzenia polaczenia dla obcego ip");
						return null;
					}
					peer.input = input;
					peer.socketIn = client;
					
					String nick = msg.getContent();
					int j = 0;
					while(peers.containsKey(nick))					
						j++;						
				
					if(j>0)																				// zabezpieczenie jak juz istenieje taki nick
						nick = nick + j;
					
					System.out.println("Player's nickname: " + nick);
					System.out.println("Adding ip to ipToNick ...");
					ipToNick.put(client.getInetAddress().getHostAddress(), nick);
					
					System.out.println("Adding to peers HashMap ...");						
					peers.put(nick, peer);
					
					System.out.println("Updating playersIP collection ...");
					pip.nickname = nick;
					pip.online = true;
					playersIP.add(pip);										
										
				}
				
			}
			return peer;
		}
		synchronized void handleMsgInvitation(Peer peer)													/* trzeba to jakos zgrac, wybor:  accept, reject */
		{
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());			
			
									// ten fragment tylko wystepuje w testowaniu lokalnym
			if(inGame == true && !peer.socketIn.getInetAddress().getHostAddress().equals("127.0.0.1"))		// jezeli dostalem obce zaproszenie 
			{
				Message msg = null;
				try {					
					msg = system.getSystemMessage(SystemType.REJECT, null);
				} catch (ContentException e) {
					e.printStackTrace();
				}
				
				try {
					sendTo(nick, msg);
				} catch (IOException e) {
					System.err.println("MsgInvitation error, problem with sendTo");
					e.printStackTrace();
				}
				return ;
			}
			
			
			
			System.out.println("Invitation to a game from: " +  nick);
			
			// Tutaj musi nastapic wybor accepet albo reject wyslanie widomosci				<-----			
			
			Message msg = null;
			try {
				msg = system.getSystemMessage(SystemType.ACCEPT, null);
				inGame = true;
			} catch (ContentException e) {
				e.printStackTrace();
			}
			
			try {
				sendTo(nick, msg);
			} catch (IOException e) {
				System.err.println("MsgInvitation error, problem with sendTo");
				e.printStackTrace();
			}
			
		}
		synchronized void handleMsgAccept(Peer peer)					// dodac zmienna, ze j
		{
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
			System.out.println("Player: " +  nick + " - Accepted");
			
			invPlayers.remove(nick);
			invPlayers.put(nick, InvStatus.ACCEPTED);
			
			System.out.println(invPlayers);
			
		}
		synchronized void handleMsgReject(Peer peer)
		{
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
			System.out.println("Player: " +  nick + " - Rejected");
			
			invPlayers.remove(nick);
			invPlayers.put(nick, InvStatus.REJECTED);
			
			System.out.println(invPlayers);
		}
		synchronized void handleMsgStartGame(Peer peer)				// dodac metode startu gry jako takiej, ustawiæ zmienna inGame czy cos
		{
			System.out.println("The game starting ...");
			System.out.println("Sending result of my dice (creating a chain)");
			myNumber = 5;
			try {
				Message ms = update.getUpdateMessage(UpdateType.DICE, myNumber);
				sendToAll(ms);
			} catch (ContentException e) { 
				e.printStackTrace();
			}
			
			
		
		}
		synchronized void handleMsgAbandon(Peer peer)				// 
		{
			System.out.println("The game has been abandoned ...");
			inGame = false;
		}
		synchronized void handleMsgEndTurn(Peer peer)
		{
			String nick = ipToNick.get(peer.socketOut.getInetAddress().getHostAddress());
			System.out.println("Player: " + nick + " has finished turn.");
		}
		synchronized void handleMsgEndGame(Peer peer)
		{
			String nick = ipToNick.get(peer.socketOut.getInetAddress().getHostAddress());
			System.out.println("Player: " + nick + " has won the game.");
		}
		
		/* UpdateMessage */
		synchronized void handleMsgDice(Peer peer, MsgDice msg)
		{			
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
			System.out.print("From: " + nick);
			System.out.println(" -- Dice result:" + msg.getContent());
			
			if(msgdice < invPlayers.size()-1)									// tylko do ustalenia kolejnosci
			{
				msgdice++;
				invPlayers.remove(nick);
				invPlayers.put(nick, InvStatus.WAIT);							// jezeli wszyscy sa wait, to kolejnosc ustalona
				
				if(msg.getContent() > myNumber)
				{
					inQueue++;
				}else if(msg.getContent() == myNumber)
				{
					if(nickname.compareToIgnoreCase(nick) > 0)
					{
						inQueue++;
					}
				}
			}
		}
		synchronized void handleMsgBoard(MsgBoard msg)			// podmienic board tam gdzie jest ona przechowywana
		{
			System.out.println("Board actualization ...");
			//this.board = msg.getContent();
		}	
		synchronized void handleMsgNode(MsgNode msg)			// problem z aktualizacja
		{
			System.out.println("Node actualization ...");
			/*
			Node n = msg.getContent();			
			int i = msg.getIndex();
			System.out.println("Node: " + n + " on index: " + i);
			board.setNode(n, i);
			System.out.println(n.getNodeNumber() + ", " + board.getNode(i));
			*/
		}
		synchronized void handleMsgTile(MsgTile msg)
		{
			System.out.println("Tail actualization ...");
			/*
			Tile t = msg.getContent();
			int i = msg.getIndex();
			board.setTile(t, i);
			*/
			
		}
		synchronized void handleMsgResources(Peer peer, MsgResources msg)
		{
			System.out.println("Resources number actualization...");
			
			int n = msg.getContent();			
			System.out.println("Player: " + ipToNick.get(peer.socketIn.getInetAddress().getHostAddress()) + ", number of resources : " + n);
			
		}
		
		/* TradeMessage */
		synchronized void handleMsgOffert(Peer peer, MsgOffert msg)								/* podobnie jak z zaproszeniami */
		{			
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
			System.out.println();
			System.out.println("--Offert from: " + nick);
			
			get = msg.getGive();
			give = msg.getGet();
			//HashMap<String, Integer> icanget = msg.getGive();
			//HashMap<String, Integer> ihavetogive = msg.getGet();						
			
			System.out.println("What he wants: " + give);
			System.out.println("What i would get: " + get);
			System.out.println();
			
			// Sprawdzenie czy mogê siê zgodziæ. Jeœli nie to wysy³am od razu no.
			//Message ms = trade.getTradeMessage(TradeType.NO);
			Message ms = trade.getTradeMessage(TradeType.YES);
			
			try {
				sendTo(nick, ms);
			} catch (IOException e) {
				System.err.println("Utracono polaczenie z: " + nick);
				disconnected(nick);				
			}
			
		}
		synchronized void handleMsgYes(Peer peer, MsgYes msg)
		{
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
			System.out.println("Player: " + nick + " has accepted your offert");
					
			invPlayers.remove(nick);
			invPlayers.put(nick, InvStatus.ACCEPTED);
			
			System.out.println("Players" + invPlayers);			
		}
		synchronized void handleMsgNo(Peer peer, MsgNo msg)
		{
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());			
			System.out.println("Player " + nick + " has rejected your offert");			
					
			invPlayers.remove(nick);
			invPlayers.put(nick, InvStatus.REJECTED);
			System.out.println(invPlayers);
			
		}
		synchronized void handleMsgDeal(Peer peer, MsgDeal msg)
		{
			String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());			
			System.out.println("Deal with: " + nick);	
			
			/*	aktualizacja surowcow gracza. Dodajac surowce z get, Odejmujac surowce z give	*/
		}
		synchronized void handleMsgEndTrade(MsgEndTrade msg)
		{			
			Set<String> s = invPlayers.keySet();
			for(String nick : s)
			{
				invPlayers.remove(nick);
				invPlayers.put(nick, InvStatus.WAIT);
			}
		}
		
	}						
	
		
	
	private Collection<PlayerIP>	playersIP;					// wszyscy gracze online i offline
	private String					nickname;
	private Map<String,Peer>		peers;						// wszyscy online, w sieci
	private Map<String,String>		ipToNick;
	private ServerSocket			serv;
	private boolean					inGame;
	final int						servport = 8080;	
	
	MessageHandler					msgHandler;
	Map<String, InvStatus>			invPlayers;				// <-- W grze: przechowuje nicki graczy bedacych ze mna w grze, ich TradeStatus
	AbstractMessageFactory			update;					//  Przed gra: przechowuje niki peerow i ich odpowiedzi na moje zaproszenie
	AbstractMessageFactory			trade;
	AbstractMessageFactory			system;
	
	
	
	
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
	public void initCommunication(String myName, Collection<PlayerIP> players, Board board) throws IOException 
	{
		System.out.println("----Communication initialization----");
		System.out.println("");
				
		playersIP = players;		
		nickname = myName;			
		peers = new HashMap<String,Peer>();
		ipToNick = new HashMap<String,String>();
		msgHandler = new MessageHandler(board);
		invPlayers = new HashMap<String,InvStatus>();
		inGame = false;
		
		update = FactoryProducer.getFactory(FactoryType.UPDATE);
		system = FactoryProducer.getFactory(FactoryType.SYSTEM);
		trade = FactoryProducer.getFactory(FactoryType.TRADE);
		
		
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
		
		System.out.println("nickname: " + nickname);
		System.out.println("players: " + nickname);
		System.out.println("inGame: " + inGame);
	
																				 	
		System.out.println("@Deamon thread - #Communication");
		Thread deamon = new Thread(this,"#Communication");
		deamon.setDaemon(true);
		deamon.start();														// stworzenie peers, nawiazanie polaczen, nasluchiwanie na nowe polaczenia
		
	}
	
	/*Private*/
	private void initServPort() throws IOException							// a co gdy jednak nam wywali blad? obs³uzyæ czy nie?
	{		
		serv = new ServerSocket(servport);
	}
	private synchronized void initPeers() throws IOException, ClassNotFoundException
	{			
		System.out.println();
		System.out.println("--Peers initialization ..."); 
		System.out.println();
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
			}
			
			i++;
		}		
		System.out.println("--Peers initialization finished");
	}	
	private void listenPort()
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
								
				ObjectInputStream input	= new ObjectInputStream(client.getInputStream());								
				Message msg	= (Message)input.readObject();								
				
							
				if(msg.getType() == Type.SYSTEM)
					if(((SystemMessage)msg).getSubType()==SystemMessage.SystemType.PEER)
					{
						Peer newPeer = this.msgHandler.handleMsgPeer(client, input, (MsgPeer) msg);
						if(newPeer == null)
							continue;
												
						String thread = ipToNick.get(client.getInetAddress().getHostAddress());
						
						System.out.println("@Deamon thread - " + "#" + thread);	
						System.out.println();
						
						Thread deamon = new Thread(new Receiver(newPeer, this),"#" + thread);
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
	synchronized private void sendTo(String nick, Message msg) throws IOException		// send to peer not only in my game
	{
		peers.get(nick).send(msg);		
	}
	private void sendToAll(Message msg)										// send to everyone in invPlayers, ---> Mozna dodac wzorzec w zaleznosci od warunku <---
	{
		Set<Entry<String, InvStatus>> entrySet = invPlayers.entrySet();
		Iterator<Entry<String, InvStatus>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String, InvStatus> e = it.next();		
			try
			{
				sendTo(e.getKey(), msg);
			}
			catch (IOException ex) 
			{
				System.err.println("Utracono polaczenie z: " + e.getKey());
				ex.printStackTrace();
				disconnected(e.getKey());
				invPlayers.remove(e.getKey());
			}
			
		}
	}
	private synchronized void close() throws IOException
	{	
		System.out.println("closing...");
		serv.close();
		for(String p : peers.keySet())
		{
			peers.get(p).close();
		}
		
	}
	
	/*Package*/	
 	void disconnected(String nick)					// nadanie atrybutu na false, usuniecie z peersow
	{
		Iterator<PlayerIP> it = playersIP.iterator();
		while(it.hasNext())
		{
			PlayerIP p = it.next();
			if(p.nickname == nick)
			{
				p.online = false;		
				peers.remove(nick);
			}
		}
	}
 	void sleep(long time)
	{
		try 
		{
			//System.out.println("");
			//System.out.println("//" + Thread.currentThread().getName() + " sleep for " + time + " milisec.");
			//System.out.println("");
			Thread.sleep(time);
		} catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		
		//System.out.println("");
		//System.out.println("---" + Thread.currentThread().getName() + " is awake.");
		//System.out.println("");
	}
	
	/*Public*/		
	/**
	 * Adding new PlayerIP to PlayerIP collection with adding nickname, creating new peer
	 * @param p New PlayerIP to add
	 */
	public synchronized void addPlayerIP(PlayerIP p)
	{		
		Iterator<PlayerIP> it = playersIP.iterator();
		while(it.hasNext())
		{
			if(it.next().address.equals(p.address))
			{
				System.out.println("PlayerIP exisists");
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
			System.out.println("Adding new player finished");
			
		}
		catch(IOException e)
		{
			System.out.println("Problem z utworzeniem po³aczenia z: " + p.getIp());	
			playersIP.remove(p);
		}
		
	}
	/**
	 * Method needed to send invitations to players.	
	 * @param names Chosen players nicknames 
	 */
	public void sendInvitations(Collection<String> names)
	{				
		if(inGame == true)																	// jezeli juz przyjalem jedno zaproszenie
			return;
				
		Message inv = null;
		try {
			inv = system.getSystemMessage(SystemType.INVITATION, null);
		} catch (ContentException e) {
			e.printStackTrace();
		}
		
		for(String name : names)
		{			
			invPlayers.put(name, InvStatus.WAIT);
			try {
				sendTo(name, inv);
			} catch (IOException e) {
				System.err.println("Utracono polaczenie z: " + name);
				disconnected(name);
				invPlayers.remove(name);
			}
		}
		inGame = true;																		// jak sam tworze gre to w niej jestem :P
		System.out.println("Invitations sended, Players status:");
		System.out.println(invPlayers);
	}
	/**
	 * Creating a game. Whenever number of players is wrong it fails. 
	 * In case of success this method sends Start_Game message to all who accepted, to the rest abandon.
	 * @return boolean value, false - starting game failed, true - starting game succeed
	 */
	synchronized public boolean startGame()												// synchronizowane, by nikt w tej chwili nie przyjal zaproszenia...
	{
		Set<Entry<String, InvStatus>> entrySet = invPlayers.entrySet();
		Iterator<Entry<String, InvStatus>> it = entrySet.iterator();
		LinkedList<String> toRemove = new LinkedList<String>();
		Entry<String, InvStatus> e = null;
		int i = 0 ;
		
		while(it.hasNext())
		{
			e = it.next();
			if(e.getValue()==InvStatus.ACCEPTED)
				i++;
		}
		
		// sprawdzenie ilosci graczy
		if(i<3 || i>4)
		{
			System.out.println("Wrong number of players");
			return false;
		}
						
		
		
		
		it = entrySet.iterator();
		while(it.hasNext())
		{
			e = it.next();
			if(e.getValue() == InvStatus.ACCEPTED)
			{
				try {
					sendTo(e.getKey(), system.getSystemMessage(SystemType.START_GAME, null));
				} catch (ContentException e1) {					
					e1.printStackTrace();
				} catch (IOException e1) {
					System.err.println("Utracono polaczenie z: " + e.getKey());
					disconnected(e.getKey());
					invPlayers.remove(e.getKey());
				}
			}
			else if(e.getValue() == InvStatus.WAIT)
			{
				try {
					sendTo(e.getKey(), system.getSystemMessage(SystemType.ABANDON, null));
					toRemove.add(e.getKey());
				} catch (ContentException e1) {
					e1.printStackTrace();
				}catch (IOException e1) {
					System.err.println("Utracono polaczenie z: " + e.getKey());
					disconnected(e.getKey());
					invPlayers.remove(e.getKey());
				}
			}else	toRemove.add(e.getKey());
				
		}
		
		for(String nick : toRemove)
			invPlayers.remove(nick);
		
		return true;
		
		
	}
	/**
	 * Useful when you have sent invitations, but you don't want to create a game.
	 * This method sends to all invited players abandon message.
	 */
	public void abandonGame()
	{
		
		Set<Entry<String, InvStatus>> entrySet = invPlayers.entrySet();
		Iterator<Entry<String, InvStatus>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String, InvStatus> e = it.next();
			if(e.getValue() != InvStatus.REJECTED)
			{
				try {
					sendTo(e.getKey(), system.getSystemMessage(SystemType.ABANDON, null));
				} catch (IOException e1) {
					System.err.println("Utracono polaczenie z: " + e.getKey());
					disconnected(e.getKey());					
					
				} catch (ContentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
				
		}
		inGame = false;
		invPlayers.clear();
		System.out.println("Gra porzucona ...");
	}	
	/**
	 * After starting a game, all players send dice result.
	 * If we have all results, we know  sequence of players. 
	 * @return number is positive or equal 0.
	 * Number says which is our place, but when number is equal 0 that means,
	 * not everyone has sent me a result and it was impossible to create sequence. 
	 */
	
	public int getPlace()																// zwraca 0 jezeli jeszcze nie ustalono kolejnosci
	{
		Set<String> s = invPlayers.keySet();
		int i = 0;
		for(String nick : s)
		{
			if(invPlayers.get(nick) == InvStatus.WAIT)
				i++;
		}
		if(invPlayers.size() == i)								// wszyscy WAIT -> znam kolejnosc
			return (msgHandler.inQueue + 1);
		else
			return 0;
	}
	/**
	 * It should be a first message for a host of this game.
	 * This method propagate our board to all players.
	 * @param board Board to propagate.
	 */
	public void sendUpdate(Board board)
	{	
		Message msg = null;
		try {
			msg = this.update.getUpdateMessage(UpdateType.BOARD, board);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);				
	}	
	/**
	 * Method useful to update a tile. It will be necessary in case of thief moving.  
	 * @param tile Tile object which has been modified. 
	 * @param index Index of this tile in our board.
	 */
	public void sendUpdate(Tile tile, int index) 
	{
		Message msg = null;
		try {			
			msg = this.update.getUpdateMessage(UpdateType.TILE, tile, index);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);		
	}	
	/**
	 * Sends update message to all players. Needed whenever some node of our graph has been changed. 
	 * Useful after building new city, road etc. 
	 * @param node Node object which has been modified.
	 * @param index Index of this node in our board.
	 */
	public void sendUpdate(Node node, int index) 
	{
		Message msg = null;
		try {			
			msg = this.update.getUpdateMessage(UpdateType.NODE, node, index);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);	
	}
	/**
	 * Everyone has to know how many resource card i have. (Thief) 
	 * @param what It might be DICE or RESOURCE
	 * @param quantity Number of what parameter.
	 */
	public void sendUpdate(NumberOf what, int quantity)			// DICE, RESOURCE 
	{
		Message msg = null;
		try {			
			if(what.toString().equals(UpdateType.DICE.toString()))
				msg = this.update.getUpdateMessage(UpdateType.DICE, quantity);
			else
				msg = this.update.getUpdateMessage(UpdateType.RESOURCES, quantity);
		} catch (ContentException e) {
			e.printStackTrace();
		}		
		sendToAll(msg);	
	}
	
	/**
	 * Sends to all players my offert.
	 * @param give HashMap which contains names of resources and their quantity. This resources we are giving to somebody.
	 * @param give HashMap which contains names of resources and their quantity. This resources we are getting from somebody.
	 */
	public void sendTrade(HashMap<String, Integer> give, HashMap<String, Integer> get) 
	{
		for(String name : invPlayers.keySet())
		{
			invPlayers.remove(name);
			invPlayers.put(name, InvStatus.WAIT);
		}		
		System.out.println("Players: " + invPlayers);													
		
		
		Message msg = null;
		msg = this.trade.getTradeMessage(TradeType.OFFERT, give, get);			
			
		
		Set<Entry<String, InvStatus>> entrySet = invPlayers.entrySet();
		Iterator<Entry<String, InvStatus>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String, InvStatus> e = it.next();		
			try
			{
				if(e.getValue() != InvStatus.REJECTED)
					sendTo(e.getKey(), msg);
			}
			catch (IOException ex) 
			{
				System.err.println("Utracono polaczenie z: " + e.getKey());
				ex.printStackTrace();
				disconnected(e.getKey());
				invPlayers.remove(e.getKey());
			}
			
		}
		
		
	}		
	/**
	 * Whenever a player accepts our offer, then we can make this transaction true by sending deal message.
	 * @param nick Player's nickname who has accepted our offer. 
	 */
	public void sendTrade(String nick) 
	{
		if(invPlayers.get(nick) != InvStatus.ACCEPTED)
		{
			System.out.println("Player: " + nick + " hasn't accepted your offert.");
			return;
		}
		Message ms = trade.getTradeMessage(TradeType.DEAL);
		
		try {
			sendTo(nick, ms);
		} catch (IOException e) {
			System.err.println("Utracono polaczenie z: " + nick);			
			disconnected(nick);
			invPlayers.remove(nick);
			e.printStackTrace();
		}
		
		Set<String> s = invPlayers.keySet();
		s.remove(nick);
		invPlayers.put(nick, InvStatus.WAIT);
		
		ms = trade.getTradeMessage(TradeType.END_TRADE);
		for(String name : s)
		{
			try {
				sendTo(name, ms);
				invPlayers.remove(name);
				invPlayers.put(name, InvStatus.WAIT);
			} catch (IOException e) {
				System.err.println("Utracono polaczenie z: " + name);			
				disconnected(name);
				invPlayers.remove(name);
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Closes trade.
	 */
	public void sendTrade()
	{
		System.out.println("--Closing trade--");
		Set<String> s = invPlayers.keySet();		
		Message ms = trade.getTradeMessage(TradeType.END_TRADE);
		for(String name : s)
		{
			try {
				sendTo(name, ms);
				invPlayers.remove(name);
				invPlayers.put(name, InvStatus.WAIT);
			} catch (IOException e) {
				System.err.println("Utracono polaczenie z: " + name);			
				disconnected(name);
				invPlayers.remove(name);
				e.printStackTrace();
			}
		}		
	}
	/**
	 * Sends END_GAME or END_TURN message to all players.
	 * @param type Type of our ending. 
	 */
	public void sendEnd(SystemType type) 
	{
		Message ms = null;
		if(type == SystemType.END_GAME)
			try {
				ms = system.getSystemMessage(SystemType.END_GAME, null);				
			} catch (ContentException e) {
				e.printStackTrace();
			}
		else if(type == SystemType.END_TURN)
			try {
				ms = system.getSystemMessage(SystemType.END_TURN, null);
			} catch (ContentException e) {
				e.printStackTrace();
			}
		else
			System.err.println("Wrong type of end message");
		
		sendToAll(ms);
		
	}
	
	/**
	 * Method which is necessary to run a thread and it is done in initCommunication, so don't run it!
	 */
	public void run() 
	{
				
		// inicjalizacji wszystkich peer. Wysy³anie od razu wiadomoœci o nicku
		try 
		{
			initPeers();								
		} 
		catch (Exception e1) {
			System.err.println("Problem z nas³uchiwaniem, odebraniem po³¹czenia  -  initPeers");
			e1.printStackTrace();
		}											
		
			
		System.out.println();
		System.out.println("Collection PlayerIP at the moment");
		for(PlayerIP p : playersIP)
			System.out.println(p.nickname + ": " + p.address + "; ");							
		System.out.println("");
		
						
		listenPort();																		// Nasluchiwanie portu, nowe polaczenia				
	}
	
	
	static void main(String[] args) throws IOException, ClassNotFoundException {
			
			
		LinkedList<PlayerIP> players = new LinkedList();									// nick i ip z hamachi
		players.add(new PlayerIP("127.0.0.1"));
			
		Board board = Board.getInstance();													// na potrzeby handlu
		board.loadMatrix();
		board.setNeighbours();
	
		Communication com = Communication.getInstance();									// tworzenie komunikacji					
		com.initCommunication("Sebastian", players, board);
		
		com.sleep(5000);
		
		System.out.println("----Communication initialization finished----");
		System.out.println();		
		
		/*System.out.println("Adding new player ... (synchronized)");							// dodawanie nowego gracza
		com.addPlayerIP(new PlayerIP("127.0.0.1"));	
		*/
		
		com.sleep(5000);																	// sleep
		
					
		System.out.println("Collection PlayerIP:");											// wyswietl playersIP
		for(PlayerIP p : players)
		{
			System.out.println(p);
		}
		
		
		
		System.out.println("");
		System.out.println("ipToNick");														// wyswietl ipToNick
		Set<Entry<String, String>> entrySet = com.ipToNick.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while(it.hasNext())
		{
			Entry<String, String> e = it.next();					
			System.out.print("ip: " + e.getKey() + ", nick: " + e.getValue() + "; ");
		}				
		System.out.println("");
		
					
				
		System.out.println("");
		System.out.println(com.peers);
		System.out.println("peers size: " + com.peers.size());								// wyswietl ilosc peers'ow
								

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
		
		
		/* Testing Update Messages */
		
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
		
		
		/* Testing Trade Messages */
		
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
	
	}
	
	

}
