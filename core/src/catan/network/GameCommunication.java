package catan.network;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import catan.network.FactoryProducer.FactoryType;
import catan.network.GameCommunication.InvStatus;
import catan.network.SystemMessage.SystemType;

public class GameCommunication extends CommunicationDecorator implements Subject {
	
	public enum InvStatus
	{
		WAIT, ACCEPTED, REJECTED;
	}
	
		
	// game == groupe
	public GameCommunication(P2P decoratedP2P, String myName, Collection<String> rememberedNodes, MessageHandler msgHandler) throws IOException {
		super(decoratedP2P);		
		msgHandler.setDecorator(this);
		this.initCommunication(myName, rememberedNodes, msgHandler);
	}
	
	public boolean					inRealGame;
	private boolean					inGame=false;					// my value		
	protected Map<String, InvStatus>invPlayers = new HashMap<String, InvStatus>();				// <-- W grze: przechowuje nicki graczy bedacych ze mna w grze, ich TradeStatus																
	AbstractMessageFactory			system = FactoryProducer.getFactory(FactoryType.SYSTEM);   // 	Przed gra: przechowuje niki peerow i ich odpowiedzi na moje zaproszenie
	
	private List<Observer> 			observers = new LinkedList<Observer>();

	
	public void putInv(String nick, InvStatus status)
	{
		invPlayers.put(nick, status);
		this.notifyObservers();
	}
	public void removeInv(String nick)
	{
		invPlayers.remove(nick);
		this.notifyObservers();
	}
	
	public void setInGame(boolean state)
	{
		this.inGame = state;
	}
	public boolean getInGame()
	{
		return inGame;
	}
	public void sendToAll(Message msg)										// send to everyone in invPlayers, ---> Mozna dodac wzorzec w zaleznosci od warunku <---
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
				disconnected(e.getKey());		// usuniecie z peersow				
			}
			
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
			}
		}
		inGame = true;																		// jak sam tworze gre to w niej jestem :P
		System.out.println("Invitations sended, Players status:");
		System.out.println(invPlayers);
		System.out.println();
	}
	public void sendInvitationAnswer(String nick, SystemType what)
	{
		Message msg = null;
		try {
			if(what==SystemType.ACCEPT)
			{
				msg = system.getSystemMessage(SystemType.ACCEPT, null);
				setInGame(true);
			}else
			{
				msg = system.getSystemMessage(SystemType.REJECT, null);				
			}
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
	public boolean checkNumberOfPlayers(int minnumber, int maxnumber)
	{
		Set<Entry<String, InvStatus>> entrySet = invPlayers.entrySet();
		Iterator<Entry<String, InvStatus>> it = entrySet.iterator();
		Entry<String, InvStatus> e = null;
		
		int i = 0 ;				
		while(it.hasNext())
		{
			e = it.next();
			if(e.getValue()==InvStatus.ACCEPTED)
				i++;
		}		
		// sprawdzenie ilosci graczy
		if(i<minnumber || i>maxnumber)
		{
			System.out.println("Wrong number of players");
			return false;
		}
		else return true;
	}
	/**
	 * Creating a game. Whenever number of players is wrong it fails. 
	 * In case of success this method sends Start_Game message to all who accepted, to the rest abandon.
	 * @return boolean value, false - starting game failed, true - starting game succeed
	 */
	synchronized public boolean startGame(int minnumber, int maxnumber)												// synchronizowane, by nikt w tej chwili nie przyjal zaproszenia...
	{
		Set<Entry<String, InvStatus>> entrySet = invPlayers.entrySet();
		Iterator<Entry<String, InvStatus>> it = entrySet.iterator();
		LinkedList<String> toRemove = new LinkedList<String>();
		Entry<String, InvStatus> e = null;
		
						
		if(!this.checkNumberOfPlayers(minnumber, maxnumber))
			return false;
		
		Set<String> names = invPlayers.keySet();
		Set<String> addresses = new HashSet<String>();
		
		for(String n : names)
			addresses.add(this.decoratedP2P.getIpFromNick(n));
			
		
		it = entrySet.iterator();
		while(it.hasNext())
		{
			e = it.next();
			if(e.getValue() == InvStatus.ACCEPTED)
			{
				try {
					sendTo(e.getKey(), system.getSystemMessage(SystemType.START_GAME, addresses));
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
				}
			}else	toRemove.add(e.getKey());
				
		}
		
		for(String nick : toRemove)
			invPlayers.remove(nick);
		
		this.inRealGame=true;
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
					e1.printStackTrace();
				}
			}			
				
		}
		inGame = false;
		invPlayers.clear();
		System.out.println("Gra porzucona ...");
	}

	
	void sendInvList()
	{		
		Map<String, InvStatus> invPlayersSend = new HashMap<String, InvStatus>();
		for(String name : invPlayers.keySet())
		{
			invPlayersSend.put(getIpFromNick(name),invPlayers.get(name));
		}
		
		
		Message msg = null;
		try {
			msg = system.getSystemMessage(SystemType.INV_LIST, invPlayersSend);
		} catch (ContentException e) { 
			e.printStackTrace();
		}
		
		for(String name : invPlayers.keySet())
		{
			if(this.invPlayers.get(name)==InvStatus.ACCEPTED)
				try {
					sendTo(name, msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
	
	
	@Override
	public void add(Observer observer) {
		if(observer.getClass()==ObserverPeers.class)
			((Communication)this.decoratedP2P).add(observer);
		else
			this.observers.add(observer);
	}
	@Override
	public void remove(Observer observer) {
		this.observers.remove(observer);
		
	}
	@Override
	public void notifyObservers() {
		for(Observer ob : observers)
		{
			ob.update();
		}
				
	}
	public Map<String, InvStatus> getStateInv()
	{
		return invPlayers;
	}
	public Set<String> getStatePeers()
	{
		return ((Communication)this.decoratedP2P).getStatePeers();		
	}

	public void disconnected(String nick)
	{
		super.disconnected(nick);
		this.invPlayers.remove(nick);
	}
	
	
}
