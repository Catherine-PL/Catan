package catan.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

class Peer{
	
	Socket socket;
	OutputStream output;
	InputStream input;

	Peer(String nick, InetAddress ip, int port) throws IOException {		
		socket = new Socket(ip,port);
		output = socket.getOutputStream();
		input = socket.getInputStream();
		 
	}

}
