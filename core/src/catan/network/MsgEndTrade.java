package catan.network;

class MsgEndTrade extends TradeMessage {

	MsgEndTrade() 
	{
		super(TradeType.END_TRADE);
	}

}
