package catan.network;

public class MsgEndTrade extends TradeMessage {

	MsgEndTrade() 
	{
		super(TradeType.END_TRADE);
	}

}
