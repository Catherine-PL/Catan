package catan.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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
		output.writeInt(socketOut.getLocalPort());
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
