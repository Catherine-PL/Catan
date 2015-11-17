package catan.network;

import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;

public class UpdateMessageFactory extends AbstractMessageFactory {

	
	UpdateMessage getUpdateMessage(UpdateType type, Object content) throws ContentException {
		switch(type)
		{
		
		case DICE:
			if (content.getClass()!=Integer.class)
				throw new ContentException();
			else
				return new MsgDice((Integer) content);
			
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}
	

	SystemMessage getSystemMessage(SystemType type, Object content) throws ContentException {
		return null;
	}
	TradeMessage getTradeMessage(TradeType type, Object content) throws ContentException {
		return null;
	}

	

}
