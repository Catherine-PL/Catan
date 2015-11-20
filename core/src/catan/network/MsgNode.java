package catan.network;

import database.Node;

public class MsgNode extends UpdateMessage
{
	private Node content;
	private int index;
	
	MsgNode(Node node, int index) {
		super(UpdateMessage.UpdateType.NODE);
		this.content = node;
		this.index = index;
	}
	
	public Node getContent()
	{
		return content;
	}
	public int getIndex()
	{
		return index;
	}
}
