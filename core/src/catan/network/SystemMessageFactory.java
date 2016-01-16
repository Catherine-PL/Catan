package catan.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import catan.network.GameCommunication.InvStatus;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;

/**
 * Class needed to create messages
 * @author Sebastian
 *
 */
class SystemMessageFactory extends AbstractMessageFactory {
	
	
	
	/**
	 * 
	 * @param t Type of message
	 * @param content Information subject to encapsulation
	 * @return Message of your type with content, ready to send	
	 * @throws ContentException In case of mismatching content to message type
	 */
	SystemMessage getSystemMessage(SystemMessage.SystemType type, Object content) throws ContentException
	{
		switch(type)
		{
		
		case PEER:
			if (content.getClass()!=String.class)
				throw new ContentException();
			else
				return new MsgPeer((String)content);
			
		case INVITATION:
			return new MsgInvitation();
			
		case ACCEPT:
			return new MsgAccept();
			
		case REJECT:
			return new MsgReject();
			
		case START_GAME:
			return new MsgStartGame();
			
		case INV_LIST:
			return new MsgInvList((Map<String, InvStatus>) content);
			
		case ABANDON:
			return new MsgAbandon();								
			
		default:
			System.err.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}


	UpdateMessage getUpdateMessage(UpdateType type, Object content) throws ContentException {
		return null;
	}


	@Override
	UpdateMessage getUpdateMessage(UpdateType type, Object content, int index) throws ContentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	TradeMessage getTradeMessage(TradeType type) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	TradeMessage getTradeMessage(TradeType type, HashMap<String, Integer> content, HashMap<String, Integer> content2) {
		// TODO Auto-generated method stub
		return null;
	}		
}
