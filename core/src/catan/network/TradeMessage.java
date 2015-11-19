package catan.network;

import catan.network.UpdateMessage.UpdateType;

public class TradeMessage extends Message 
{


	public enum TradeType		
	{
		PROPOSITION, YES, NO, CONTR;
	}
	
	protected TradeType subType;
	
	TradeMessage(TradeType tt) 
	{
		super(Message.Type.TRADE);
		subType = tt;
	}	
	public TradeType getSubType()
	{
		return subType;
	}
	
	
	
}
