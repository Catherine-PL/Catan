package catan.network;

public class MsgPlayer extends Message {

	public MsgPlayer(Object content)
	{
		super(Message.Type.GRACZ, content);		
	}
	

}
