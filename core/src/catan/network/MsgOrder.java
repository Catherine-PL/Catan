package catan.network;

import java.util.List;

import database.Node;

public class MsgOrder extends UpdateMessage {

	private List content = null;
		
	MsgOrder(List list) {
		super(UpdateMessage.UpdateType.ORDER);
		content = list;
	}

	
	List getContent()
	{
		return content;
	}
	
}
