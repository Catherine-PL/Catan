package catan.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Communication implements Runnable {

	private Player[]			players;
	private Map<String,Peer>	peers;
	private ServerSocket		serv;
	private int					servport = 8080;
	private int					port = 8080;
	
		
	private void initServPort() throws IOException
	{
		serv = new ServerSocket(servport);
	}
	private void initPeers(Player[] players) throws IOException	// dodaæ oczekiwanie i ewentualnie zmiana portu na wy¿szy	
	{
		
		for(Player p : players)
		{			
			for(int i=0; i<5; i++)
			{
				try
				{
					peers.put(p.getNickname(), new Peer(p.getNickname(), p.getIp(), port+i));	// stworzenie peera i przypisanie socketa
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
		
		Set set = peers.entrySet();
		Iterator it = set.iterator();
		
		for(int i=0; i<players.length; i++)
		{
			Socket client = serv.accept();
					
			System.out.println("zakaceptowano polaczenie, które przysz³o z: " + client.getInetAddress() + " : " + client.getPort());
			System.out.println("Aktualnie przypisany mu port: " + client.getLocalPort());			
			
			
			while(it.hasNext())
			{
				Map.Entry<String, Peer> pair = (Map.Entry<String, Peer>) it.next();
															
				// jezeli moj zapisany peer socket = ten ktory do mnie napisal
				if(pair.getValue().socketOut.getInetAddress().getHostAddress().equals(client.getInetAddress().getHostAddress()))
				{	
					pair.getValue().socketIn = client;
					pair.getValue().input = new ObjectInputStream(client.getInputStream());
					
					System.out.println("Wiadomosc: " + pair.getValue().input.readInt());
					
					break;
				}
			}		
						
		}
		
	}
	private void close() throws IOException
	{
		serv.close();
		for(Player p : players)
		{
			peers.get(p.getNickname()).close();
		}
	}
	
	public Communication(Player[] p)
	{
		players = p;
	}
	public Communication(Player[] p, int servPort, int peerPort)
	{
		players = p;
		servport = servPort;
		port = peerPort;
	}
	public int getServPort()
	{
		return servport;
	}
	
	public void send(String nick, Message msg) throws IOException
	{
		peers.get(nick).send(msg);
	}
	Object receive(String nick) throws ClassNotFoundException, IOException
	{
		return peers.get(nick).receive();
	}
		
	public void run() 
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
			initPeers(players);
		} catch (IOException e1) {
			System.err.println("Niepowodzenie z utworzeniem Peers");
			e1.printStackTrace();
		}		
		
			
		
		// Closing everything
		try {
			close();
		} catch (IOException e) {
			System.err.println("Error with closing");
			e.printStackTrace();
		}
		
			
		
	}
	
	
	public static void main(String[] args) throws IOException {
		Player[] players = {new Player("ja","localhost"), new Player("ja2","localhost")};	// nick i ip z hamachi
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
						
		System.out.println("...................");
		
		Communication com = new Communication(players);		
		exec.execute(com);
							
	}







}
