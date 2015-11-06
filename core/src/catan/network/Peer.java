package catan.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Peer{
	
	Socket socketOut = null;
	Socket socketIn = null;
	ObjectOutputStream output = null;
	ObjectInputStream input = null;

	Peer(String nick, String ip, int port) throws IOException {		
		socketOut = new Socket(ip,port);
		System.out.println("Stworzono PeerSocket na porcie: " + socketOut.getLocalPort());
		
		output = new ObjectOutputStream(socketOut.getOutputStream());
		output.writeObject(nick);
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
