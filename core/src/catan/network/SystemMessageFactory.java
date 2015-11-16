package catan.network;

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
			
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}		
}
