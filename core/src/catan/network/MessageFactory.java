package catan.network;

class ContentException extends Exception{}

/**
 * Class needed to create messages
 * @author Sebastian
 *
 */
public class MessageFactory {
	
	/**
	 * 
	 * @param t Type of message
	 * @param content Information subject to encapsulation
	 * @return Message of your type with content, ready to send	
	 * @throws ContentException In case of mismatching content to message type
	 */
	public static Message createMessage(Message.Type t, Object content) throws ContentException
	{
		switch(t)
		{
		case TEXT:
			if (content.getClass()!=String.class)
				throw new ContentException();
			else
				return new MsgText((String)content);
		case DICE:
			if (content.getClass()!=Integer.class)				// zabezpieczenie
				throw new ContentException();
			else
				return new MsgDice(((Integer)content).intValue());
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}

}
