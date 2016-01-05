package catan.network;

public class MsgThiefLoot extends UpdateMessage {

	private String content = null;
	
	MsgThiefLoot(String resource) 
	{
		super(UpdateType.THIEF_LOOT);
		content = resource;
	}
	
	String getContent()
	{
		return content;
	}

	
}
