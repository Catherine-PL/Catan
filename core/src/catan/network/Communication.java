package catan.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Communication implements Runnable {

	private Map<String,Peer> peers;
	
	private int port = 8080;
	
	public Communication(Player[] players) throws IOException
	{		
		peers = new HashMap<String,Peer>();
		for(Player p : players)
		{
			peers.put(p.getNickname(), new Peer(p.getNickname(), p.getIp(), port));			
		}				
	}
	public void send(Message msg) throws IOException
	{
		peers.get(msg.getDestination()).output.write(msg.getContent());
	}
		
	public void run() 
	{
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}







}
