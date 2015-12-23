package catan.network;

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

}
