package catan.network;

public abstract class SystemMessage extends Message{

	/**
	 * Types of system messages
	 * @author Sebastian
	 *
	 */
	public enum SystemType		
	{
		PEER, INVITATION, ACCEPT, REJECT, START_GAME, ABANDON;
	}
	
	protected SystemType subType;
	
	SystemMessage(SystemType st) 
	{
		super(Message.Type.SYSTEM);
		subType = st;
	}	
	SystemType getSubType()
	{
		return subType;
	}
	
	

}
