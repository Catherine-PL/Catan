package catan.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Communication implements Runnable {

	private Player[]			players;
	private Map<String,Peer>	peers;
	public ServerSocket			serv;
	private int					servport = 8080;
	private int					port = 8080;
	
	
	public Communication(Player[] p)
	{
		players = p;
	}
	private void initServPort() throws IOException
	{
		serv = new ServerSocket(servport);
	}
	private void initPeers(Player[] players)	// dodaæ oczekiwanie i ewentualnie zmiana portu na wy¿szy	
	{
		for(Player p : players)
		{
			try
			{
				peers.put(p.getNickname(), new Peer(p.getNickname(), p.getIp(), servport));		
				peers.get(p.getNickname()).output.write(p.getNickname().getBytes());
				peers.get(p.getNickname()).output.flush();
			}catch(IOException e)
			{
				System.out.println("Problem z: " + p.getNickname());
				e.printStackTrace();				
			}
		}	
	}
	private void setPorts() throws IOException
	{
		for(int i=0; i<2; i++)
		{
			Socket client = serv.accept();
			DataInputStream input = new DataInputStream(client.getInputStream());
		
			System.out.println("zakaceptowano polaczenie, które przysz³o z: " + client.getInetAddress() + " : " + client.getPort());
			System.out.println("Aktualnie przypisany mu port: " + client.getLocalPort());
			System.out.println("odczytana wiadomosc: " + input.readInt());
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
	public void send(Message msg) throws IOException
	{
		peers.get(msg.getDestination()).output.write(msg.getContent());
	}
		
	public void run() 
	{
		peers = new HashMap<String,Peer>();
		for(int i=0; i<5; i++)		// a¿ 5 razy spróbuje utwozyæ ServerSocket
		{
			try
			{  
				initServPort();		// inicjalizacja portu servera
				break;
			}
			catch(IOException port)
			{
				System.out.println("B³¹d przy tworzeniu server socket.");
				System.out.println("Ponowna próba na innym porcie o 1 wiêkszym");
				servport++;
			}
		}
		System.out.println("Utworzono ServSocket: " + serv.getLocalPort());
		
		
		
		
		initPeers(players);		// inicjalizacji wszystkich peer i wysy³anie od razu wiadomoœci o porcie lokalnym
		
		
		
		
		try {
			setPorts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		try {
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/*
		for(int i=0; i < peers.keySet().size(); i++)
		{
			Socket clientSocket = serv.accept();
			System.out.println(clientSocket.getPort());
			InputStream input = clientSocket.getInputStream();			
			int x;
			
			x =	input.read();
			while(x != -1)
			{
				System.out.println((char)x);
				x =	input.read();
			}
		}
		*/
		
	}
	
	
	public static void main(String[] args) throws IOException {
		Player[] players = {new Player("ja","localhost"), new Player("ja2","localhost")};	// nick i ip z hamachi
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		
		System.out.println("Nick: " + players[0].getNickname());
		System.out.println(players[0].getIp());
		System.out.println("");
		
		System.out.println(players[0].getIp().getHostAddress());
		System.out.println(players[0].getIp().getHostName());
		
		System.out.println("...................");
		
		Communication com = new Communication(players);
		
		exec.execute(com);
							
	}







}
