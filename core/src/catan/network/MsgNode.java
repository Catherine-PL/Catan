package catan.network;

import database.Node;

class MsgNode extends UpdateMessage
{
	private Boolean content = null;
	private int index;
	
	MsgNode(Boolean city, int index) {
		super(UpdateMessage.UpdateType.NODE);
		this.content = city;
		this.index = index;
	}
	
	Boolean getContent()
	{
		return content;
	}
	int getIndex()
	{
		return index;
	}
}
