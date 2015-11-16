package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

class Peer{
	
	Socket socketOut = null;
	Socket socketIn = null;
	ObjectOutputStream output = null;
	ObjectInputStream input = null;

	Peer(String nick, String ip, int port) throws IOException {		
		SocketAddress sockaddr = new InetSocketAddress(ip, port);
		socketOut = new Socket();
		socketOut.connect(sockaddr, 5*1000);		// 5 sekund na pol¹czenie
		
		//socketOut = new Socket(ip,port);	// chêæ nawiazania polaczenia  -- w jednej lini to co wyzej, bez timeout
		System.out.println("Stworzono PeerSocket na porcie: " + socketOut.getLocalPort());
		
		output = new ObjectOutputStream(socketOut.getOutputStream());
		output.writeObject(new MsgPeer(nick));	// wys³anie od razu wiadomosci z moim nickiem
		output.flush();		
		 
	}
		
	void send(Object ob) throws IOException
	{
		output.writeObject(ob);
	}
	Object receive() throws ClassNotFoundException, IOException
	{
		return input.readObject();
	}
	void close() throws IOException
	{		
		output.close();
		socketOut.close();
		input.close();
		socketIn.close();
	}

}
