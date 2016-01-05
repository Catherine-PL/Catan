package catan.network;

class UpdateMessage extends Message {

	enum UpdateType		
	{
		DICE, BOARD, TILE, NODE, RESOURCES, END_GAME, END_TURN;
	}
	
	protected UpdateType subType;
	
	UpdateMessage(UpdateType ut) 
	{
		super(Message.Type.UPDATE);
		subType = ut;
	}	
	UpdateType getSubType()
	{
		return subType;
	}
	
	
	
}
