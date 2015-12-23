package catan.network;

import java.io.IOException;

public interface P2P {

	public void addNodeP2P(String address);
	//public void	removeNodeP2P(String address);
	public void	sendTo(String nick, Message msg) throws IOException;
	
}
