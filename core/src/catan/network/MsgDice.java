package catan.network;

public class MsgDice extends Message {
		
	MsgDice(Object obj)
	{
		super(Message.Type.DICE, obj);
	}
	
}
