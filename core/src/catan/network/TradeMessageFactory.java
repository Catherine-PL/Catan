package catan.network;

import java.util.HashMap;

import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;

public class TradeMessageFactory extends AbstractMessageFactory {


	TradeMessage getTradeMessage(TradeType type){
		switch(type)
		{							
		case YES:
			return new MsgYes();
		
		case NO:
			return new MsgNo();
			
		case DEAL:
			return new MsgDeal();
			
		case END_TRADE:
			return new MsgEndTrade();
			
		default:
			System.err.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}
	
	TradeMessage getTradeMessage(TradeType type, HashMap<String, Integer> give, HashMap<String, Integer> get) 
	{
		switch(type)
		{
		case OFFERT:		
			return new MsgOffert(give, get);
			
		default:
			System.err.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;			
		}

	}
	
	
	SystemMessage getSystemMessage(SystemType type, Object content) throws ContentException {
		return null;
	}
	UpdateMessage getUpdateMessage(UpdateType type, Object content) throws ContentException {
		return null;
	}


	@Override
	UpdateMessage getUpdateMessage(UpdateType type, Object content, int index) throws ContentException {
		// TODO Auto-generated method stub
		return null;
	}


	

}
