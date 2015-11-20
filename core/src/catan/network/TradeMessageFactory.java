package catan.network;

import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;

public class TradeMessageFactory extends AbstractMessageFactory {


	TradeMessage getTradeMessage(TradeType type, Object content) throws ContentException {
		switch(type)
		{
		
		case YES:
			if (content.getClass()!=Integer.class)
				throw new ContentException();
			else
				return null;// new MsgPeer((String)content);
			
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
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
