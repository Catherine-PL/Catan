package catan.network;

class FactoryProducer {
	
	enum FactoryType		
	{
		SYSTEM, TRADE, UPDATE;
	}
	
	static AbstractMessageFactory getFactory(FactoryType type)
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
	
}
