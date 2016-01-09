package catan.network;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class CommunicationDecorator implements P2P{
	
	protected P2P decoratedP2P;
		
	public CommunicationDecorator(P2P decoratedP2P)
	{
		this.decoratedP2P = decoratedP2P;
				
		/*
		msgHandler = new MessageHandler(board);
		invPlayers = new HashMap<String,InvStatus>();
		inGame = false;
		
		update = FactoryProducer.getFactory(FactoryType.UPDATE);
		system = FactoryProducer.getFactory(FactoryType.SYSTEM);
		trade = FactoryProducer.getFactory(FactoryType.TRADE);
		
		
		System.out.println("inGame: " + inGame);
		*/
	}

	
	@Override
	public void 	initCommunication(String myName, Collection<String> rememberedNodes, MessageHandler msgHandler)
			throws IOException 
	{	
		this.decoratedP2P.initCommunication(myName, rememberedNodes, msgHandler);				
	}

	@Override
	public String 	getNickname() {		
		return this.decoratedP2P.getNickname();
	}
	@Override
	public int 		getServPort() {
		return this.decoratedP2P.getServPort();
	}
	@Override
	public void 	addNodeP2P(String address) {
		this.decoratedP2P.addNodeP2P(address);		
	}	
	@Override
	public void 	removeNodeP2P(String n) {
		this.decoratedP2P.removeNodeP2P(n);
		
	}

	public String	getNickFromIp(String ip)
	{
		return this.decoratedP2P.getNickFromIp(ip);
	}
	
	public String getIpFromNick(String nick) {
		return this.decoratedP2P.getIpFromNick(nick);
	}
	
	@Override
	public void 	sendTo(String nick, Message msg) throws IOException {
		this.decoratedP2P.sendTo(nick, msg);
		
	}

	@Override
	public void 	disconnected(String nick) {
		this.decoratedP2P.disconnected(nick);		
	}

}
