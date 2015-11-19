package catan.network;

abstract class SystemMessage extends Message{

	public enum SystemType		
	{
		PEER, INVITATION, ACCEPT, REJECT, START_GAME, END_GAME, ABANDON, END_TURN;
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
