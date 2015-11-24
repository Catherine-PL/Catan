package catan.network;

import database.Node;

class MsgNode extends UpdateMessage
{
	private Node content = null;
	private int index;
	
	MsgNode(Node node, int index) {
		super(UpdateMessage.UpdateType.NODE);
		this.content = node;
		this.index = index;
	}
	
	Node getContent()
	{
		return content;
	}
	int getIndex()
	{
		return index;
	}
}
