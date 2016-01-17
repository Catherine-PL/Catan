package catan.network;

class UpdateMessage extends Message {

	enum UpdateType		
	{
		DICE, BOARD, TILE, NODE, RESOURCES, END_GAME, END_TURN, THIEF_LOOT, THIEF_TARGET, ORDER, ROAD;
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
