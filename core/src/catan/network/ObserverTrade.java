package catan.network;

import catan.network.TradeMessage.TradeType;

public class ObserverTrade extends Observer {

	private TradeType msgType;

	ObserverTrade(GameCommunication subject) {
		super(subject);
		this.sub.add(this);
	}

	@Override
	void update() {
		if(((GameCommunication)this.sub).getTradeState()!=null)
			msgType = ((GameCommunication)this.sub).getTradeState();		
	}

	
	public TradeType getStateGame() {
		return msgType;
	}

}
