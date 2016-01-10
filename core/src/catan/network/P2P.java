package catan.network;

import java.io.IOException;
import java.util.Collection;

public interface P2P {

	
	public void 	initCommunication(String myName, Collection<String> rememberedNodes, MessageHandler msgHandler) throws IOException;
	
	public String 	getNickname();
	public int 		getServPort();
	
	/**
	 * Adding new NodeP2P to nodesP2P collection with adding nickname, creating new peer
	 * @param n New PlayerIP to add
	 */
	public void 	addNodeP2P(String address);
	public void		removeNodeP2P(String n);
	
	public String	getNickFromIp(String ip);
	public String	getIpFromNick(String ip);
	
	public void		sendTo(String nick, Message msg) throws IOException;
	public void 	disconnected(String nick);		

	
	
}
