package catan.network;

abstract class SystemMessage extends Message{

	public enum SystemType		
	{
		PEER, END_TURN, END_GAME;
	}
	
	protected SystemType subType;
	
	SystemMessage(SystemType st) 
	{
		super(Message.Type.SYSTEM);
		subType = st;
	}	
	public SystemType getSubType()
	{
		return subType;
	}
	
	

}
