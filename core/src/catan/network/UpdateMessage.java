package catan.network;

import catan.network.SystemMessage.SystemType;

public class UpdateMessage extends Message {

	public enum UpdateType		
	{
		DICE;
	}
	
	protected UpdateType subType;
	
	UpdateMessage(UpdateType ut) 
	{
		super(Message.Type.UPDATE);
		subType = ut;
	}	
	public UpdateType getSubType()
	{
		return subType;
	}
	
	
	
}
