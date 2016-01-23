package catan.network;

import java.util.HashMap;

import catan.network.TradeMessage.TradeType;

public class ObserverTrade extends Observer {

	private TradeType msgType;
	public HashMap get;
	public HashMap give;

	ObserverTrade(GameCommunication subject) {
		super(subject);
		this.sub.add(this);
	}

	@Override
	void update() {
		
		if(((GameCommunication)this.sub).getTradeState()!=null)
		{
			msgType = ((GameCommunication)this.sub).getTradeState();
			this.get=((GameCommunication)this.sub).get;
			this.give=((GameCommunication)this.sub).give;
		}
		else
		{
			msgType=null;
		}
	}

	
	public TradeType getStateGame() {
		return msgType;
	}

}
