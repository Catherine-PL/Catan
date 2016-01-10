package catan.network;

import java.util.Map;

import catan.network.GameCommunication.InvStatus;

public class MsgInvList extends SystemMessage {

	private Map<String,InvStatus> content;
	
	MsgInvList(Map<String,InvStatus> invStatus) {
		super(SystemType.INV_LIST);
		this.content = invStatus;
	}
	
	Map<String,InvStatus> getContent()
	{
		return this.content;
	}

}
