package catan.network;

import catan.network.UpdateMessage.UpdateType;

public class TradeMessage extends Message 
{


	public enum TradeType		
	{
		OFFERT, YES, NO, CONTR, DEAL, END_TRADE;
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
