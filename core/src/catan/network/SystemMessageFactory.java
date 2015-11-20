package catan.network;

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
	public SystemMessage getSystemMessage(SystemMessage.SystemType type, Object content) throws ContentException
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
			
		case ABANDON:
			return new MsgAbandon();
		
		
		case END_GAME:
			return new MsgReject();
		
			
		default:
			System.err.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}


	TradeMessage getTradeMessage(TradeType type, Object content) throws ContentException {
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
