package catan.network;

class ContentException extends Exception{}

public class MessageFactory {
	
	public static Message createMessage(Message.Type t, Object content) throws ContentException
	{
		switch(t)
		{
		case GRACZ:
			return new MsgPlayer(content);
		case DICE:
			if (content.getClass()!=Integer.class)				// zabezpieczenie
				throw new ContentException();
			else
				return new MsgDice(content);
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}

}
