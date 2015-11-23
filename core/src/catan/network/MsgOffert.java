package catan.network;

import java.util.HashMap;

import database.Tile;

public class MsgOffert extends TradeMessage {

	private HashMap<String, Integer> give = null;
	private HashMap<String, Integer> get = null;
	
	MsgOffert(HashMap<String, Integer> give, HashMap<String, Integer> get) 
	{
		super(TradeType.OFFERT);	
		this.give = give;
		this.get = get;		
	}
	
	public HashMap<String, Integer> getGive()
	{
		return give;
	}
	public HashMap<String, Integer> getGet()
	{
		return get;
	}

}
