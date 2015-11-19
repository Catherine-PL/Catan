package catan.network;

import catan.network.SystemMessage.SystemType;

public class FactoryProducer {
	
	public enum FactoryType		
	{
		SYSTEM, TRADE, UPDATE;
	}
	
	public static AbstractMessageFactory getFactory(FactoryType type)
	{
		switch(type)
		{
		
		case SYSTEM:
			return new SystemMessageFactory();
		
		case TRADE:
			return new TradeMessageFactory();
			
		case UPDATE:
			return new UpdateMessageFactory();
			
		default:
			System.out.println("Podany zosta³ niepoprawny typ Factory");
			return null;
		
		}
	}

	public static void main(String[] args)
	{
		AbstractMessageFactory mf = FactoryProducer.getFactory(FactoryType.SYSTEM);
		MsgPeer msg = null;
		try {
			msg = (MsgPeer) mf.getSystemMessage(SystemType.PEER, "Sebastian");
		} catch (ContentException e) {

			e.printStackTrace();
		}
		
		System.out.println(msg.getContent());
	}

	
}
