package catan.network;

import java.util.HashMap;

class MsgOffert extends TradeMessage {

	private HashMap<String, Integer> give = null;
	private HashMap<String, Integer> get = null;
	
	MsgOffert(HashMap<String, Integer> give, HashMap<String, Integer> get) 
	{
		super(TradeType.OFFERT);	
		this.give = give;
		this.get = get;		
	}
	
	HashMap<String, Integer> getGive()
	{
		return give;
	}
	HashMap<String, Integer> getGet()
	{
		return get;
	}

}
