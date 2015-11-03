package catan.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

class Peer{
	
	Socket socket = null;
	DataOutputStream output = null;
	DataInputStream input = null;

	Peer(String nick, InetAddress ip, int port) throws IOException {		
		socket = new Socket(ip,port);
		System.out.println("Stworzono PeerSocket na porcie: " + socket.getLocalPort());
		
		output = new DataOutputStream(socket.getOutputStream());
		output.writeInt(socket.getLocalPort());
		output.flush();
		
		input = new DataInputStream(socket.getInputStream());
		 
	}
	
	void close() throws IOException
	{
		
		input.close();
		output.close();
		socket.close();
		
	}

}
