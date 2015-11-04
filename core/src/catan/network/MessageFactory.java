package catan.network;

public class MessageFactory {
	
	public Message getShape(Message.Type t)
	{
		switch(t)
		{
		case GRACZ:
			return new MsgPlayer(new Integer(1));
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		
		}
	}

}
